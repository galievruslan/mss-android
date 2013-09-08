package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Category;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriesAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<Category> mCategoryList;
	private final DatabaseHelper mHelper;
	private final CategoryService mCategoryService;

	public CategoriesAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mCategoryService = new CategoryService(mHelper);
		notifyDataSetChanged();		
	}
	
	public Category getItemById(long id) throws Throwable {
		return mCategoryService.getById(id);
	}

	@Override
	public int getCount() {
		if (mCategoryList == null || mCategoryList.isEmpty())
			return 0;
		return mCategoryList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mCategoryList == null || mCategoryList.isEmpty())
			return null;
		return mCategoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mCategoryList == null || mCategoryList.isEmpty())
			return -1l;
		return mCategoryList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_category , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		Category n = mCategoryList.get(position);

		holder.mName.setText(n.getName());

		return v;
	}

	@Override
	public void onClick(View v) {}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<Category> data) {
		mCategoryList = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
	}
}
