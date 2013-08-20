package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Product;
import com.mss.domain.models.ProductUnitOfMeasure;
import com.mss.domain.services.ProductService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class ProductUnitsOfMeasuresLoader extends AsyncTaskLoader<List<ProductUnitOfMeasure>> {

	private static final String TAG = ProductUnitsOfMeasuresLoader.class.getSimpleName();

	private Product mProduct;
	private List<ProductUnitOfMeasure> mUnitOfMeasureList;

	private final DatabaseHelper mHelper;
	private final ProductService mProductService;

	public ProductUnitsOfMeasuresLoader(Context ctx, Long productId) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);		
		mProductService = new ProductService(mHelper);
		
		mProduct = mProductService.getById(productId);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<ProductUnitOfMeasure> loadInBackground() {
		try {
			mUnitOfMeasureList = IterableHelpers.toList(ProductUnitOfMeasure.class, mProductService.getProductsUnitsOfMeasure(mProduct.getId()));
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mUnitOfMeasureList;
	}

	@Override
	public void deliverResult(List<ProductUnitOfMeasure> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mUnitOfMeasureList != null) {
			deliverResult(mUnitOfMeasureList);
		}

		if (takeContentChanged() || mUnitOfMeasureList == null) {
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
	public void onCanceled(List<ProductUnitOfMeasure> data) {
		super.onCanceled(data);
	}
}
