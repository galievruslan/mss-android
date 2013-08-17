package com.mss.application;

import java.util.HashSet;
import java.util.Set;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.application.R;
import com.mss.domain.models.OrderPickupItem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OrderPickupItemsActivity extends SherlockListFragment {
	private static final String TAG = OrderPickupItemsActivity.class.getSimpleName();
	
	private final Set<OnOrderPickupItemSelectedListener> mOnOrderPickupItemSelectedListeners = 
			new HashSet<OrderPickupItemsActivity.OnOrderPickupItemSelectedListener>(1);
	
	private int mLastPosition;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_order_pickup_item_list, container, false);
				
	    try {
			setListAdapter(new OrderAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnOrderPickupItemSelectedListener listener : mOnOrderPickupItemSelectedListeners) {
			listener.onOrderPickupItemSelected((OrderPickupItem) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}
	
	public void addOnOrderPickupItemSelectedListener(OnOrderPickupItemSelectedListener listener) {
		mOnOrderPickupItemSelectedListeners.add(listener);
	}

	public boolean removeOnOrderPickupItemSelectedListener(OnOrderPickupItemSelectedListener listener) {
		return mOnOrderPickupItemSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnOrderPickupItemSelectedListener {
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
		void onOrderPickupItemSelected(OrderPickupItem orderPickupItem, int position, long id);
	}
}
