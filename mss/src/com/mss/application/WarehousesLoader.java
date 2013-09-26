package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Warehouse;
import com.mss.domain.services.WarehouseService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

public class WarehousesLoader extends AsyncTaskLoader<List<Warehouse>> {

	private static final String TAG = WarehousesLoader.class.getSimpleName();

	private List<Warehouse> mWarehouseList;

	private final DatabaseHelper mHelper;
	private final WarehouseService mWarehouseService;
	private final String mSearchCriteria;

	public WarehousesLoader(Context ctx, String searchCriteria) throws Throwable {
		super(ctx);
		
		mSearchCriteria = searchCriteria;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mWarehouseService = new WarehouseService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<Warehouse> loadInBackground() {
		try {
			if (TextUtils.isEmpty(mSearchCriteria))
				mWarehouseList = IterableHelpers.toList(Warehouse.class, mWarehouseService.getWarehouses());
			else 
				mWarehouseList = IterableHelpers.toList(Warehouse.class, mWarehouseService.getWarehouses(mSearchCriteria));
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
