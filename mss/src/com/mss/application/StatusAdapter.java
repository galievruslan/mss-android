package com.mss.application;

import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Status;
import com.mss.domain.services.StatusService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatusAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	private List<Status> mStatuses;
	private final DatabaseHelper mHelper;
	private final StatusService mStatusService;

	public StatusAdapter(Context ctx) throws Throwable {
		mContext = ctx;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mStatusService = new StatusService(mHelper);
		notifyDataSetChanged();		
	}
	
	public Status getItemById(long id) throws Throwable {
		return mStatusService.getById(id);
	}

	@Override
	public int getCount() {
		if (mStatuses == null || mStatuses.isEmpty())
			return 0;
		return mStatuses.size();
	}

	@Override
	public Object getItem(int position) {
		if (mStatuses == null || mStatuses.isEmpty())
			return null;
		return mStatuses.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (mStatuses == null || mStatuses.isEmpty())
			return -1l;
		return mStatuses.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder holder;

		if (v == null) {
			v = mLayoutInflater.inflate(R.layout.item_layout_status , null, false);

			holder = new ViewHolder();
			holder.mName = (TextView) v.findViewById(R.id.label_name);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		Status n = mStatuses.get(position);

		holder.mName.setText(n.getName());

		return v;
	}

	@Override
	public void onClick(View v) {}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void swapData(List<Status> data) {
		mStatuses = data;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		TextView mName;
	}
}
