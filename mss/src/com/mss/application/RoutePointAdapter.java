package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.RoutePointService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoutePointAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<RoutePoint> mRoutePointList;
	private final DatabaseHelper mHelper;
	private final RoutePointService mRoutePointService;

	public RoutePointAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mRoutePointService = new RoutePointService(mHelper);
		notifyDataSetChanged();		
	}
	
	public RoutePoint getItemById(long id) throws Throwable {
		return mRoutePointService.getById(id);
	}

	@Override
	public int getCount() {
		if (mRoutePointList == null || mRoutePointList.isEmpty())
			return 0;
		return mRoutePointList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mRoutePointList == null || mRoutePointList.isEmpty())
			return null;
		return mRoutePointList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mRoutePointList == null || mRoutePointList.isEmpty())
			return -1l;
		return mRoutePointList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_route_point , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);
			holder.mAddress = (TextView) v.findViewById(R.id.label_address);
			holder.mStatus = (TextView) v.findViewById(R.id.label_status);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		RoutePoint n = mRoutePointList.get(position);

		holder.mName.setText(n.getShippingAddressName());
		holder.mAddress.setText(n.getShippingAddressValue());
		holder.mStatus.setText(n.getStatusName());

		return v;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void notifyDataSetChanged() {

		super.notifyDataSetChanged();
	}

	public void delete(RoutePoint routePoint) throws Throwable {
		mRoutePointService.deletePoint(routePoint);
		notifyDataSetChanged();
	}

	public void swapData(List<RoutePoint> data) {
		mRoutePointList = data;

		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
		TextView mAddress;
		TextView mStatus;
	}
}
