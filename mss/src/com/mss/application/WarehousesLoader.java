package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Warehouse;
import com.mss.domain.services.WarehouseService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class WarehousesLoader extends AsyncTaskLoader<List<Warehouse>> {

	private static final String TAG = WarehousesLoader.class.getSimpleName();

	private List<Warehouse> mWarehouseList;

	private final DatabaseHelper mHelper;
	private final WarehouseService mWarehouseService;

	public WarehousesLoader(Context ctx) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mWarehouseService = new WarehouseService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<Warehouse> loadInBackground() {
		try {
			mWarehouseList = IterableHelpers.toList(Warehouse.class, mWarehouseService.getWarehouses());
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mWarehouseList;
	}

	@Override
	public void deliverResult(List<Warehouse> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mWarehouseList != null) {
			deliverResult(mWarehouseList);
		}

		if (takeContentChanged() || mWarehouseList == null) {
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
	public void onCanceled(List<Warehouse> data) {
		super.onCanceled(data);
	}
}
