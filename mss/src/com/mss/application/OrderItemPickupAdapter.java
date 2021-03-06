package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderPickedUpItem;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.services.PickupService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

public class OrderItemPickupAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<OrderPickupItem> mOrderPickupItemList;
	private final DatabaseHelper mHelper;
	private final PickupService mPickupService;
	
	public OrderItemPickupAdapter(Context ctx, long priceListId, long warehouseId) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mPickupService = new PickupService(mHelper, priceListId, warehouseId);
		notifyDataSetChanged();		
	}
	
	public OrderPickupItem getItemById(long id) throws Throwable {
		return mPickupService.getOrderPickupItemById(id);
	}

	@Override
	public int getCount() {
		if (mOrderPickupItemList == null || mOrderPickupItemList.isEmpty())
			return 0;
		return mOrderPickupItemList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mOrderPickupItemList == null || mOrderPickupItemList.isEmpty())
			return null;
		return mOrderPickupItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mOrderPickupItemList == null || mOrderPickupItemList.isEmpty())
			return -1l;
		return mOrderPickupItemList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_order_pickup_item , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);
			holder.mPickedUpData = (TableRow)v.findViewById(R.id.picked_up_data_table_row);
			holder.mPrice = (TextView) v.findViewById(R.id.label_price);
			holder.mCount = (TextView) v.findViewById(R.id.label_count);
			holder.mUoM = (TextView) v.findViewById(R.id.label_uom);
			holder.mWhseCount = (TextView) v.findViewById(R.id.label_count_on_whse);
			holder.mWhseUoM = (TextView) v.findViewById(R.id.label_uom_on_whse);
			

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		OrderPickupItem n = mOrderPickupItemList.get(position);

		holder.mName.setText(n.getProductName());
		holder.mPrice.setText(String.valueOf(n.getPrice()));
		holder.mWhseCount.setText(String.valueOf(n.getRemainder()));
		holder.mWhseUoM.setText(String.valueOf(n.getUoMName()));
		if (OrderEditContext.getPickedUpItems().containsKey(n.getProductId())) {
			OrderPickedUpItem pickedUpItem = OrderEditContext.getPickedUpItems().get(n.getProductId());
			
			holder.mCount.setText(String.valueOf(pickedUpItem.getCount()));
			holder.mUoM.setText(String.valueOf(pickedUpItem.getUoMName()));
			holder.mPickedUpData.setVisibility(View.VISIBLE);
			
			if (n.getRemainder() * n.getCountInBase() >=
				pickedUpItem.getCount() * pickedUpItem.getCountInBase()) {
				v.setBackgroundColor(Color.argb(100, 169, 220, 165));
			} else {
				v.setBackgroundColor(Color.argb(100, 248, 137, 137));
			}
		} else {
			holder.mPickedUpData.setVisibility(View.GONE);
			v.setBackgroundColor(Color.TRANSPARENT);
		}

		return v;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<OrderPickupItem> data) {
		mOrderPickupItemList = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;		
		TextView mPrice;
		TextView mWhseCount;
		TextView mWhseUoM;
		TableRow mPickedUpData;
		TextView mCount;
		TextView mUoM;
	}
}
