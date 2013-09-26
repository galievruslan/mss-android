package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.mss.application.fragments.PriceListsFragment;
import com.mss.application.fragments.PriceListsFragment.OnPriceListSelectedListener;
import com.mss.domain.models.PriceList;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

public class PriceListsActivity extends SherlockFragmentActivity implements OnPriceListSelectedListener, Callback, LoaderCallbacks<List<PriceList>> {

	private static final String TAG = PriceListsActivity.class.getSimpleName();
	private static final int LOADER_ID_PRICE_LISTS = 0;
	
	PriceListAdapter mPriceListsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_lists);
		try {
			mPriceListsAdapter = new PriceListAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		PriceListsFragment fragmentPriceLists = getPriceListsFragment();
		fragmentPriceLists.addOnPriceListSelectedListener(this);
		
		getSupportLoaderManager().initLoader(LOADER_ID_PRICE_LISTS, null, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onPriceListSelected(PriceList priceList, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("price_list_id", priceList.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected PriceListsFragment getPriceListsFragment() {
		return (PriceListsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_price_lists);
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
	public Loader<List<PriceList>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_PRICE_LISTS:
			try {
				return new PriceListsLoader(this);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<PriceList>> loader, List<PriceList> data) {
		switch (loader.getId()) {
		case LOADER_ID_PRICE_LISTS:
			mPriceListsAdapter.swapData(data);
			getPriceListsFragment().setListAdapter(mPriceListsAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<PriceList>> arg0) {
		mPriceListsAdapter.swapData(null);
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
