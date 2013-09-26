package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.widget.SearchView;
import com.mss.application.fragments.WarehousesFragment;
import com.mss.application.fragments.WarehousesFragment.OnWarehouseSelectedListener;
import com.mss.domain.models.Warehouse;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

public class WarehousesActivity extends SherlockFragmentActivity implements OnWarehouseSelectedListener, Callback, LoaderCallbacks<List<Warehouse>> {

	private static final String TAG = WarehousesActivity.class.getSimpleName();
	private static final int LOADER_ID_WAREHOUSES = 0;
	
	private WarehouseAdapter mWarehousesAdapter;
	private String mSearchCriteria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warehouses);
		try {
			mWarehousesAdapter = new WarehouseAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		WarehousesFragment fragmentWarehouses = getWarehousesFragment();
		fragmentWarehouses.addOnWarehouseSelectedListener(this);
		
		getSupportLoaderManager().initLoader(LOADER_ID_WAREHOUSES, null, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onWarehouseSelected(Warehouse warehouse, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("warehouse_id", warehouse.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected WarehousesFragment getWarehousesFragment() {
		return (WarehousesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_warehouse_list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_warehouses, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() { 
        	public boolean onQueryTextChange(String newText) { 
        		search(newText); 
        		return true; 
        	}

        	public boolean onQueryTextSubmit(String query) 
        	{
        		search(query);
        		return true;
        	}
        };
    
        searchView.setOnQueryTextListener(queryTextListener);		
		return true;
	}
	
	public void search(String criteria) { 
		mSearchCriteria = criteria;
		getSupportLoaderManager().restartLoader(LOADER_ID_WAREHOUSES, null, this);
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
	public Loader<List<Warehouse>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_WAREHOUSES:
			try {
				return new WarehousesLoader(this, mSearchCriteria);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<Warehouse>> loader, List<Warehouse> data) {
		switch (loader.getId()) {
		case LOADER_ID_WAREHOUSES:
			mWarehousesAdapter.swapData(data);
			getWarehousesFragment().setListAdapter(mWarehousesAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Warehouse>> arg0) {
		mWarehousesAdapter.swapData(null);
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
