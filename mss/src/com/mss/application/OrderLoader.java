package com.mss.application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Order;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class OrderLoader extends AsyncTaskLoader<Order> {

	private final long mId;
	private Order mOrder;
	private final DatabaseHelper mHelper;
	private final OrderService mOrderService;

	public OrderLoader(Context ctx, long id) throws Throwable {
		super(ctx);
		mId = id;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
	}

	@Override
	public Order loadInBackground() {
		return mOrderService.getById(mId);
	}

	@Override
	public void deliverResult(Order data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mOrder != null) {
			deliverResult(mOrder);
		}

		if (takeContentChanged() || mOrder == null) {
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
	public void onCanceled(Order data) {
		super.onCanceled(data);
	}
}
