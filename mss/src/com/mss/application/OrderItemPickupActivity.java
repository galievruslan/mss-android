package com.mss.application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.R;
import com.mss.domain.models.Customer;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.services.CustomerService;
import com.mss.domain.services.RoutePointService;
import com.mss.domain.services.ShippingAddressService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OrderItemPickupActivity extends SherlockFragmentActivity implements LoaderCallbacks<OrderPickupItem> {

	private static final String TAG = OrderItemPickupActivity.class.getSimpleName();

	public static final int REQUEST_EDIT_ORDER_PICKUP_ITEM = 5;
	public static final String KEY_ID = "id";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_UNIT_OF_MEASURE_ID = "unit_of_measure_id";
	public static final String KEY_PRICE = "price";
	public static final String KEY_COUNT = "count";
		
	public static final int LOADER_ID_ORDER_PICKUP_ITEM = 0;
	
	static final int PICK_UNIT_OF_MEASURE_REQUEST = 1;

	private OrderPickupItem mOrderPickupItem;
	private TextView mDescription;
	private TextView mPrice;
	private TextView mCount;
	private TextView mAmount;
	private EditText mUnitOfMeasure;

	private DatabaseHelper mHelper;
	private RoutePointService mRoutePointService;
	private CustomerService mCustomerService;
	private ShippingAddressService mShippingAddressService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_item_pickup);

		long id = getIntent().getLongExtra(getString(R.string.key_id), RoutePointActivity.ROUTE_POINT_ID_NEW);
		
		mDescription = (TextView) findViewById(R.id.description_text_view);
		mPrice = (TextView) findViewById(R.id.price_text_view);
		mCount = (TextView) findViewById(R.id.count_text_view);
		mAmount = (TextView) findViewById(R.id.amount_text_view);
		mUnitOfMeasure = (EditText) findViewById(R.id.uom_edit_text);
		mUnitOfMeasure.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent customersActivity = new Intent(getApplicationContext(), CustomersActivity.class);
		    	startActivityForResult(customersActivity, PICK_UNIT_OF_MEASURE_REQUEST);
			}
        });

		// Let's show the application icon as the Up button
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_order_item_pickup, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_UNIT_OF_MEASURE_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	long uomId = data.getLongExtra("unit_of_measure_id", 0l);
	        	
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
	    } 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, RouteActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this).addNextIntent(upIntent).startActivities();
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
			return true;
		default:
			return false;
		}
	}

	@Override
	public Loader<OrderPickupItem> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_ID_ORDER_PICKUP_ITEM:

			try {
				return new OrderPickupItemLoader(this, routePointId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<OrderPickupItem> loader, OrderPickupItem data) {
		mOrderPickupItem = data;
				
		if (mOrderPickupItem != null) {
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
	public void onLoaderReset(Loader<OrderPickupItem> loader) {
		mOrderPickupItem = null;
	}
}