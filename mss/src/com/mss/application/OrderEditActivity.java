package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.OrderFragment;
import com.mss.domain.models.Customer;
import com.mss.domain.models.Order;
import com.mss.domain.models.PriceList;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.models.Warehouse;
import com.mss.domain.services.CustomerService;
import com.mss.domain.services.PriceListService;
import com.mss.domain.services.RoutePointService;
import com.mss.domain.services.RouteService;
import com.mss.domain.services.ShippingAddressService;
import com.mss.domain.services.WarehouseService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class OrderEditActivity extends SherlockFragmentActivity implements LoaderCallbacks<Order>, OnTabChangeListener {

	private static final String TAG = OrderEditActivity.class.getSimpleName();
	public static final String TAB_GENERAL = "General";
    public static final String TAB_DETAILS = "Details";
    public static final String TAB_NOTES = "Notes";
	
    private TabHost mTabHost;
    private int mCurrentTab;

	public static final int REQUEST_EDIT_ORDER = 5;
	public static final String KEY_ORDER_ID = "id";
	public static final String KEY_ROUTE_POINT_ID = "route_point_id";
	public static final int LOADER_ID_ORDER = 0;
	
	static final int PICK_PRICE_LIST_REQUEST = 1;
	static final int PICK_WAREHOUSE_REQUEST = 2;
	static final int PICK_PRODUCTS_REQUEST = 3;

	private long mRoutePointId;
	private Order mOrder;
	private EditText mOrderDate;
	private EditText mOrderShippingDate;
	private EditText mOrderShippingTime;
	private EditText mOrderCustomer;
	private EditText mOrderShippingAddress;
	private EditText mOrderPriceList;
	private EditText mOrderWarehouse;
	private EditText mOrderNotes;

	private DatabaseHelper mHelper;
	private RouteService mRouteService;
	private RoutePointService mRoutePointService;
	private CustomerService mCustomerService;
	private ShippingAddressService mShippingAddressService;
	private PriceListService mPriceListService;
	private WarehouseService mWarehouseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_edit);
		
		mOrderDate = (EditText)findViewById(R.id.order_date_edit_text);
		mOrderShippingDate = (EditText)findViewById(R.id.order_shipping_date_edit_text);
		mOrderShippingTime = (EditText)findViewById(R.id.order_shipping_time_edit_text);
		mOrderCustomer = (EditText)findViewById(R.id.order_customer_edit_text);
		mOrderShippingAddress = (EditText)findViewById(R.id.order_shipping_address_edit_text);
		mOrderPriceList = (EditText)findViewById(R.id.order_price_list_edit_text);
		mOrderWarehouse = (EditText)findViewById(R.id.order_warehouse_edit_text);
		mOrderNotes = (EditText)findViewById(R.id.order_notes_edit_text);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        setupTabs();
        
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        updateTab(TAB_GENERAL, R.id.tab_general);

        mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mRouteService = new RouteService(mHelper);
			mRoutePointService = new RoutePointService(mHelper);
			mCustomerService = new CustomerService(mHelper);
			mShippingAddressService = new ShippingAddressService(mHelper);
			mPriceListService = new PriceListService(mHelper);
			mWarehouseService = new WarehouseService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
        
		long id = getIntent().getLongExtra(getString(R.string.key_id), 0);
		if (id != 0) {
			Bundle args = new Bundle();
			args.putLong(KEY_ORDER_ID, id);
			getSupportLoaderManager().initLoader(LOADER_ID_ORDER, args, this);
		} else {
			Long routePointId = getIntent().getLongExtra(KEY_ROUTE_POINT_ID, 0);
			RoutePoint routePoint;
			try {
				routePoint = mRoutePointService.getById(routePointId);
				Route route = mRouteService.getById(routePoint.getRouteId());
				ShippingAddress shippingAddress = mShippingAddressService.getById(routePoint.getShippingAddressId());
				Customer customer = mCustomerService.getById(shippingAddress.getCustomerId());
				PriceList priceList = mPriceListService.getDefault();
				Warehouse warehouse = mWarehouseService.getDefault();
				
				java.text.DateFormat dateFormat = DateFormat.getDateFormat(getApplicationContext());
				java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getApplicationContext());
				mOrderDate.setText(dateFormat.format(route.getDate()));
				mOrderShippingDate.setText(dateFormat.format(route.getDate()));
				mOrderShippingTime.setText(timeFormat.format(route.getDate()));
				mOrderCustomer.setTag(customer.getId());
				mOrderCustomer.setText(customer.getName());
				mOrderShippingAddress.setTag(shippingAddress.getId());
				mOrderShippingAddress.setText(shippingAddress.getName());
				if (priceList != null) {
					mOrderPriceList.setTag(priceList.getId());
					mOrderPriceList.setText(priceList.getName());
				}
				
				if (warehouse != null) {
					mOrderWarehouse.setTag(warehouse.getId());
					mOrderWarehouse.setText(warehouse.getName());
				}
				
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}			
		}

		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}	
	
	private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_GENERAL, R.string.label_tab_general, R.id.tab_general));
        mTabHost.addTab(newTab(TAB_DETAILS, R.string.label_tab_details, R.id.tab_details));
        mTabHost.addTab(newTab(TAB_NOTES, R.string.label_tab_notes, R.id.tab_notes));
    }
    
    private TabSpec newTab(String tag, int labelId, int tabContentId) {
        Log.d(TAG, "buildTab(): tag=" + tag);
 
        TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(getString(labelId));
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }
 
    
    
    @Override
    public void onTabChanged(String tabId) {
        if (TAB_GENERAL.equals(tabId)) {
            updateTab(tabId, R.id.tab_general);
            mCurrentTab = 0;
            return;
        }
        if (TAB_DETAILS.equals(tabId)) {
            updateTab(tabId, R.id.tab_details);
            mCurrentTab = 1;
            return;
        }
        if (TAB_NOTES.equals(tabId)) {
            updateTab(tabId, R.id.tab_notes);
            mCurrentTab = 2;
            return;
        }
    }
 
    private void updateTab(String tabId, int placeholder) {
        //FragmentManager fm = getSupportFragmentManager();
        //Fragment fragment = fm.findFragmentByTag(tabId);
        //if (fragment == null) {
        //    fm.beginTransaction()
        //            .replace(placeholder, new OrderFragment(), tabId)
        //            .commit();
        //}
    }

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_route_point_edit, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_PRICE_LIST_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	//long customerId = data.getLongExtra("customer_id", 0l);
	        	//mCustomer.setTag(customerId);
	        	
	        	//try {
	        	//	Customer customer = mCustomerService.getById(customerId);
	        	//	mCustomer.setText(customer.getName());
	        	//	Iterable<ShippingAddress> shippingAddresses = mShippingAddressService.findByCustomer(customer);
				//	if (IterableHelpers.size(ShippingAddress.class, shippingAddresses) == 1) {
				//		ShippingAddress shippingAddress = shippingAddresses.iterator().next();
				//		mShippinAddress.setTag(shippingAddress.getId());
			    //    	mShippinAddress.setText(shippingAddress.getName());
				//	} else {	        		
				//		mShippinAddress.setTag(0);
			    //    	mShippinAddress.setText("");
				//	}
				//} catch (Throwable e) {
				//	Log.e(TAG, e.getMessage());
				//}	        	
	        }
	    } else if (requestCode == PICK_WAREHOUSE_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	//long shippingAddressId = data.getLongExtra("shipping_address_id", 0l);
	        	//	        	
	        	//mShippinAddress.setTag(shippingAddressId);	        		        	
	        	//try {
	        	//	ShippingAddress shippingAddress = mShippingAddressService.getById(shippingAddressId);
	        	//	mShippinAddress.setText(shippingAddress.getName());
				//	Customer customer = mCustomerService.getById(shippingAddress.getCustomerId());
				//	mCustomer.setTag(customer.getId());
		        //	mCustomer.setText(customer.getName());
				//} catch (Throwable e) {
				//	Log.e(TAG, e.getMessage());
				//}
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
			//if (mOrder == null && mRoutePoint != null) {
			//	ShippingAddress shippingAddress;
			//	try {
			//		shippingAddress = mShippingAddressService.getById((Long)mShippinAddress.getTag());
			//		mRoutePointService.cratePoint(mRouteDate, shippingAddress);
			//	} catch (Throwable e) {
			//		Log.e(TAG, e.getMessage());
			//	}			
				
				//mRoutePoint = new RoutePoint(  mTitle.getText().toString(), mText.getText().toString());
			//} else {
				//mRoutePoint.setTitle(mTitle.getText().toString());
				//mRoutePoint.setText(mText.getText().toString());
			//}

			//onRoutePointSaved(mRoutePoint);

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
	public Loader<Order> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_ID_ORDER:
			long orderId = args.getLong(KEY_ORDER_ID);

			try {
				return new OrderLoader(this, orderId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Order> loader, Order data) {
		mOrder = data;
				
		if (mOrder != null) {
			try {
				java.text.DateFormat dateFormat = DateFormat.getDateFormat(getApplicationContext());
				java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getApplicationContext());
				mOrderDate.setText(dateFormat.format(mOrder.getOrderDate()));
				mOrderShippingDate.setText(dateFormat.format(mOrder.getShippingDate()));
				mOrderShippingTime.setText(timeFormat.format(mOrder.getShippingDate()));
				mOrderCustomer.setTag(mOrder.getCustomerId());
				mOrderCustomer.setText(mOrder.getCustomerName());
				mOrderShippingAddress.setTag(mOrder.getShippingAddressId());
				mOrderShippingAddress.setText(mOrder.getShippingAddressName());
				mOrderPriceList.setTag(mOrder.getPriceListId());
				mOrderPriceList.setText(mOrder.getPriceListName());
				mOrderWarehouse.setTag(mOrder.getWarehouseId());
				mOrderWarehouse.setText(mOrder.getWarehouseName());
				mOrderNotes.setText(mOrder.getNote());
			} catch (Throwable e) {
				e.printStackTrace();
			}		
		}
	}

	@Override
	public void onLoaderReset(Loader<Order> loader) {
		mOrder = null;
	}
}