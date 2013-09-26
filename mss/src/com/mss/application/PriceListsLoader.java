package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.PriceList;
import com.mss.domain.services.PriceListService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

public class PriceListsLoader extends AsyncTaskLoader<List<PriceList>> {

	private static final String TAG = PriceListsLoader.class.getSimpleName();

	private List<PriceList> mPriceLists;

	private final DatabaseHelper mHelper;
	private final PriceListService mPriceListService;
	private final String mSearchCriteria;
	
	public PriceListsLoader(Context ctx, String searchCriteria) throws Throwable {
		super(ctx);
		
		mSearchCriteria = searchCriteria;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mPriceListService = new PriceListService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<PriceList> loadInBackground() {
		try {
			if (TextUtils.isEmpty(mSearchCriteria))
				mPriceLists = IterableHelpers.toList(PriceList.class, mPriceListService.getPriceLists());
			else 
				mPriceLists = IterableHelpers.toList(PriceList.class, mPriceListService.getPriceLists(mSearchCriteria));
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mPriceLists;
	}

	@Override
	public void deliverResult(List<PriceList> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mPriceLists != null) {
			deliverResult(mPriceLists);
		}

		if (takeContentChanged() || mPriceLists == null) {
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
	public void onCanceled(List<PriceList> data) {
		super.onCanceled(data);
	}
}
