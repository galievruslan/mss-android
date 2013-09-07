package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderItem;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderItemAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<OrderItem> mOrderItems;
	private final DatabaseHelper mHelper;
	private final OrderService mOrderService;
	
	public OrderItemAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
		notifyDataSetChanged();		
	}
	
	public OrderItem getItemById(long id) throws Throwable {
		return mOrderService.getOrderItemById(id);
	}

	@Override
	public int getCount() {
		if (mOrderItems == null || mOrderItems.isEmpty())
			return 0;
		return mOrderItems.size();
	}

	@Override
	public Object getItem(int position) {
		if (mOrderItems == null || mOrderItems.isEmpty())
			return null;
		return mOrderItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mOrderItems == null || mOrderItems.isEmpty())
			return -1l;
		return mOrderItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_order_item , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);
			holder.mCount = (TextView) v.findViewById(R.id.label_count);
			holder.mPrice = (TextView) v.findViewById(R.id.label_price);
			holder.mUoM = (TextView) v.findViewById(R.id.label_uom);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		OrderItem n = mOrderItems.get(position);

		holder.mName.setText(n.getProductName());
		holder.mPrice.setText(String.valueOf(n.getPrice()));		
		holder.mCount.setText(String.valueOf(n.getCount()));
		holder.mUoM.setText(String.valueOf(n.getUnitOfMeasureName()));
		
		return v;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<OrderItem> data) {
		mOrderItems = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
		TextView mCount;
		TextView mPrice;
		TextView mUoM;
	}
}
