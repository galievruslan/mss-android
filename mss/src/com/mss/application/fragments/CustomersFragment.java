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
import com.mss.application.CustomersAdapter;
import com.mss.application.R;
import com.mss.domain.models.Customer;

public class CustomersFragment extends SherlockListFragment {
	private static final String TAG = CustomersFragment.class.getSimpleName();
	
	private final Set<OnCustomerSelectedListener> mOnCustomerSelectedListeners = 
			new HashSet<CustomersFragment.OnCustomerSelectedListener>(1);
	
	private int mLastPosition;

	public CustomersFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_customer_list, container, false);
				
	    try {
			setListAdapter(new CustomersAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnCustomerSelectedListener listener : mOnCustomerSelectedListeners) {
			listener.onCustomerSelected((Customer) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnCustomerSelectedListener(OnCustomerSelectedListener listener) {
		mOnCustomerSelectedListeners.add(listener);
	}

	public boolean removeOnCustomerSelectedListener(OnCustomerSelectedListener listener) {
		return mOnCustomerSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnCustomerSelectedListener {
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
		void onCustomerSelected(Customer customer, int position, long id);
	}
}