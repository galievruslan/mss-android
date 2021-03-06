package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Order;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<Order> mOrderList;
	private final DatabaseHelper mHelper;
	private final OrderService mOrderService;

	public OrderAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
		notifyDataSetChanged();		
	}
	
	public Order getItemById(long id) throws Throwable {
		return mOrderService.getById(id);
	}

	@Override
	public int getCount() {
		if (mOrderList == null || mOrderList.isEmpty())
			return 0;
		return mOrderList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mOrderList == null || mOrderList.isEmpty())
			return null;
		return mOrderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mOrderList == null || mOrderList.isEmpty())
			return -1l;
		return mOrderList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_order , null, false);

			holder = new ViewHolder();
			holder.mShippingDate = (TextView) v.findViewById(R.id.label_shipping_date);
			holder.mAmount = (TextView) v.findViewById(R.id.label_amount);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		Order n = mOrderList.get(position);

		holder.mShippingDate.setText(DateFormat.getDateFormat(v.getContext()).format(n.getShippingDate()));
		holder.mAmount.setText(String.valueOf(n.getAmount()));

		return v;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void notifyDataSetChanged() {

		super.notifyDataSetChanged();
	}

	public void delete(Order order) throws Throwable {
		mOrderService.deleteOrder(order);
		notifyDataSetChanged();
	}

	public void swapData(List<Order> data) {
		mOrderList = data;

		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mShippingDate;
		TextView mAmount;
	}
}
