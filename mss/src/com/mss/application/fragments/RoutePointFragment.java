package com.mss.application.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.mss.application.R;
import com.mss.application.R.layout;
import com.mss.application.R.menu;
import com.mss.domain.models.RoutePoint;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RoutePointFragment extends SherlockFragment {

	private RoutePoint mRoutePoint;
	private TextView mName;
	private TextView mAddress;

	/**
	 * We have to provide a public empty constructor because the framework needs it
	 */
	public RoutePointFragment() {

	}

	/**
	 * We prefer to instantiate our {@link Fragment Fragments} using a factory method
	 * 
	 * @param note
	 *            the {@link Note} instance
	 * @return the actual {@link NoteFragment} instance
	 */
	public static RoutePointFragment newInstance(RoutePoint routePoint) {
		RoutePointFragment fragment = new RoutePointFragment();
		fragment.mRoutePoint = routePoint;

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remember to call this method to enable onCreateOptionsMenu callback

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_route_point, null);

		if (mRoutePoint != null) {
			mName = (TextView) v.findViewById(R.id.tv_title);
			mName.setText(mRoutePoint.getId());
			mAddress = (TextView) v.findViewById(R.id.tv_text);
			mAddress.setText(mRoutePoint.getShippingAddressId());
		}

		return v;
	}
	
	

	@Override
	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			com.actionbarsherlock.view.MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.menu_note, menu);
	}

	public RoutePoint getRoutePoint() {
		return mRoutePoint;
	}

	public long getRoutePointId() {
		if (mRoutePoint != null)
			return mRoutePoint.getId();
		return 0l;
	}

	public void updateContent(RoutePoint routePoint) {
		mRoutePoint = routePoint;
		mName.setText(mRoutePoint.getTitle());
		mAddress.setText(mRoutePoint.getText());
	}

}
