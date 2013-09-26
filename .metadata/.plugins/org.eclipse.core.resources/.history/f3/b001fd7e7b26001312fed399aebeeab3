package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
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
	
	StatusAdapter mStatusAdapter;
	
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
	public Loader<List<Status>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_STATUSES:
			try {
				return new StatusesLoader(this);
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
