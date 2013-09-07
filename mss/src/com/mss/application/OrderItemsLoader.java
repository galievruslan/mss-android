package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderItem;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class OrderItemsLoader extends AsyncTaskLoader<List<OrderItem>> {
	private long mOrderId;
	private List<OrderItem> mOrderItemList;

	private final DatabaseHelper mHelper;
	private final OrderService mOrderService;

	public OrderItemsLoader(Context ctx, long orderId) throws Throwable {
		super(ctx);
		
		this.mOrderId = orderId;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<OrderItem> loadInBackground() {
		mOrderItemList = IterableHelpers.toList(OrderItem.class, mOrderService.getOrderItems(mOrderId));
		return mOrderItemList;
	}

	@Override
	public void deliverResult(List<OrderItem> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mOrderItemList != null) {
			deliverResult(mOrderItemList);
		}

		if (takeContentChanged() || mOrderItemList == null) {
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
	public void onCanceled(List<OrderItem> data) {
		super.onCanceled(data);
	}
}
