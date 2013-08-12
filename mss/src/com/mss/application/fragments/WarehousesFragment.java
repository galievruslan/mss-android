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
import com.mss.application.WarehousesAdapter;
import com.mss.domain.models.Warehouse;

public class WarehousesFragment extends SherlockListFragment {
	private static final String TAG = WarehousesFragment.class.getSimpleName();
	
	private final Set<OnWarehouseSelectedListener> mOnWarehouseSelectedListeners = 
			new HashSet<WarehousesFragment.OnWarehouseSelectedListener>(1);
	
	private int mLastPosition;

	public WarehousesFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_warehouse_list, container, false);
				
	    try {
			setListAdapter(new WarehousesAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnWarehouseSelectedListener listener : mOnWarehouseSelectedListeners) {
			listener.onWarehouseSelected((Warehouse) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnWarehouseSelectedListener(OnWarehouseSelectedListener listener) {
		mOnWarehouseSelectedListeners.add(listener);
	}

	public boolean removeOnWarehouseSelectedListener(OnWarehouseSelectedListener listener) {
		return mOnWarehouseSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnWarehouseSelectedListener {
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
		void onWarehouseSelected(Warehouse warehouse, int position, long id);
	}
}