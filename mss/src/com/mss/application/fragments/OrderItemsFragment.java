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
import com.mss.application.OrderItemAdapter;
import com.mss.application.OrderItemsLoader;
import com.mss.application.R;
import com.mss.domain.models.OrderItem;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OrderItemsFragment extends SherlockListFragment implements Callback, LoaderCallbacks<List<OrderItem>> {
	private static final String TAG = OrderItemsFragment.class.getSimpleName();
	private static final int LOADER_ID_ORDER_ITEMS = 0;
	
	private long mOrderId;
	
	private final Set<OnOrderItemSelectedListener> mOnOrderItemSelectedListeners = 
			new HashSet<OrderItemsFragment.OnOrderItemSelectedListener>(1);
	
	private int mLastPosition;
	private OrderItemAdapter mOrderItemAdapter;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_order_item_list, container, false);
				
		try {
			mOrderItemAdapter = new OrderItemAdapter(v.getContext());			
			setListAdapter(new OrderAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	    
	    return v;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnOrderItemSelectedListener listener : mOnOrderItemSelectedListeners) {
			listener.onOrderItemSelected((OrderItem) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}
	
	public void addOnOrderItemSelectedListener(OnOrderItemSelectedListener listener) {
		mOnOrderItemSelectedListeners.add(listener);
	}

	public boolean removeOnOrderItemSelectedListener(OnOrderItemSelectedListener listener) {
		return mOnOrderItemSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnOrderItemSelectedListener {
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
		void onOrderItemSelected(OrderItem orderItem, int position, long id);
	}

	public void refresh(long orderId) {
		mOrderId = orderId;
		getLoaderManager().restartLoader(LOADER_ID_ORDER_ITEMS, null, this);
	}
	
	@Override
	public Loader<List<OrderItem>> onCreateLoader(int id, Bundle bundle) {
		switch (id) {
		case LOADER_ID_ORDER_ITEMS:
			try {
				return new OrderItemsLoader(getSherlockActivity(), mOrderId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<OrderItem>> loader,
			List<OrderItem> data) {				
		switch (loader.getId()) {
		case LOADER_ID_ORDER_ITEMS:
			mOrderItemAdapter.swapData(data);
			setListAdapter(mOrderItemAdapter);
			setSelection(mLastPosition);
			break;
		default:
			break;
		}		
	}

	@Override
	public void onLoaderReset(Loader<List<OrderItem>> arg0) {
		mOrderItemAdapter.swapData(null);
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
