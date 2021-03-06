package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.widget.SearchView;
import com.mss.application.fragments.CustomersFragment;
import com.mss.application.fragments.CustomersFragment.OnCustomerSelectedListener;
import com.mss.domain.models.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

public class CustomersActivity extends SherlockFragmentActivity implements OnCustomerSelectedListener, Callback, LoaderCallbacks<List<Customer>> {

	private static final String TAG = CustomersActivity.class.getSimpleName();
	private static final int LOADER_ID_CUSTOMERS = 0;
	
	private CustomersAdapter mCustomersAdapter;
	private String mSearchCriteria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customers);
		try {
			mCustomersAdapter = new CustomersAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		CustomersFragment fragmentCustomers = getCustomersFragment();
		fragmentCustomers.addOnCustomerSelectedListener(this);
		
		getSupportLoaderManager().initLoader(LOADER_ID_CUSTOMERS, null, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_customers, menu);

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
		getSupportLoaderManager().restartLoader(LOADER_ID_CUSTOMERS, null, this);
	}
	
	@Override
	public void onCustomerSelected(Customer customer, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("customer_id", customer.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected CustomersFragment getCustomersFragment() {
		return (CustomersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_customer_list);
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
	public Loader<List<Customer>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_CUSTOMERS:
			try {
				return new CustomersLoader(this, mSearchCriteria);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<Customer>> loader, List<Customer> data) {
		switch (loader.getId()) {
		case LOADER_ID_CUSTOMERS:
			mCustomersAdapter.swapData(data);
			getCustomersFragment().setListAdapter(mCustomersAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Customer>> arg0) {
		mCustomersAdapter.swapData(null);
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
