package com.mss.application.fragments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.mss.application.OrderAdapter;
import com.mss.application.OrdersLoader;
import com.mss.application.R;
import com.mss.domain.models.Order;
import com.mss.domain.models.RoutePoint;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class RoutePointFragment extends SherlockListFragment implements Callback, LoaderCallbacks<List<Order>> {
	private static final String TAG = RoutePointFragment.class.getSimpleName();
	
	private RoutePoint mRoutePoint;
	private TextView mName;
	private TextView mAddress;

	private final Set<OnOrderSelectedListener> mOnOrderSelectedListeners = 
			new HashSet<RoutePointFragment.OnOrderSelectedListener>(1);
	
	private static final int LOADER_ID_ORDERS = 0;
	
	private int mLastPosition;
	private OrderAdapter mOrderAdapter;
	
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
			mName = (TextView) v.findViewById(R.id.route_point_name_text_view);
			mName.setText(mRoutePoint.getShippingAddressName());
			mAddress = (TextView) v.findViewById(R.id.route_point_address_text_view);
			mAddress.setText(mRoutePoint.getShippingAddressValue());
			
			try {
		    	mOrderAdapter = new OrderAdapter(v.getContext());
				setListAdapter(mOrderAdapter);
				getLoaderManager().initLoader(LOADER_ID_ORDERS, null, this);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		}
		
		return v;
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

	@Override
	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			com.actionbarsherlock.view.MenuInflater inflater) {
		inflater.inflate(R.menu.menu_route_point, menu);
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
		mName.setText(mRoutePoint.getShippingAddressName());
		mAddress.setText(mRoutePoint.getShippingAddressValue());
	}
	
	@Override
	public Loader<List<Order>> onCreateLoader(int id, Bundle bundle) {
		switch (id) {
		case LOADER_ID_ORDERS:
			try {
				return new OrdersLoader(getActivity(), mRoutePoint.getId());
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
			OrderAdapter orderAdapter = mOrderAdapter;
			orderAdapter.swapData(data);
			setListAdapter(orderAdapter);
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
}
