package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.RoutePointFragment;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.RoutePointService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;

public class RoutePointActivity extends SherlockFragmentActivity {

	public static final int REQUEST_SHOW_ROUTE_POINT = 1;
	public static final long ROUTE_POINT_ID_NEW = 0;
	public static final String EXTRA_ROUTE_POINT_ID = "route_point_id";

	private DatabaseHelper mHelper;
	private RoutePointService mRoutePointService;
	private RoutePointFragment mRoutePointFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		long id = getIntent().getLongExtra(getString(R.string.key_id), ROUTE_POINT_ID_NEW);

		if (getResources().getBoolean(R.bool.dual_pane)) {
			// Let's get back to the NoteListActivity since it should show both
			// fragments at the same time
			
			Intent data = new Intent();
			data.putExtra(EXTRA_ROUTE_POINT_ID, id);
			setResult(0, data);
			finish();
		}

		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		mRoutePointService = new RoutePointService(mHelper);
		if (id != ROUTE_POINT_ID_NEW) {			
			RoutePoint routePoint = null;
			try {
				routePoint = mRoutePointService.getPointById(id);
			} catch (Throwable e) {
				e.printStackTrace();
			}

			mRoutePointFragment = RoutePointFragment.newInstance(routePoint);

			getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mRoutePointFragment).commit();
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
			RoutePoint routePoint = null;
			try {
				routePoint = mRoutePointService.getPointById(mRoutePointFragment.getRoutePointId());
			} catch (Throwable e) {
				e.printStackTrace();
			}

			mRoutePointFragment.updateContent(routePoint);

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
				TaskStackBuilder.from(this).addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		case R.id.menu_item_edit:
			if (mRoutePointFragment != null) {
				Intent i = new Intent(this, RoutePointEditActivity.class);
				i.putExtra(getString(R.string.key_id), mRoutePointFragment.getRoutePointId());
				startActivityForResult(i, RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT);
			}
			return true;
		case R.id.menu_item_delete:
			try {
				mRoutePointService.deletePoint(mRoutePointFragment.getRoutePoint());
			} catch (Throwable e) {
				e.printStackTrace();
			}
			finish();
		default:
			return false;
		}
	}
}