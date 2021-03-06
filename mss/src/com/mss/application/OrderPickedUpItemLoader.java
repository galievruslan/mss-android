package com.mss.application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderPickedUpItem;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.services.PickupService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class OrderPickedUpItemLoader extends AsyncTaskLoader<OrderPickedUpItem> {

	private final long mId;
	private OrderPickedUpItem mOrderPickedUpItem;
	
	private final DatabaseHelper mHelper;
	private final PickupService mPickupService;
	

	public OrderPickedUpItemLoader(Context ctx, long id, long priceListId, long warehouseId) throws Throwable {
		super(ctx);

		mId = id;

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mPickupService = new PickupService(mHelper, priceListId, warehouseId);
	}

	@Override
	public OrderPickedUpItem loadInBackground() {
		OrderPickupItem orderPickupItem = mPickupService.getOrderPickupItemById(mId);
		if (PickupItemContext.getPickedUpItem() != null)
			return PickupItemContext.getPickedUpItem();
		
		if (OrderEditContext.getPickedUpItems().containsKey(orderPickupItem.getProductId())) {
			OrderPickedUpItem orderPickedUpItem = OrderEditContext.getPickedUpItems().get(orderPickupItem.getProductId());
			mOrderPickedUpItem = new OrderPickedUpItem(
					orderPickedUpItem.getId(),
					orderPickedUpItem.getName(),
					orderPickedUpItem.getPrice(),
					orderPickedUpItem.getCount(),
					orderPickedUpItem.getProductUoMId(),
					orderPickedUpItem.getUoMId(),
					orderPickedUpItem.getUoMName(),
					orderPickedUpItem.getCountInBase());
		} else {
			mOrderPickedUpItem = new OrderPickedUpItem(
					orderPickupItem.getProductId(),
					orderPickupItem.getProductName(),
					orderPickupItem.getPrice(),
					0,
					orderPickupItem.getProductUoMId(),
					orderPickupItem.getUoMId(),
					orderPickupItem.getUoMName(),
					orderPickupItem.getCountInBase());
		}
		
		PickupItemContext.setPickedUpItem(mOrderPickedUpItem);
		return mOrderPickedUpItem;
	}

	@Override
	public void deliverResult(OrderPickedUpItem data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mOrderPickedUpItem != null) {
			deliverResult(mOrderPickedUpItem);
		}

		if (takeContentChanged() || mOrderPickedUpItem == null) {
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
	public void onCanceled(OrderPickedUpItem data) {
		super.onCanceled(data);
	}
}
