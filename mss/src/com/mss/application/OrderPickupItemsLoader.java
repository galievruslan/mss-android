package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class OrderPickupItemsLoader extends AsyncTaskLoader<List<OrderPickupItem>> {
	private long priceListId;
	private List<OrderPickupItem> mOrderPickupItemList;

	private final DatabaseHelper mHelper;
	private final OrderService mOrderService;

	public OrderPickupItemsLoader(Context ctx, long priceListId) throws Throwable {
		super(ctx);
		
		this.priceListId = priceListId;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<OrderPickupItem> loadInBackground() {
		mOrderPickupItemList = IterableHelpers.toList(OrderPickupItem.class, mOrderService.getOrderPickupItems(priceListId, OrderEditContext.getSelectedCategories()));
		return mOrderPickupItemList;
	}

	@Override
	public void deliverResult(List<OrderPickupItem> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mOrderPickupItemList != null) {
			deliverResult(mOrderPickupItemList);
		}

		if (takeContentChanged() || mOrderPickupItemList == null) {
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
	public void onCanceled(List<OrderPickupItem> data) {
		super.onCanceled(data);
	}
}
