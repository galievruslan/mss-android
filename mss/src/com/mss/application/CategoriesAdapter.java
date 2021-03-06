package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import pl.polidea.treeview.AbstractTreeViewAdapter;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoriesAdapter extends AbstractTreeViewAdapter<Long> {

	private static final String TAG = CategoriesAdapter.class.getSimpleName();

    private DatabaseHelper mHelper;
    private CategoryService mCategoryService;
    
    private final OnCheckedChangeListener onCheckedChange = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final CompoundButton buttonView,
                final boolean isChecked) {
            final Long id = (Long) buttonView.getTag();
            changeSelected(isChecked, id);
        }

    };

    private void changeSelected(final boolean isChecked, final Long id) {
        if (isChecked) {
        	CategorySelectContext.getSelectedCategories().add(id);
        } else {
        	CategorySelectContext.getSelectedCategories().remove(id);
        }
    }

    public CategoriesAdapter(final SherlockFragmentActivity activity,
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
                .getLayoutInflater().inflate(R.layout.item_layout_category, null);
        return updateView(viewLayout, treeNodeInfo);
    }

    @Override
    public LinearLayout updateView(final View view,
            final TreeNodeInfo<Long> treeNodeInfo) {
        final LinearLayout viewLayout = (LinearLayout) view;
        final TextView descriptionView = (TextView) viewLayout
                .findViewById(R.id.list_item_description);
        descriptionView.setText(getDescription(treeNodeInfo.getId()));
        final CheckBox box = (CheckBox) viewLayout
                .findViewById(R.id.list_checkbox);
        box.setTag(treeNodeInfo.getId());
        if (treeNodeInfo.isWithChildren()) {
            box.setVisibility(View.GONE);
        } else {
            box.setVisibility(View.VISIBLE);
            box.setChecked(CategorySelectContext.getSelectedCategories().contains(treeNodeInfo.getId()));
        }
        box.setOnCheckedChangeListener(onCheckedChange);
        return viewLayout;
    }

    @Override
    public void handleItemClick(final View view, final Object id) {
        final Long longId = (Long) id;
        final TreeNodeInfo<Long> info = getManager().getNodeInfo(longId);
        if (info.isWithChildren()) {
            super.handleItemClick(view, id);
        } else {
            final ViewGroup vg = (ViewGroup) view;
            final CheckBox cb = (CheckBox) vg
                    .findViewById(R.id.list_checkbox);
            cb.performClick();
        }
    }

    @Override
    public long getItemId(final int position) {
        return getTreeId(position);
    }
}

