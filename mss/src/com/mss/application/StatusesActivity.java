package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.widget.SearchView;
import com.mss.application.fragments.StatusesFragment;
import com.mss.application.fragments.StatusesFragment.OnStatusSelectedListener;
import com.mss.domain.models.Status;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

public class StatusesActivity extends SherlockFragmentActivity implements OnStatusSelectedListener, Callback, LoaderCallbacks<List<Status>> {

	private static final String TAG = StatusesActivity.class.getSimpleName();
	private static final int LOADER_ID_STATUSES = 0;
	
	private StatusAdapter mStatusAdapter;
	private String mSearchCriteria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statuses);
		try {
			mStatusAdapter = new StatusAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		StatusesFragment fragmentStatuses = getStatusesFragment();
		fragmentStatuses.addOnStatusSelectedListener(this);
		
		getSupportLoaderManager().initLoader(LOADER_ID_STATUSES, null, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onStatusSelected(Status status, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("status_id", status.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected StatusesFragment getStatusesFragment() {
		return (StatusesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_statuses);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_statuses, menu);

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
		getSupportLoaderManager().restartLoader(LOADER_ID_STATUSES, null, this);
	}
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return false;
		}
	}

	@Override
	public Loader<List<Status>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_STATUSES:
			try {
				return new StatusesLoader(this, mSearchCriteria);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<Status>> loader, List<Status> data) {
		switch (loader.getId()) {
		case LOADER_ID_STATUSES:
			mStatusAdapter.swapData(data);
			getStatusesFragment().setListAdapter(mStatusAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Status>> arg0) {
		mStatusAdapter.swapData(null);
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
