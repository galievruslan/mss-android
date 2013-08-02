package com.mss.application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.RoutePointService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class RoutePointLoader extends AsyncTaskLoader<RoutePoint> {

	private final long mId;
	private RoutePoint mRoutePoint;
	private final DatabaseHelper mHelper;
	private final RoutePointService mRoutePointService;

	public RoutePointLoader(Context ctx, long id) throws Throwable {
		super(ctx);

		mId = id;

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mRoutePointService = new RoutePointService(mHelper);
	}

	@Override
	public RoutePoint loadInBackground() {
		try {
			mRoutePoint = mRoutePointService.getById(mId);
		} catch (Throwable e) {
			return null;
		}

		return mRoutePoint;
	}

	@Override
	public void deliverResult(RoutePoint data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mRoutePoint != null) {
			deliverResult(mRoutePoint);
		}

		if (takeContentChanged() || mRoutePoint == null) {
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
	public void onCanceled(RoutePoint data) {
		super.onCanceled(data);
	}
}
