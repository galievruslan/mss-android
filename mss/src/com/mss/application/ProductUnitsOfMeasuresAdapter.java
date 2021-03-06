package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.ProductUnitOfMeasure;
import com.mss.domain.services.ProductService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductUnitsOfMeasuresAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<ProductUnitOfMeasure> mProductUnitOfMeasureList;
	private final DatabaseHelper mHelper;
	private final ProductService mProductService;

	public ProductUnitsOfMeasuresAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mProductService = new ProductService(mHelper);
		notifyDataSetChanged();		
	}
	
	public ProductUnitOfMeasure getItemById(long id) throws Throwable {
		return mProductService.getProductsUnitOfMeasure(id);
	}

	@Override
	public int getCount() {
		if (mProductUnitOfMeasureList == null || mProductUnitOfMeasureList.isEmpty())
			return 0;
		return mProductUnitOfMeasureList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mProductUnitOfMeasureList == null || mProductUnitOfMeasureList.isEmpty())
			return null;
		return mProductUnitOfMeasureList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mProductUnitOfMeasureList == null || mProductUnitOfMeasureList.isEmpty())
			return -1l;
		return mProductUnitOfMeasureList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_product_unit_of_measure , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);
			holder.mIsBase = (TextView) v.findViewById(R.id.label_is_base);
			holder.mCountInBase = (TextView) v.findViewById(R.id.label_count_in_base);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		ProductUnitOfMeasure n = mProductUnitOfMeasureList.get(position);

		holder.mName.setText(n.getUnitOfMeasureName());
		if (n.getIsBase()) {
			holder.mIsBase.setVisibility(View.VISIBLE);
			holder.mCountInBase.setVisibility(View.GONE);
		} else {
			holder.mIsBase.setVisibility(View.GONE);
			holder.mCountInBase.setVisibility(View.VISIBLE);
			holder.mCountInBase.setText(holder.mCountInBase.getText().toString() + " - " + n.getCountInBase());
		}

		return v;
	}

	@Override
	public void onClick(View v) {}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<ProductUnitOfMeasure> data) {
		mProductUnitOfMeasureList = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
		TextView mIsBase;
		TextView mCountInBase;
	}
}
