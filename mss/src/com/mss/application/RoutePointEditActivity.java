package com.mss.application;

import java.util.Date;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Customer;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.services.CustomerService;
import com.mss.domain.services.RoutePointService;
import com.mss.domain.services.ShippingAddressService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RoutePointEditActivity extends SherlockFragmentActivity implements LoaderCallbacks<RoutePoint> {

	private static final String TAG = RoutePointEditActivity.class.getSimpleName();

	public static final int REQUEST_EDIT_ROUTE_POINT = 5;
	public static final String KEY_ROUTE_POINT_ID = "id";
	public static final String KEY_ROUTE_DATE = "route_date";
	public static final int LOADER_ID_ROUTE_POINT = 0;
	
	static final int PICK_CUSTOMER_REQUEST = 1;
	static final int PICK_SHIPPING_ADDRESS_REQUEST = 2;

	private Date mRouteDate;
	private RoutePoint mRoutePoint;
	private EditText mCustomer;
	private EditText mShippinAddress;

	private DatabaseHelper mHelper;
	private RoutePointService mRoutePointService;
	private CustomerService mCustomerService;
	private ShippingAddressService mShippingAddressService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_point_edit);

		long id = getIntent().getLongExtra(getString(R.string.key_id), RoutePointActivity.ROUTE_POINT_ID_NEW);

		if (id != RoutePointActivity.ROUTE_POINT_ID_NEW) {
			Bundle args = new Bundle();
			args.putLong(KEY_ROUTE_POINT_ID, id);
			getSupportLoaderManager().initLoader(LOADER_ID_ROUTE_POINT, args, this);
		}
		
		mRouteDate = new Date(getIntent().getStringExtra(KEY_ROUTE_DATE));				
		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mRoutePointService = new RoutePointService(mHelper);
			mCustomerService = new CustomerService(mHelper);
			mShippingAddressService = new ShippingAddressService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		mCustomer = (EditText) findViewById(R.id.customer_edit_text);
		mShippinAddress = (EditText) findViewById(R.id.shipping_address_edit_text);
		mCustomer.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent customersActivity = new Intent(getApplicationContext(), CustomersActivity.class);
		    	startActivityForResult(customersActivity, PICK_CUSTOMER_REQUEST);
			}
        });
		
		mShippinAddress.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent shippingAddressesActivity = new Intent(getApplicationContext(), ShippingAddressesActivity.class);
				if (mCustomer.getTag() != null) {
					shippingAddressesActivity.putExtra("customer_id", (Long)mCustomer.getTag());
				}
				startActivityForResult(shippingAddressesActivity, PICK_SHIPPING_ADDRESS_REQUEST);
			}
        });

		// Let's show the application icon as the Up button
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_route_point_edit, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_CUSTOMER_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	long customerId = data.getLongExtra("customer_id", 0l);
	        	mCustomer.setTag(customerId);
	        	
	        	try {
	        		Customer customer = mCustomerService.getById(customerId);
	        		mCustomer.setText(customer.getName());
	        		Iterable<ShippingAddress> shippingAddresses = mShippingAddressService.findByCustomer(customer);
					if (IterableHelpers.size(ShippingAddress.class, shippingAddresses) == 1) {
						ShippingAddress shippingAddress = shippingAddresses.iterator().next();
						mShippinAddress.setTag(shippingAddress.getId());
			        	mShippinAddress.setText(shippingAddress.getName());
					} else {	        		
						mShippinAddress.setTag(0);
			        	mShippinAddress.setText("");
					}
				} catch (Throwable e) {
					Log.e(TAG, e.getMessage());
				}	        	
	        }
	    } else if (requestCode == PICK_SHIPPING_ADDRESS_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	long shippingAddressId = data.getLongExtra("shipping_address_id", 0l);
	        		        	
	        	mShippinAddress.setTag(shippingAddressId);	        		        	
	        	try {
	        		ShippingAddress shippingAddress = mShippingAddressService.getById(shippingAddressId);
	        		mShippinAddress.setText(shippingAddress.getName());
					Customer customer = mCustomerService.getById(shippingAddress.getCustomerId());
					mCustomer.setTag(customer.getId());
		        	mCustomer.setText(customer.getName());
				} catch (Throwable e) {
					Log.e(TAG, e.getMessage());
				}
	        }
	    }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, RouteActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.from(this).addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		case R.id.menu_item_save:
			if (mRoutePoint == null && mRouteDate != null) {
				ShippingAddress shippingAddress;
				try {
					shippingAddress = mShippingAddressService.getById((Long)mShippinAddress.getTag());
					mRoutePointService.cratePoint(mRouteDate, shippingAddress);
				} catch (Throwable e) {
					Log.e(TAG, e.getMessage());
				}			
				
				//mRoutePoint = new RoutePoint(  mTitle.getText().toString(), mText.getText().toString());
			} else {
				//mRoutePoint.setTitle(mTitle.getText().toString());
				//mRoutePoint.setText(mText.getText().toString());
			}

			onRoutePointSaved(mRoutePoint);

			return true;
		default:
			return false;
		}
	}

	public void onRoutePointSaved(RoutePoint routePoint) {
		try {
			mRoutePointService.savePoint(routePoint);
		} catch (Throwable e) {
			Log.e(TAG, "Unable to create or update route point: " + routePoint);
		}
		finish();
	}

	@Override
	public Loader<RoutePoint> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_ID_ROUTE_POINT:
			long routePointId = args.getLong(KEY_ROUTE_POINT_ID);

			try {
				return new RoutePointLoader(this, routePointId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<RoutePoint> loader, RoutePoint data) {
		mRoutePoint = data;
				
		if (mRoutePoint != null) {
			try {
				ShippingAddress shippingAddress = mShippingAddressService.getById(mRoutePoint.getShippingAddressId());
				Customer customer = mCustomerService.getById(shippingAddress.getCustomerId());
				mCustomer.setTag(customer.getId());
				mCustomer.setText(customer.getName());
				mShippinAddress.setTag(shippingAddress.getAddress());
				mShippinAddress.setText(shippingAddress.getAddress());
			} catch (Throwable e) {
				e.printStackTrace();
			}		
		}
	}

	@Override
	public void onLoaderReset(Loader<RoutePoint> loader) {
		mRoutePoint = null;
	}
}