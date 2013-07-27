package com.mss.application.fragments;

import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.domain.models.RoutePoint;

public class RouteFragment extends SherlockListFragment {
	private final Set<OnRoutePointSelectedListener> mOnRoutePointSelectedListeners = 
			new HashSet<RouteFragment.OnRoutePointSelectedListener>(1);
	private int mLastPosition;

	public RouteFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnRoutePointSelectedListener listener : mOnRoutePointSelectedListeners) {
			listener.onRoutePointSelected((RoutePoint) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnRoutePointSelectedListener(OnRoutePointSelectedListener listener) {
		mOnRoutePointSelectedListeners.add(listener);
	}

	public boolean removeOnRoutePointSelectedListener(OnRoutePointSelectedListener listener) {
		return mOnRoutePointSelectedListeners.remove(listener);
	}

	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnRoutePointSelectedListener {
		/**
		 * Called when the user selects a note entry on the list
		 * 
		 * @param n
		 *            {@link Note} instance
		 * @param position
		 *            its position on the list
		 * @param id
		 *            its id
		 */
		void onRoutePointSelected(RoutePoint routePoint, int position, long id);
	}
}