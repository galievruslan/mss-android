package com.mss.application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.models.ProductUnitOfMeasure;
import com.mss.domain.services.OrderService;
import com.mss.domain.services.ProductService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class OrderPickupItemLoader extends AsyncTaskLoader<OrderPickupItem> {

	private final long mId;
	private OrderPickupItem mOrderPickupItem;
	private final int count;
	
	private final DatabaseHelper mHelper;
	private final OrderService mOrderService;
	private final ProductService mProductService;
	
	private ProductUnitOfMeasure mProductUnitOfMeasure = null;

	public OrderPickupItemLoader(Context ctx, long id, long productIomId, int count) throws Throwable {
		super(ctx);

		mId = id;

		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mOrderService = new OrderService(mHelper);
		mProductService = new ProductService(mHelper);
		if (productIomId != 0)
			mProductUnitOfMeasure = mProductService.getProductsUnitOfMeasure(productIomId);
		
		this.count = count;
	}

	@Override
	public OrderPickupItem loadInBackground() {
		return mOrderService.getOrderPickupItemById(mId);
	}

	@Override
	public void deliverResult(OrderPickupItem data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			if (mProductUnitOfMeasure != null)
				data.setProductUnitOfMeasure(mProductUnitOfMeasure);
			
			data.setCount(count);
				
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mOrderPickupItem != null) {
			deliverResult(mOrderPickupItem);
		}

		if (takeContentChanged() || mOrderPickupItem == null) {
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
	public void onCanceled(OrderPickupItem data) {
		super.onCanceled(data);
	}
}
