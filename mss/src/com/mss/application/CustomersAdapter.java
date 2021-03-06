package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Customer;
import com.mss.domain.services.CustomerService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomersAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<Customer> mCustomerList;
	private final DatabaseHelper mHelper;
	private final CustomerService mCustomerService;
	
	public CustomersAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mCustomerService = new CustomerService(mHelper);
		notifyDataSetChanged();		
	}
	
	public Customer getItemById(long id) throws Throwable {
		return mCustomerService.getById(id);
	}

	@Override
	public int getCount() {
		if (mCustomerList == null || mCustomerList.isEmpty())
			return 0;
		return mCustomerList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mCustomerList == null || mCustomerList.isEmpty())
			return null;
		return mCustomerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mCustomerList == null || mCustomerList.isEmpty())
			return -1l;
		return mCustomerList.get(position).getId();
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

		Customer n = mCustomerList.get(position);

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

	public void swapData(List<Customer> data) {
		mCustomerList = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
		TextView mAddress;
	}
}
