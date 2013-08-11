package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.OrderFragment;
import com.mss.domain.models.Order;
import com.mss.domain.services.OrderService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class OrderActivity extends SherlockFragmentActivity {

	private static final String TAG = OrderActivity.class.getSimpleName();
    	
	public static final int REQUEST_SHOW_ORDER = 1;
	public static final long ORDER_ID_NEW = 0;
	public static final String EXTRA_ORDER_ID = "order_id";
	
	private DatabaseHelper mHelper;
	private OrderService mOrderService;
	private OrderFragment mOrderFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		long id = getIntent().getLongExtra(getString(R.string.key_id), ORDER_ID_NEW);

		if (getResources().getBoolean(R.bool.dual_pane)) {
			// Let's get back to the NoteListActivity since it should show both
			// fragments at the same time
			
			Intent data = new Intent();
			data.putExtra(EXTRA_ORDER_ID, id);
			setResult(0, data);
			finish();
		}

		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mOrderService = new OrderService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		if (id != ORDER_ID_NEW) {			
			Order order = null;
			try {
				order = mOrderService.getById(id);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}

			mOrderFragment = OrderFragment.newInstance(order);

			getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mOrderFragment).commit();
		}

		// Let's show the application icon as the Up button
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT:
			// We are coming back from the NoteEditActivity
			Order order = null;
			try {
				order = mOrderService.getById(mOrderFragment.getOrderId());
			} catch (Throwable e) {
				e.printStackTrace();
			}

			mOrderFragment.updateContent(order);

			break;
		default:
			break;
		}
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
		case R.id.menu_item_edit:
			if (mOrderFragment != null) {
				Order order = null;				
				try {
					order = mOrderService.getById(mOrderFragment.getOrderId());
				} catch (Throwable e) {
					e.printStackTrace();
				}				
				
				Intent i = new Intent(this, RoutePointEditActivity.class);
				i.putExtra(getString(R.string.key_id), order.getId());
				startActivityForResult(i, RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT);
			}
			return true;
		case R.id.menu_item_delete:
			try {
				mOrderService.deleteOrder(mOrderFragment.getOrder());
			} catch (Throwable e) {
				e.printStackTrace();
			}
			finish();
		default:
			return false;
		}
	}	
}
