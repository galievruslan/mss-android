package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Warehouse;
import com.mss.domain.services.WarehouseService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WarehousesAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<Warehouse> mWarehouseList;
	private final DatabaseHelper mHelper;
	private final WarehouseService mWarehouseService;

	public WarehousesAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mWarehouseService = new WarehouseService(mHelper);
		notifyDataSetChanged();		
	}
	
	public Warehouse getItemById(long id) throws Throwable {
		return mWarehouseService.getById(id);
	}

	@Override
	public int getCount() {
		if (mWarehouseList == null || mWarehouseList.isEmpty())
			return 0;
		return mWarehouseList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mWarehouseList == null || mWarehouseList.isEmpty())
			return null;
		return mWarehouseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mWarehouseList == null || mWarehouseList.isEmpty())
			return -1l;
		return mWarehouseList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.warehouse_item_layout , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);
			holder.mAddress = (TextView) v.findViewById(R.id.label_address);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		Warehouse n = mWarehouseList.get(position);

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

	public void swapData(List<Warehouse> data) {
		mWarehouseList = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
		TextView mAddress;
	}
}
