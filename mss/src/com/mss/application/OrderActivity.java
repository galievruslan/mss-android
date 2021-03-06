package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.OrderItemsFragment;
import com.mss.domain.models.Order;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class OrderActivity extends SherlockFragmentActivity implements OnTabChangeListener, LoaderCallbacks<Order>{

	private static final String TAG = OrderEditActivity.class.getSimpleName();
    
	public static final String TAB_GENERAL = "General";
    public static final String TAB_DETAILS = "Details";
    public static final String TAB_NOTES = "Notes";
    private TabHost mTabHost;
    private int mCurrentTab;

    public static final int LOADER_ID_ORDER = 0;
	
	public static final String KEY_ORDER_ID = "id";
	
	public static final int REQUEST_SHOW_ORDER = 30;
	
	private Long mOrderId;
	private Order mOrder;
		
	private TextView mOrderDate;
	private TextView mOrderShippingDate;
	private TextView mOrderShippingTime;
	private TextView mOrderCustomer;
	private TextView mOrderShippingAddress;
	private TextView mOrderPriceList;
	private TextView mOrderWarehouse;
	private TextView mOrderNotes;
	
	private DatabaseHelper mHelper;
	private OrderService mOrderService;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
						
		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mOrderService = new OrderService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		mOrderDate = (TextView)findViewById(R.id.order_date_text_view);
		mOrderShippingDate = (TextView)findViewById(R.id.order_shipping_date_text_view);
		mOrderShippingTime = (TextView)findViewById(R.id.order_shipping_time_text_view);
		mOrderCustomer = (TextView)findViewById(R.id.order_customer_name_text_view);
		mOrderShippingAddress = (TextView)findViewById(R.id.order_shipping_address_text_view);
		mOrderPriceList = (TextView)findViewById(R.id.order_price_list_text_view);
		mOrderWarehouse = (TextView)findViewById(R.id.order_warehouse_text_view);
		mOrderNotes = (TextView)findViewById(R.id.order_notes_text_view);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        setupTabs();
        
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);        
        
		mOrderId = getIntent().getLongExtra(getString(R.string.key_id), 0);
		getSupportLoaderManager().initLoader(LOADER_ID_ORDER, null, this);
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);		
	}	
	
	protected OrderItemsFragment getOrderItemsFragment() {
		return (OrderItemsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_order_item_list);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		  // Save UI state changes to the savedInstanceState.
		  // This bundle will be passed to onCreate if the process is
		  // killed and restarted.
		  savedInstanceState.putBoolean("restart", true);
	}
	
	private void setupTabs() {
        mTabHost.setup();
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
            mCurrentTab = 0;
            return;
        }
        if (TAB_DETAILS.equals(tabId)) {
            mCurrentTab = 1;
            return;
        }
        if (TAB_NOTES.equals(tabId)) {
            mCurrentTab = 2;
            return;
        }
    }

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		if (mOrderService.canBeEditedOrDeleted(mOrderId)) {
			getSupportMenuInflater().inflate(R.menu.menu_order, menu);
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == OrderEditActivity.REQUEST_EDIT_ORDER) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {	        		        	
	        	getSupportLoaderManager().restartLoader(LOADER_ID_ORDER, null, this);
	        }
	    }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_item_edit:
			if (mOrder != null)
				try {
					Intent intent = new Intent(this, OrderEditActivity.class);
					intent.putExtra(OrderEditActivity.KEY_ORDER_ID, mOrderId);
					startActivityForResult(intent, OrderEditActivity.REQUEST_EDIT_ORDER);
				} catch (Throwable e) {
					Log.e(TAG, e.getMessage());
				}
				finish();

			return true;
		case R.id.menu_item_delete: {
			if (mOrder != null) {
				new AlertDialog.Builder(this)
	        	.setTitle(R.string.dialog_delete_confirmation_title) 
	        	.setMessage(R.string.dialog_delete_confirmation_message) 
	        	.setIcon(R.drawable.ic_action_delete)
	        	.setPositiveButton(R.string.dialog_delete_confirmation_positive_button, 
	        			new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int whichButton) { 
	        			mOrderService.deleteOrder(mOrder);
	    				finish();
	        			dialog.dismiss();
	        		}   
	        	})
	        	.setNegativeButton(R.string.dialog_delete_confirmation_negative_button, new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int which) {
	        			dialog.dismiss();
	        		}
	        	})
	        	.create()
	        	.show();				
			}
			return true;
		}	
		default:
			return false;
		}
	}

	@Override
	public Loader<Order> onCreateLoader(int id, Bundle bundle) {
		switch (id) {
		case LOADER_ID_ORDER:
			try {
				return new OrderLoader(this, mOrderId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Order> orders, Order order) {
		if (order != null)
			mOrder = order;
		
		if (mOrder != null) {
			java.text.DateFormat dateFormat = DateFormat.getDateFormat(getApplicationContext());
			java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getApplicationContext());
			
			mOrderDate.setText(dateFormat.format(mOrder.getOrderDate()));
			mOrderShippingDate.setText(dateFormat.format(mOrder.getShippingDate()));
			mOrderShippingTime.setText(timeFormat.format(mOrder.getShippingDate()));
			mOrderCustomer.setText(mOrder.getCustomerName());
			mOrderShippingAddress.setText(mOrder.getShippingAddressName());
			mOrderPriceList.setText(mOrder.getPriceListName());
			mOrderWarehouse.setText(mOrder.getWarehouseName());	
			mOrderNotes.setText(mOrder.getNote());
			getOrderItemsFragment().refresh(mOrder.getId());
		}
	}

	@Override
	public void onLoaderReset(Loader<Order> orders) {
		mOrder = null;
	}	
}