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
import com.mss.application.ShippingAddressAdapter;
import com.mss.domain.models.ShippingAddress;

public class ShippingAddressesFragment extends SherlockListFragment {
	private static final String TAG = ShippingAddressesFragment.class.getSimpleName();
	
	private final Set<OnShippingAddressSelectedListener> mOnShippingAddressSelectedListeners = 
			new HashSet<ShippingAddressesFragment.OnShippingAddressSelectedListener>(1);
	
	private int mLastPosition;

	public ShippingAddressesFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_shipping_address_list, container, false);
				
	    try {
			setListAdapter(new ShippingAddressAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnShippingAddressSelectedListener listener : mOnShippingAddressSelectedListeners) {
			listener.onShippingAddressSelected((ShippingAddress) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnShippingAddressSelectedListener(OnShippingAddressSelectedListener listener) {
		mOnShippingAddressSelectedListeners.add(listener);
	}

	public boolean removeOnShippingAddressSelectedListener(OnShippingAddressSelectedListener listener) {
		return mOnShippingAddressSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnShippingAddressSelectedListener {
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
		void onShippingAddressSelected(ShippingAddress shippingAddress, int position, long id);
	}
}