package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Order;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.OrderService;
import com.mss.domain.services.RoutePointService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class OrdersLoader extends AsyncTaskLoader<List<Order>> {

	private static final String TAG = OrdersLoader.class.getSimpleName();

	private RoutePoint mRoutePoint;
	private List<Order> mOrderList;

	private final DatabaseHelper mHelper;
	private final RoutePointService mRoutePointService;
	private final OrderService mOrderService;

	public OrdersLoader(Context ctx, long routePointId) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
		mRoutePointService = new RoutePointService(mHelper);
		try {
			mRoutePoint = mRoutePointService.getById(routePointId);
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<Order> loadInBackground() {
		try {
			mOrderList = IterableHelpers.toList(Order.class, mOrderService.getByRoutePoint(mRoutePoint));
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mOrderList;
	}

	@Override
	public void deliverResult(List<Order> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mOrderList != null) {
			deliverResult(mOrderList);
		}

		if (takeContentChanged() || mOrderList == null) {
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	@Override
	public void onCanceled(List<Order> data) {
		super.onCanceled(data);
	}
}
