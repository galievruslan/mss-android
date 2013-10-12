package com.mss.application;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class RoutePointPhotosAdapter extends BaseAdapter {

	private Context mContext;
	private final Integer[] mImage = {  };

	public RoutePointPhotosAdapter(Context ñontext) {
		mContext = ñontext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImage.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mImage[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mImage[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView view = new ImageView(mContext);
		view.setImageResource(mImage[position]);
		view.setPadding(20, 20, 20, 20);
		view.setLayoutParams(new Gallery.LayoutParams(250, 250));
		view.setScaleType(ImageView.ScaleType.FIT_XY);

		return view;
	}
}