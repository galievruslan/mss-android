package com.mss.application;

import java.util.Date;
import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.RoutePointService;
import com.mss.domain.services.RouteService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class RouteLoader extends AsyncTaskLoader<List<RoutePoint>> {

	private static final String TAG = RouteLoader.class.getSimpleName();

	private Route mRoute;
	private List<RoutePoint> mRoutePointList;

	private final DatabaseHelper mHelper;
	private final RouteService mRouteService;
	private final RoutePointService mRoutePointService;

	public RouteLoader(Context ctx, Date date) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mRouteService = new RouteService(mHelper);
		mRoutePointService = new RoutePointService(mHelper);
		try {
			mRoute = mRouteService.getOnDate(date);
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<RoutePoint> loadInBackground() {
		try {
			mRoutePointList = IterableHelpers.toList(RoutePoint.class, mRoutePointService.getPointsByRoute(mRoute));
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mRoutePointList;
	}

	@Override
	public void deliverResult(List<RoutePoint> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mRoutePointList != null) {
			deliverResult(mRoutePointList);
		}

		if (takeContentChanged() || mRoutePointList == null) {
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
	public void onCanceled(List<RoutePoint> data) {
		super.onCanceled(data);
	}
}
