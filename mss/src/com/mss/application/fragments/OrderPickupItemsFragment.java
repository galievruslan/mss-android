package com.mss.application.fragments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mss.application.OrderAdapter;
import com.mss.application.OrderItemPickupAdapter;
import com.mss.application.OrderPickupItemsLoader;
import com.mss.application.R;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.models.RoutePoint;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OrderPickupItemsFragment extends SherlockListFragment implements Callback, LoaderCallbacks<List<OrderPickupItem>> {
	private static final String TAG = OrderPickupItemsFragment.class.getSimpleName();
	private static final int LOADER_ID_ORDER_PICKUP_ITEMS = 0;
	
	private long mPriceListId;
	
	private final Set<OnOrderPickupItemSelectedListener> mOnOrderPickupItemSelectedListeners = 
			new HashSet<OrderPickupItemsFragment.OnOrderPickupItemSelectedListener>(1);
	
	private int mLastPosition;
	private OrderItemPickupAdapter mOrderPickupItemAdapter;

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
			mOrderPickupItemAdapter = new OrderItemPickupAdapter(v.getContext());
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
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

	public void refresh(long priceListId) {
		mPriceListId = priceListId;
		getLoaderManager().restartLoader(LOADER_ID_ORDER_PICKUP_ITEMS, null, this);
	}
	
	@Override
	public Loader<List<OrderPickupItem>> onCreateLoader(int id, Bundle bundle) {
		switch (id) {
		case LOADER_ID_ORDER_PICKUP_ITEMS:
			try {
				return new OrderPickupItemsLoader(getSherlockActivity(), mPriceListId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<OrderPickupItem>> loader,
			List<OrderPickupItem> data) {				
		switch (loader.getId()) {
		case LOADER_ID_ORDER_PICKUP_ITEMS:
			OrderItemPickupAdapter orderPickupItemAdapter = mOrderPickupItemAdapter;
			orderPickupItemAdapter.swapData(data);
			setListAdapter(orderPickupItemAdapter);
			break;
		default:
			break;
		}		
	}

	@Override
	public void onLoaderReset(Loader<List<OrderPickupItem>> arg0) {
		mOrderPickupItemAdapter.swapData(null);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = getSherlockActivity().getSupportMenuInflater();
		inflater.inflate(R.menu.action_mode_list, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		default:
			return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		mode = null;
	}
}
