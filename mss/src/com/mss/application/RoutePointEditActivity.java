package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.services.RoutePointService;
import com.mss.domain.services.ShippingAddressService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class RoutePointEditActivity extends SherlockFragmentActivity implements LoaderCallbacks<RoutePoint> {

	private static final String TAG = RoutePointEditActivity.class.getSimpleName();

	public static final int REQUEST_EDIT_ROUTE_POINT = 5;
	public static final String KEY_ROUTE_POINT_ID = "id";
	public static final int LOADER_ID_ROUTE_POINT = 0;

	private RoutePoint mRoutePoint;
	private EditText mName;
	private EditText mAddress;

	private DatabaseHelper mHelper;
	private RoutePointService mRoutePointService;
	private ShippingAddressService mShippingAddressService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_point_edit);

		mName = (EditText) findViewById(R.id.label_name);
		mAddress = (EditText) findViewById(R.id.label_address);

		long id = getIntent().getLongExtra(getString(R.string.key_id), RoutePointActivity.ROUTE_POINT_ID_NEW);

		if (id != RoutePointActivity.ROUTE_POINT_ID_NEW) {
			Bundle args = new Bundle();
			args.putLong(KEY_ROUTE_POINT_ID, id);
			getSupportLoaderManager().initLoader(LOADER_ID_ROUTE_POINT, args, this);
		}

		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		mRoutePointService = new RoutePointService(mHelper);
		mShippingAddressService = new ShippingAddressService(mHelper);

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
			//int shippigAddressId =  mName.getTag();
			if (mRoutePoint == null) {
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

			return new RoutePointLoader(this, routePointId);
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<RoutePoint> loader, RoutePoint data) {
		mRoutePoint = data;
				
		if (mRoutePoint != null) {
			ShippingAddress shippingAddress;
			try {
				shippingAddress = mShippingAddressService.getById(mRoutePoint.getShippingAddressId());
				mName.setTag(shippingAddress.getId());
				mName.setText(shippingAddress.getName());
				mAddress.setText(shippingAddress.getAddress());
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