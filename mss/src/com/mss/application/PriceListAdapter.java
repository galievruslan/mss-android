package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.PriceList;
import com.mss.domain.services.PriceListService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PriceListAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<PriceList> mPriceLists;
	private final DatabaseHelper mHelper;
	private final PriceListService mPriceListService;

	public PriceListAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mPriceListService = new PriceListService(mHelper);
		notifyDataSetChanged();		
	}
	
	public PriceList getItemById(long id) throws Throwable {
		return mPriceListService.getById(id);
	}

	@Override
	public int getCount() {
		if (mPriceLists == null || mPriceLists.isEmpty())
			return 0;
		return mPriceLists.size();
	}

	@Override
	public Object getItem(int position) {
		if (mPriceLists == null || mPriceLists.isEmpty())
			return null;
		return mPriceLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mPriceLists == null || mPriceLists.isEmpty())
			return -1l;
		return mPriceLists.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_price_list , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		PriceList n = mPriceLists.get(position);

		holder.mName.setText(n.getName());

		return v;
	}

	@Override
	public void onClick(View v) {}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<PriceList> data) {
		mPriceLists = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
	}
}
