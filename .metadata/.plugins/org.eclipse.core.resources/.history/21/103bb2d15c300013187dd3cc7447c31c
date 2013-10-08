package com.mss.application;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.CategoriesQuickFilterAdapter.OnCategorySelectedListener;
import com.mss.domain.models.Category;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CategoriesQuickFilterActivity extends SherlockFragmentActivity implements OnCategorySelectedListener {
    private enum TreeType implements Serializable {
        SIMPLE,
        FANCY
    }

    private static final String TAG = CategoriesQuickFilterActivity.class.getSimpleName();
    private TreeViewList treeView;

    private static final int LEVEL_NUMBER = 4;
    private TreeStateManager<Long> manager = null;
    private CategoriesQuickFilterAdapter simpleAdapter;
    private TreeType treeType;
    private boolean collapsible;
    
    private DatabaseHelper mHelper;
    private CategoryService mCategoryService;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeType newTreeType = null;
        boolean newCollapsible;
        
        mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mCategoryService = new CategoryService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
        
        if (savedInstanceState == null) {
        	CategorySelectContext.Init();
        	
            manager = new InMemoryTreeStateManager<Long>();
            final TreeBuilder<Long> treeBuilder = new TreeBuilder<Long>(manager);
                       
            Category[] categories = IterableHelpers.toArray(Category.class, mCategoryService.getCategories());
            for (int i = 0; i < categories.length; i++) {
            	if (categories[i].getParentId() == 0)
            		treeBuilder.sequentiallyAddNextNode(categories[i].getId(), 0);
            	else
            		treeBuilder.addRelation(categories[i].getParentId(), categories[i].getId());
            }
            Log.d(TAG, manager.toString());
            newTreeType = TreeType.SIMPLE;
            newCollapsible = true;
            manager.expandEverythingBelow(null);
        } else {
            manager = (TreeStateManager<Long>) savedInstanceState
                    .getSerializable("treeManager");
            if (manager == null) {
                manager = new InMemoryTreeStateManager<Long>();
            }
            newTreeType = (TreeType) savedInstanceState
                    .getSerializable("treeType");
            if (newTreeType == null) {
                newTreeType = TreeType.SIMPLE;
            }
            newCollapsible = savedInstanceState.getBoolean("collapsible");
        }
        setContentView(R.layout.activity_categories);
        treeView = (TreeViewList) findViewById(R.id.mainTreeView);
        simpleAdapter = new CategoriesQuickFilterAdapter(this, manager,
                LEVEL_NUMBER);
        simpleAdapter.addOnCategorySelectedListener(this);
        setTreeAdapter(newTreeType);
        setCollapsible(newCollapsible);
        
        if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putSerializable("treeManager", manager);
        outState.putSerializable("treeType", treeType);
        outState.putBoolean("collapsible", this.collapsible);
        super.onSaveInstanceState(outState);
    }

    protected final void setTreeAdapter(final TreeType newTreeType) {
        this.treeType = newTreeType;
        switch (newTreeType) {
        case SIMPLE:
            treeView.setAdapter(simpleAdapter);
            break;
        default:
            treeView.setAdapter(simpleAdapter);
        }
    }

    protected final void setCollapsible(final boolean newCollapsible) {
        this.collapsible = newCollapsible;
        treeView.setCollapsible(this.collapsible);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
    	if (item.getItemId() == android.R.id.home) {
    		CategorySelectContext.Init();
    		finish();
    	} 
        return true;
    }

	@Override
	public void onCategorySelected(long id) {
		CategorySelectContext.Init();
		collectChildren(id);
		   
		Set<Long> categoriesIdsSet = new HashSet<Long>();
		for (long categoryId : CategorySelectContext.getSelectedCategories()) {
			if (!categoriesIdsSet.contains(categoryId)) {
				categoriesIdsSet.add(categoryId);
			}
		}
		OrderEditContext.setSelectedCategories(categoriesIdsSet);
		
	    CategorySelectContext.Init();	 
		Intent intent=new Intent();
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	private void collectChildren(long id){
		if (!CategorySelectContext.getSelectedCategories().contains(id))
    		CategorySelectContext.getSelectedCategories().add(id);
		
		List<Long> childs = manager.getChildren(id);
        for (Long child : childs) {  
        	collectChildren(child);
		}
	}
}

