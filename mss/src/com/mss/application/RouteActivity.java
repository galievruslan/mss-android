package com.mss.application;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.MenuInflater;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.RouteFragment;
import com.mss.application.fragments.RouteFragment.OnRoutePointSelectedListener;
import com.mss.application.fragments.RoutePointFragment;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.RouteService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class RouteActivity extends SherlockFragmentActivity implements OnRoutePointSelectedListener, Callback, LoaderCallbacks<List<RoutePoint>> {

private static final String TAG = RouteActivity.class.getSimpleName();
private static final boolean DEBUG = BuildConfig.DEBUG && false;

/// Note-specific Loader id
private static final int LOADER_ID_NOTES = 0;

private PaneMode mPaneMode;
private ActionMode mActionMode;
private RoutePointAdapter mRoutePointAdapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

setContentView(R.layout.activity_route);

mRoutePointAdapter = new RoutePointAdapter(this);
getSupportLoaderManager().initLoader(LOADER_ID_NOTES, null, this);

// Instantiate the proper PaneMode logic helper

if (getResources().getBoolean(R.bool.dual_pane))
	mPaneMode = new DualPaneMode(this);
else
	mPaneMode = new OnePaneMode(this);

RouteFragment fragmentRoute = getRouteFragment();
fragmentRoute.addOnRoutePointSelectedListener(this);

mPaneMode.onCreate(savedInstanceState);
}

protected RouteFragment getRouteFragment() {
return (RouteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_route);
}

protected RoutePointFragment getRoutePointFragment() {
return (RoutePointFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_route_point);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
super.onActivityResult(requestCode, resultCode, data);

getSupportLoaderManager().restartLoader(LOADER_ID_NOTES, null, this);

switch (requestCode) {
case RoutePointActivity.REQUEST_SHOW_ROUTE_POINT:
	// We are coming back from the NoteActivity, so we should select
	// a proper note from the list

	if (data != null) {
		long noteId = data.getLongExtra(RoutePointActivity.EXTRA_ROUTE_POINT_ID, 0l);
		if (noteId != 0l) {
			showRoutePointById(noteId);
		}
	}
	break;
case RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT:
	// We are coming back from the NoteEditActivity, so let's update the
	// content of the fragment

	RoutePointFragment f = getRoutePointFragment();
	if (f != null) {
		try {
			f.updateContent(mRoutePointAdapter.getItemById(f.getRoutePointId()));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	break;
default:
	break;
}
}

@Override
public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
MenuInflater inflater = getSupportMenuInflater();
inflater.inflate(R.menu.menu_list, menu);

return true;
}

/**
* {@inheritDoc}
*/
@Override
public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {

// Note that we handle Edit and Delete items here, even if they were
// added by the NoteFragment.

switch (item.getItemId()) {
case R.id.menu_item_add:
	mPaneMode.onAddRoutePoint();
	return true;
case R.id.menu_item_delete:
	RoutePointFragment frag = getRoutePointFragment();
	if (frag != null) {
		try {
			mRoutePointAdapter.delete(getRoutePointFragment().getRoutePoint());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSupportLoaderManager().restartLoader(LOADER_ID_NOTES, null, this);
	}

	return true;
default:
	return false;
}
}

@Override
public void onRoutePointSelected(RoutePoint routePoint, int position, long id) {
if (DEBUG)
	Log.d(TAG, "onNoteSelected: " + position);

if (mActionMode != null)
	mActionMode.finish();

mPaneMode.onRoutePointSelected(routePoint, position, id);
}

public void onNoteDeleted(int position) {

}

@Override
public boolean onCreateActionMode(ActionMode mode,
		com.actionbarsherlock.view.Menu menu) {
	MenuInflater inflater = getSupportMenuInflater();
	inflater.inflate(R.menu.action_mode_list, menu);

	mActionMode = mode;

	return true;
}

@Override
public boolean onPrepareActionMode(ActionMode mode,
		com.actionbarsherlock.view.Menu menu) {
	return false;
}

@Override
public boolean onActionItemClicked(ActionMode mode, com.actionbarsherlock.view.MenuItem item) {
switch (item.getItemId()) {
case R.id.menu_item_delete:
	FragmentManager manager = getSupportFragmentManager();
	RouteFragment frag = (RouteFragment) manager.findFragmentById(R.id.fragment_route);
	RoutePointAdapter adapter = (RoutePointAdapter) frag.getListAdapter();
	try {
		adapter.delete(getRoutePointFragment().getRoutePoint());
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	getSupportLoaderManager().restartLoader(LOADER_ID_NOTES, null, this);

	mode.finish();
	return true;
default:
	return false;
}
}

@Override
public void onDestroyActionMode(ActionMode mode) {
mActionMode = null;

FragmentManager manager = getSupportFragmentManager();
RouteFragment frag = (RouteFragment) manager.findFragmentById(R.id.fragment_route);
RoutePointAdapter adapter = (RoutePointAdapter) frag.getListAdapter();
}

@Override
public Loader<List<RoutePoint>> onCreateLoader(int id, Bundle args) {
switch (id) {
case LOADER_ID_NOTES:
	return new RouteLoader(this, new Date());
default:
	return null;
}
}

@Override
public void onLoadFinished(Loader<List<RoutePoint>> loader, List<RoutePoint> data) {
switch (loader.getId()) {
case LOADER_ID_NOTES:
	mPaneMode.onLoadFinished(loader, data);
	break;
default:
	break;
}
}

@Override
public void onLoaderReset(Loader<List<RoutePoint>> loader) {
mRoutePointAdapter.swapData(null);
}

public void showRoutePointById(long id) {
	RoutePoint n;
	try {
		n = mRoutePointAdapter.getItemById(id);
		mPaneMode.onRoutePointSelected(n, -1, id);
	} catch (Throwable e) {
		e.printStackTrace();
	}
}

public void showRoutePointByPosition(int position) {
if (mRoutePointAdapter.getCount() > 0) {
	int nextPosition = position;
	if (nextPosition >= mRoutePointAdapter.getCount()) {
		// Decrease the position since we don't have enough notes in the list

		nextPosition = mRoutePointAdapter.getCount() - 1;
	}
	RoutePointFragment f = RoutePointFragment.newInstance((RoutePoint) mRoutePointAdapter.getItem(nextPosition));

	getSupportFragmentManager().beginTransaction().replace(R.id.fragment_route_point, f).commitAllowingStateLoss();
} else {
	// There are no notes, remove the side fragment

	RoutePointFragment f = getRoutePointFragment();
	if (f != null) {
		getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
	}
}
}

/**
* Hides the logic that depends on the pane mode
*/
private static abstract class PaneMode {

protected final WeakReference<RouteActivity> mWeakActivity;

public PaneMode(RouteActivity activity) {
	mWeakActivity = new WeakReference<RouteActivity>(activity);
}

/**
 * Called by the {@link AbsNoteActivity#onCreate(Bundle)} method
 */
public abstract void onCreate(Bundle savedInstanceState);

/**
 * Called by the {@link AbsNoteActivity#onNoteSelected(Note, int, long)} method
 * 
 * @param n
 *            the {@link Note} instance that was selected
 * @param position
 *            the position on the list
 * @param id
 *            {@link Note} id
 */
public abstract void onRoutePointSelected(RoutePoint n, int position, long id);

/**
 * Called when a {@link Note} was added.
 */
public abstract void onAddRoutePoint();

/**
 * Called when the {@link Loader} finishes its work
 * 
 * @param loader the {@link Loader} object
 * @param data returned list of {@link Note Notes}
 */
public void onLoadFinished(Loader<List<RoutePoint>> loader, List<RoutePoint> data) {
	RoutePointAdapter noteAdapter = mWeakActivity.get().mRoutePointAdapter;
	noteAdapter.swapData(data);
	mWeakActivity.get().getRouteFragment().setListAdapter(noteAdapter);
}
}

/**
* One-pane mode specific logic
*/
private static class OnePaneMode extends PaneMode {

public OnePaneMode(RouteActivity activity) {
	super(activity);
}

@Override
public void onCreate(Bundle savedInstanceState) {
	RouteActivity activity = mWeakActivity.get();

	if (savedInstanceState == null) {
		// Activity is being recreated
	}

	RoutePointFragment f = (RoutePointFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_route_point);
	if (f != null) {
		// We don't need the NoteFragment in OnePaneMode so we get rid of it

		activity.getSupportFragmentManager().beginTransaction().remove(f).commit();
	}
}

@Override
public void onRoutePointSelected(RoutePoint n, int position, long id) {
	RouteActivity activity = mWeakActivity.get();

	Intent intent = new Intent(activity, RoutePointActivity.class);
	intent.putExtra(activity.getString(R.string.key_id), id);
	activity.startActivityForResult(intent, RoutePointActivity.REQUEST_SHOW_ROUTE_POINT);
}

@Override
public void onAddRoutePoint() {
	RouteActivity activity = mWeakActivity.get();

	Intent intent = new Intent(activity, RoutePointEditActivity.class);
	activity.startActivityForResult(intent, 0);
}
}

/**
* Dual-pane mode specific logic
*/
private static class DualPaneMode extends PaneMode {

public DualPaneMode(RouteActivity activity) {
	super(activity);
}

/**
 * In dual pane mode we want to show a NoteFragment if there's one in the database
 */
@Override
public void onCreate(Bundle savedInstanceState) {
	if (savedInstanceState == null) {
		// Activity is being recreated
	}
}



@Override
public void onRoutePointSelected(RoutePoint n, int position, long id) {
	RouteActivity activity = mWeakActivity.get();

	RoutePointFragment currentFragment = activity.getRoutePointFragment();

	if (currentFragment == null || currentFragment.getRoutePointId() != id) {
		// Let's swap the NoteFragment with the selected one

		activity.getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_route_point, RoutePointFragment.newInstance(n)).commit();
	}
}

@Override
public void onAddRoutePoint() {
	RouteActivity activity = mWeakActivity.get();

	Intent intent = new Intent(activity, RoutePointEditActivity.class);
	activity.startActivityForResult(intent, 0);
}

@Override
public void onLoadFinished(Loader<List<RoutePoint>> loader, List<RoutePoint> data) {
	super.onLoadFinished(loader, data);

	RouteActivity activity = mWeakActivity.get();

	// In DualPaneMode we want to show a side NoteFragment
	// Remember that we can not do FragmentTransactions in onLoadFinished

	activity.showRoutePointByPosition(activity.getRouteFragment().getLastClickedPosition());
}
}
}
