package com.mss.application;

import java.io.Serializable;
import java.util.List;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Category;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CategoriesActivity extends SherlockFragmentActivity {
    private enum TreeType implements Serializable {
        SIMPLE,
        FANCY
    }

    private static final String TAG = CategoriesActivity.class.getSimpleName();
    private TreeViewList treeView;

    private static final int LEVEL_NUMBER = 4;
    private TreeStateManager<Long> manager = null;
    private CategoriesAdapter simpleAdapter;
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
        	
        	long[] selected = getIntent().getLongArrayExtra("categories");
    		for (long category : selected) {
    			CategorySelectContext.getSelectedCategories().add(category);
			}
        	
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
            manager.collapseChildren(null);
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
        simpleAdapter = new CategoriesAdapter(this, manager,
                LEVEL_NUMBER);
        setTreeAdapter(newTreeType);
        setCollapsible(newCollapsible);
        registerForContextMenu(treeView);
        
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
        final MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
    	if (item.getItemId() == android.R.id.home) {
    		CategorySelectContext.Init();
    		finish();
    	}    		
        if (item.getItemId() == R.id.save_menu_item) {
        	Intent intent=new Intent();
        	Long[] selected = CategorySelectContext.getSelectedCategories().toArray(new Long[0]);
        	long[] returns = new long[selected.length];
        	for (int i = 0; i < selected.length; i++) {
				returns[i] = selected[i];
			}
        	        	
    	    intent.putExtra("categories", returns);
    	    setResult(RESULT_OK, intent);
    	    CategorySelectContext.Init();
    	    finish();
        } else if (item.getItemId() == R.id.expand_all_menu_item) {
            manager.expandEverythingBelow(null);
        } else if (item.getItemId() == R.id.collapse_all_menu_item) {
            manager.collapseChildren(null);
        } else if (item.getItemId() == R.id.clear_filter_menu_item) {
            CategorySelectContext.Init();
            treeView.setAdapter(simpleAdapter);
        } else {
            return false;
        }
        return true;
    }    
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		final AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) menuInfo;
        final long id = adapterInfo.id;
        final TreeNodeInfo<Long> info = manager.getNodeInfo(id);
        final android.view.MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_categories, menu);
        if (!info.isWithChildren()) {
        	for (int i = 0; i < menu.size(); i++) {
				menu.getItem(i).setVisible(false);
			}
        }
        
        super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();
        final long id = info.id;
        if (item.getItemId() == R.id.context_menu_select_all) {            
            List<Long> childs = manager.getChildren(id);
            for (Long child : childs) {  
            	if (!CategorySelectContext.getSelectedCategories().contains(child))
            		CategorySelectContext.getSelectedCategories().add(child);
			}
            treeView.setAdapter(simpleAdapter);
            
            return true;
        } else if (item.getItemId() == R.id.context_menu_unselect_all) {
        	List<Long> childs = manager.getChildren(id);
            for (Long child : childs) {  
            	if (CategorySelectContext.getSelectedCategories().contains(child))
            		CategorySelectContext.getSelectedCategories().remove(child);
			}
            treeView.setAdapter(simpleAdapter);
            
            return true;
        }
        else {
            return super.onContextItemSelected(item);
        }
	}
}

