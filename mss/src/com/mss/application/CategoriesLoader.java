package com.mss.application;

import java.util.List;
import com.mss.utils.*;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Category;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class CategoriesLoader extends AsyncTaskLoader<List<Category>> {

	private static final String TAG = CategoriesLoader.class.getSimpleName();

	private List<Category> mCategoryList;

	private final DatabaseHelper mHelper;
	private final CategoryService mCategoryService;

	public CategoriesLoader(Context ctx) throws Throwable {
		super(ctx);
		
		mHelper = OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		mCategoryService = new CategoryService(mHelper);
	}

	/**
	 * Runs on {@link AsyncTask AsyncTask's} thread
	 */
	@Override
	public List<Category> loadInBackground() {
		try {
			mCategoryList = IterableHelpers.toList(Category.class, mCategoryService.getCategories());
		} catch (Throwable e) {
			Log.e(TAG, e.toString());
		}

		return mCategoryList;
	}

	@Override
	public void deliverResult(List<Category> data) {
		if (isReset()) {
			return;
		}

		if (isStarted()) {
			super.deliverResult(data);
		}
	}

	@Override
	protected void onStartLoading() {
		if (mCategoryList != null) {
			deliverResult(mCategoryList);
		}

		if (takeContentChanged() || mCategoryList == null) {
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
	public void onCanceled(List<Category> data) {
		super.onCanceled(data);
	}
}
