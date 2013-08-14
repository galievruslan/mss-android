package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.mss.application.fragments.ShippingAddressesFragment;
import com.mss.application.fragments.ShippingAddressesFragment.OnShippingAddressSelectedListener;
import com.mss.domain.models.ShippingAddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

public class ShippingAddressesActivity extends SherlockFragmentActivity implements OnShippingAddressSelectedListener, Callback, LoaderCallbacks<List<ShippingAddress>> {

	private static final String TAG = ShippingAddressesActivity.class.getSimpleName();
	private static final int LOADER_ID_SHIPPING_ADDRESSES = 0;
	
	private Long mCustomerId;
	ShippingAddressAdapter mShippingAddressesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shipping_addresses);
		try {
			mShippingAddressesAdapter = new ShippingAddressAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		mCustomerId = getIntent().getLongExtra("customer_id", 0l);
				
		ShippingAddressesFragment fragmentShippingAddresses = getShippingAddressesFragment();
		fragmentShippingAddresses.addOnShippingAddressSelectedListener(this);
		
		Bundle bundle = new Bundle();
		bundle.putLong("customer_id", mCustomerId);
		getSupportLoaderManager().initLoader(LOADER_ID_SHIPPING_ADDRESSES, bundle, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onShippingAddressSelected(ShippingAddress shippingAddress, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("shipping_address_id", shippingAddress.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected ShippingAddressesFragment getShippingAddressesFragment() {
		return (ShippingAddressesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_shipping_address_list);
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
	public Loader<List<ShippingAddress>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_SHIPPING_ADDRESSES:
			try {
				return new ShippingAddressesLoader(this, arg1.getLong("customer_id"));
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<ShippingAddress>> loader, List<ShippingAddress> data) {
		switch (loader.getId()) {
		case LOADER_ID_SHIPPING_ADDRESSES:
			mShippingAddressesAdapter.swapData(data);
			getShippingAddressesFragment().setListAdapter(mShippingAddressesAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<ShippingAddress>> arg0) {
		mShippingAddressesAdapter.swapData(null);
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
