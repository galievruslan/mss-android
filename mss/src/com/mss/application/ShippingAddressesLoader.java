package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Customer;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.services.CustomerService;
import com.mss.domain.services.ShippingAddressService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

public class ShippingAddressesLoader extends AsyncTaskLoader<List<ShippingAddress>> {

	private static final String TAG = ShippingAddressesLoader.class.getSimpleName();

	private Customer mCustomer;
	private List<ShippingAddress> mShippingAddressList;

	private final DatabaseHelper mHelper;
	private final ShippingAddressService mShippingAddressService;
	
	private final String mSearchCriteria;

	public ShippingAddressesLoader(Context ctx, Long customerId, String searchCriteria) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mSearchCriteria = searchCriteria;
		
		CustomerService customerService = new CustomerService(mHelper);
		mShippingAddressService = new ShippingAddressService(mHelper);
		mCustomer = customerService.getById(customerId);
	}
	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<ShippingAddress> loadInBackground() {
		try {
			Iterable<ShippingAddress> shippingAddresses;
			if (mCustomer != null) {
				if (TextUtils.isEmpty(mSearchCriteria))
					shippingAddresses = mShippingAddressService.findByCustomer(mCustomer);
				else
					shippingAddresses = mShippingAddressService.findByCustomer(mCustomer, mSearchCriteria);					
			} else {
				if (TextUtils.isEmpty(mSearchCriteria))
					shippingAddresses = mShippingAddressService.find();
				else
					shippingAddresses = mShippingAddressService.find(mSearchCriteria);
			}
			
			mShippingAddressList = IterableHelpers.toList(ShippingAddress.class, shippingAddresses);
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mShippingAddressList;
	}

	@Override
	public void deliverResult(List<ShippingAddress> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mShippingAddressList != null) {
			deliverResult(mShippingAddressList);
		}

		if (takeContentChanged() || mShippingAddressList == null) {
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
	public void onCanceled(List<ShippingAddress> data) {
		super.onCanceled(data);
	}
}
