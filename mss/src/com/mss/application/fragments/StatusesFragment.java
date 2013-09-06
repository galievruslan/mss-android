package com.mss.application.fragments;

import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.application.R;
import com.mss.application.StatusAdapter;
import com.mss.domain.models.Status;

public class StatusesFragment extends SherlockListFragment {
	private static final String TAG = StatusesFragment.class.getSimpleName();
	
	private final Set<OnStatusSelectedListener> mOnStatusSelectedListeners = 
			new HashSet<StatusesFragment.OnStatusSelectedListener>(1);
	
	private int mLastPosition;

	public StatusesFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_statuses, container, false);
				
	    try {
			setListAdapter(new StatusAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnStatusSelectedListener listener : mOnStatusSelectedListeners) {
			listener.onStatusSelected((Status) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnStatusSelectedListener(OnStatusSelectedListener listener) {
		mOnStatusSelectedListeners.add(listener);
	}

	public boolean removeOnStatusSelectedListener(OnStatusSelectedListener listener) {
		return mOnStatusSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnStatusSelectedListener {
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
		void onStatusSelected(Status status, int position, long id);
	}
}