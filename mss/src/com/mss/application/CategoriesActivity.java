package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.mss.application.fragments.CategoriesFragment;
import com.mss.application.fragments.CategoriesFragment.OnCategorySelectedListener;
import com.mss.domain.models.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
public class CategoriesActivity extends SherlockFragmentActivity implements OnCategorySelectedListener, Callback, LoaderCallbacks<List<Category>> {

	private static final String TAG = CategoriesActivity.class.getSimpleName();
	private static final int LOADER_ID_CATEGORIES = 0;
	
	CategoriesAdapter mCategoriesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		try {
			mCategoriesAdapter = new CategoriesAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		CategoriesFragment fragmentCategories = getCategoriesFragment();
		fragmentCategories.addOnCategorySelectedListener(this);
		
		getSupportLoaderManager().initLoader(LOADER_ID_CATEGORIES, null, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onCategorySelected(Category category, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("category_id", category.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected CategoriesFragment getCategoriesFragment() {
		return (CategoriesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_category_list);
	}
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		// Note that we handle Edit and Delete items here, even if they were
		// added by the NoteFragment.

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return false;
		}
	}

	@Override
	public Loader<List<Category>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_CATEGORIES:
			try {
				return new CategoriesLoader(this);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<Category>> loader, List<Category> data) {
		switch (loader.getId()) {
		case LOADER_ID_CATEGORIES:
			mCategoriesAdapter.swapData(data);
			getCategoriesFragment().setListAdapter(mCategoriesAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Category>> arg0) {
		mCategoriesAdapter.swapData(null);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode,
		com.actionbarsherlock.view.Menu menu) {
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode,
		com.actionbarsherlock.view.Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, com.actionbarsherlock.view.MenuItem item) {
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {	}
}
