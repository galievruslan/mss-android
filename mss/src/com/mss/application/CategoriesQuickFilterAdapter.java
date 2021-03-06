package com.mss.application;

import java.util.HashSet;
import java.util.Set;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.RouteFragment.OnRoutePointSelectedListener;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import pl.polidea.treeview.AbstractTreeViewAdapter;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoriesQuickFilterAdapter extends AbstractTreeViewAdapter<Long> {

	private static final String TAG = CategoriesQuickFilterAdapter.class.getSimpleName();

	private final Set<OnCategorySelectedListener> mOnCategorySelectedListeners = 
			new HashSet<CategoriesQuickFilterAdapter.OnCategorySelectedListener>(1);
	
    private DatabaseHelper mHelper;
    private CategoryService mCategoryService;
       

    public CategoriesQuickFilterAdapter(final SherlockFragmentActivity activity,
            final TreeStateManager<Long> treeStateManager,
            final int numberOfLevels) {
        super(activity, treeStateManager, numberOfLevels);
        
        mHelper = OpenHelperManager.getHelper(activity, DatabaseHelper.class);
		try {
			mCategoryService = new CategoryService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
    }

    private String getDescription(final long id) {
        return mCategoryService.getById(id).getName();
    }

    @Override
    public View getNewChildView(final TreeNodeInfo<Long> treeNodeInfo) {
        final LinearLayout viewLayout = (LinearLayout) getActivity()
                .getLayoutInflater().inflate(R.layout.item_layout_category_quick_filter, null);
        return updateView(viewLayout, treeNodeInfo);
    }

    @Override
    public LinearLayout updateView(final View view,
            final TreeNodeInfo<Long> treeNodeInfo) {
        final LinearLayout viewLayout = (LinearLayout) view;
        final TextView descriptionView = (TextView) viewLayout
                .findViewById(R.id.list_item_description);
        descriptionView.setText(getDescription(treeNodeInfo.getId()));
        descriptionView.setTag(treeNodeInfo.getId());
        
        return viewLayout;
    }

    @Override
    public void handleItemClick(final View view, final Object id) {        
        final ViewGroup vg = (ViewGroup) view;
        final TextView tv = (TextView) vg
                .findViewById(R.id.list_item_description);
    	
    	for (OnCategorySelectedListener listener : mOnCategorySelectedListeners) {
			listener.onCategorySelected((Long)tv.getTag());
		}
    }

    @Override
    public long getItemId(final int position) {
        return getTreeId(position);
    }
    
    public void addOnCategorySelectedListener(OnCategorySelectedListener listener) {
		mOnCategorySelectedListeners.add(listener);
	}

	public boolean removeOnCategorySelectedListener(OnRoutePointSelectedListener listener) {
		return mOnCategorySelectedListeners.remove(listener);
	}
    
	public interface OnCategorySelectedListener {
		void onCategorySelected(long id);
	}
}

