package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Customer;
import com.mss.domain.services.CustomerService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

public class CustomersLoader extends AsyncTaskLoader<List<Customer>> {

	private static final String TAG = CustomersLoader.class.getSimpleName();

	private List<Customer> mCustomerList;

	private final DatabaseHelper mHelper;
	private final CustomerService mCustomerService;
	
	private final String mSearchCriteria;

	public CustomersLoader(Context ctx, String searchCriteria) throws Throwable {
		super(ctx);
		
		mSearchCriteria = searchCriteria;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mCustomerService = new CustomerService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<Customer> loadInBackground() {
		try {
			if (TextUtils.isEmpty(mSearchCriteria)) {
				mCustomerList = IterableHelpers.toList(Customer.class, mCustomerService.getCustomers());
			}
			else {
				mCustomerList = IterableHelpers.toList(Customer.class, mCustomerService.getCustomers(mSearchCriteria));
			}
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mCustomerList;
	}

	@Override
	public void deliverResult(List<Customer> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mCustomerList != null) {
			deliverResult(mCustomerList);
		}

		if (takeContentChanged() || mCustomerList == null) {
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
	public void onCanceled(List<Customer> data) {
		super.onCanceled(data);
	}
}
