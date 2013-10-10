package com.mss.application;

import java.util.ArrayList;
import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.services.PickupService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

public class OrderPickupItemsLoader extends AsyncTaskLoader<List<OrderPickupItem>> {
	private List<OrderPickupItem> mOrderPickupItemList;

	private final DatabaseHelper mHelper;
	private final PickupService mPickupService;
	private final String mSearchCriteria;

	public OrderPickupItemsLoader(Context ctx, long priceListId, long warehouseId, String searchCriteria) throws Throwable {
		super(ctx);
		
		mSearchCriteria = searchCriteria;
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mPickupService = new PickupService(mHelper, priceListId, warehouseId);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<OrderPickupItem> loadInBackground() {
		if (TextUtils.isEmpty(mSearchCriteria))
			mOrderPickupItemList = IterableHelpers.toList(OrderPickupItem.class, mPickupService.getOrderPickupItems(OrderEditContext.getSelectedCategories()));
		else 
			mOrderPickupItemList = IterableHelpers.toList(OrderPickupItem.class, mPickupService.getOrderPickupItems(OrderEditContext.getSelectedCategories(), mSearchCriteria));
		
		if (OrderEditContext.getInOrder()) {			
			List<OrderPickupItem> pickedUpItems = new ArrayList<OrderPickupItem>();
			for (OrderPickupItem orderPickupItem : mOrderPickupItemList) {
				if (OrderEditContext.getPickedUpItems().containsKey(orderPickupItem.getProductId())) {
					pickedUpItems.add(orderPickupItem);
				}
			}			
			
			mOrderPickupItemList = pickedUpItems;
		}
		
		if (OrderEditContext.getInStock()) {			
			List<OrderPickupItem> pickedUpItems = new ArrayList<OrderPickupItem>();
			for (OrderPickupItem orderPickupItem : mOrderPickupItemList) {
				if (orderPickupItem.getRemainder() > 0) {
					pickedUpItems.add(orderPickupItem);
				}
			}			
			
			mOrderPickupItemList = pickedUpItems;
		}
		
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
