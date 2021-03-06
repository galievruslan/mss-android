package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.services.ShippingAddressService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShippingAddressAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<ShippingAddress> mShippingAddressList;
	private final DatabaseHelper mHelper;
	private final ShippingAddressService mShippingAddressService;

	public ShippingAddressAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mShippingAddressService = new ShippingAddressService(mHelper);
		notifyDataSetChanged();		
	}
	
	public ShippingAddress getItemById(long id) throws Throwable {
		return mShippingAddressService.getById(id);
	}

	@Override
	public int getCount() {
		if (mShippingAddressList == null || mShippingAddressList.isEmpty())
			return 0;
		return mShippingAddressList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mShippingAddressList == null || mShippingAddressList.isEmpty())
			return null;
		return mShippingAddressList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mShippingAddressList == null || mShippingAddressList.isEmpty())
			return -1l;
		return mShippingAddressList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_customer , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);
			holder.mAddress = (TextView) v.findViewById(R.id.label_address);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		ShippingAddress n = mShippingAddressList.get(position);

		holder.mName.setText(n.getName());
		holder.mAddress.setText(n.getAddress());

		return v;
	}

	@Override
	public void onClick(View v) {}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<ShippingAddress> data) {
		mShippingAddressList = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
		TextView mAddress;
	}
}
