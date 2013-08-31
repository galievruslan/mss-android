package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.mss.application.fragments.RoutePointsOrdersFragment;
import com.mss.application.fragments.RoutePointsOrdersFragment.OnOrderSelectedListener;
import com.mss.domain.models.Order;
import com.mss.domain.models.RoutePoint;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class RoutePointActivity extends SherlockFragmentActivity implements OnTabChangeListener, OnOrderSelectedListener, LoaderCallbacks<RoutePoint> {

	private static final String TAG = RoutePointActivity.class.getSimpleName();
	
	public static final String TAB_GENERAL = "General";
    public static final String TAB_DETAILS = "Details";
    private TabHost mTabHost;
    private int mCurrentTab;
    
    public static final int LOADER_ID_ROUTE_POINT = 0;
    
	public static final int REQUEST_SHOW_ROUTE_POINT = 1;
	public static final long ROUTE_POINT_ID_NEW = 0;
	public static final String EXTRA_ROUTE_POINT_ID = "route_point_id";
	
	private long mRoutePointId;
	private RoutePoint mRoutePoint;
	
	private TextView mName;
	private TextView mAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_point);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        setupTabs();
        
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);     

        mName = (TextView) findViewById(R.id.route_point_name_text_view);
        mAddress = (TextView) findViewById(R.id.route_point_address_text_view);
        
        mRoutePointId = getIntent().getLongExtra(getString(R.string.key_id), ROUTE_POINT_ID_NEW);
		getSupportLoaderManager().initLoader(LOADER_ID_ROUTE_POINT, null, this);
        
		RoutePointsOrdersFragment fragment = getRoutePointsOrdersFragment();		
		fragment.addOnOrderSelectedListener(this);
		
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);;
			//getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
	}
	
	protected RoutePointsOrdersFragment getRoutePointsOrdersFragment() {
		return (RoutePointsOrdersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_order_list);
	}
	
	private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_GENERAL, R.string.label_tab_general, R.id.tab_general));
        mTabHost.addTab(newTab(TAB_DETAILS, R.string.label_tab_details, R.id.tab_details));
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
    }
    
    @Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_route_point, menu);
		return true;
	}
	
	@Override
	public void onOrderSelected(Order order, int position, long id) {
		Intent intent = new Intent(this, OrderEditActivity.class);
		intent.putExtra(OrderEditActivity.KEY_ORDER_ID, id);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT:
			// We are coming back from the NoteEditActivity
			
			break;
		default:
			break;
		}
		
		getSupportLoaderManager().restartLoader(LOADER_ID_ROUTE_POINT, null, this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// ActionBar's Home button clicked

			Intent upIntent = new Intent(this, RouteActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this).addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		case R.id.menu_item_edit: {
				Intent i = new Intent(this, RoutePointEditActivity.class);				
				i.putExtra(RoutePointEditActivity.KEY_ROUTE_POINT_ID, mRoutePointId);
				startActivityForResult(i, RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT);
			}
			return true;
		case R.id.menu_item_delete:
			try {
				//mRoutePointService.deletePoint(mRoutePointFragment.getRoutePoint());
			} catch (Throwable e) {
				e.printStackTrace();
			}
			finish();
		case R.id.menu_item_add:
			Intent intent = new Intent(this, OrderEditActivity.class);
			intent.putExtra(OrderEditActivity.KEY_ROUTE_POINT_ID, mRoutePointId);
			startActivityForResult(intent, 0);
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public Loader<RoutePoint> onCreateLoader(int id, Bundle bundle) {
		switch (id) {
		case LOADER_ID_ROUTE_POINT:
			try {
				return new RoutePointLoader(this, mRoutePointId);
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
			mName.setText(mRoutePoint.getShippingAddressName());
			mAddress.setText(mRoutePoint.getShippingAddressValue());		
			
			getRoutePointsOrdersFragment().refresh(mRoutePointId);
		}
	}

	@Override
	public void onLoaderReset(Loader<RoutePoint> arg0) {
		mRoutePoint = null;	
	}
}
