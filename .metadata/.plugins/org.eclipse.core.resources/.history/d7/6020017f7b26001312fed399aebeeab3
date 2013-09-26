package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Status;
import com.mss.domain.services.StatusService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class StatusesLoader extends AsyncTaskLoader<List<Status>> {

	private static final String TAG = StatusesLoader.class.getSimpleName();

	private List<Status> mStatuses;

	private final DatabaseHelper mHelper;
	private final StatusService mStatusService;

	public StatusesLoader(Context ctx) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mStatusService = new StatusService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<Status> loadInBackground() {
		try {
			mStatuses = IterableHelpers.toList(Status.class, mStatusService.getStatuses());
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mStatuses;
	}

	@Override
	public void deliverResult(List<Status> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mStatuses != null) {
			deliverResult(mStatuses);
		}

		if (takeContentChanged() || mStatuses == null) {
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
	public void onCanceled(List<Status> data) {
		super.onCanceled(data);
	}
}
