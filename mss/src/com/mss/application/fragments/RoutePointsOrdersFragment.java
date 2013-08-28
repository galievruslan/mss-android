package com.mss.application.fragments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mss.application.OrderAdapter;
import com.mss.application.OrdersLoader;
import com.mss.application.R;
import com.mss.domain.models.Order;

public class RoutePointsOrdersFragment extends SherlockListFragment implements Callback, LoaderCallbacks<List<Order>> {
	private static final String TAG = RoutePointsOrdersFragment.class.getSimpleName();
	
	private static final int LOADER_ID_ORDERS = 0;
	
	private final Set<OnOrderSelectedListener> mOnOrderSelectedListeners = 
			new HashSet<RoutePointsOrdersFragment.OnOrderSelectedListener>(1);
	
	private int mLastPosition;
	
	private long mRoutePointId;
	private OrderAdapter mOrderAdapter;

	public RoutePointsOrdersFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_order_list, container, false);
		
		try {
			mOrderAdapter = new OrderAdapter(v.getContext());
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
	    return v;
	}
	
	public void refresh(long routePointId) {
		mRoutePointId = routePointId;
		getLoaderManager().initLoader(LOADER_ID_ORDERS, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnOrderSelectedListener listener : mOnOrderSelectedListeners) {
			listener.onOrderSelected((Order) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnOrderSelectedListener(OnOrderSelectedListener listener) {
		mOnOrderSelectedListeners.add(listener);
	}

	public boolean removeOnOrderSelectedListener(OnOrderSelectedListener listener) {
		return mOnOrderSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnOrderSelectedListener {
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
		void onOrderSelected(Order order, int position, long id);
	}

	@Override
	public Loader<List<Order>> onCreateLoader(int id, Bundle bundle) {
		switch (id) {
		case LOADER_ID_ORDERS:
			try {
				return new OrdersLoader(getSherlockActivity(), mRoutePointId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<Order>> loader, List<Order> data) {
		switch (loader.getId()) {
		case LOADER_ID_ORDERS:
			mOrderAdapter.swapData(data);
			setListAdapter(mOrderAdapter);
			break;
		default:
			break;
		}		
	}

	@Override
	public void onLoaderReset(Loader<List<Order>> arg0) {
		mOrderAdapter.swapData(null);		
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