package com.mss.application;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.MenuInflater;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.RouteFragment;
import com.mss.application.fragments.RouteFragment.OnRouteDateChangedListener;
import com.mss.application.fragments.RouteFragment.OnRoutePointSelectedListener;
import com.mss.application.fragments.RoutePointFragment;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.services.RouteService;
import com.mss.infrastructure.ormlite.DatabaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

public class RouteActivity extends SherlockFragmentActivity implements OnRoutePointSelectedListener, OnRouteDateChangedListener, Callback, LoaderCallbacks<List<RoutePoint>> {

	private static final String TAG = RouteActivity.class.getSimpleName();
	private static final boolean DEBUG = BuildConfig.DEBUG && false;

	/// RoutePoints-specific Loader id
	private static final int LOADER_ID_ROUTE_POINTS = 0;

	private PaneMode mPaneMode;
	private ActionMode mActionMode;
	private RoutePointAdapter mRoutePointAdapter;
	
	private DatabaseHelper mHelper;
	private RouteService mRouteService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_route);

		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mRouteService = new RouteService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		try {
			mRoutePointAdapter = new RoutePointAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		getSupportLoaderManager().initLoader(LOADER_ID_ROUTE_POINTS, null, this);

		// Instantiate the proper PaneMode logic helper

		if (getResources().getBoolean(R.bool.dual_pane))
			mPaneMode = new DualPaneMode(this);
		else
			mPaneMode = new OnePaneMode(this);

		RouteFragment fragmentRoute = getRouteFragment();
		fragmentRoute.addOnRoutePointSelectedListener(this);
		fragmentRoute.addOnRouteDateChangedListener(this);

		mPaneMode.onCreate(savedInstanceState);
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

		getSupportLoaderManager().restartLoader(LOADER_ID_ROUTE_POINTS, null, this);
		
		switch (requestCode) {
			case RoutePointActivity.REQUEST_SHOW_ROUTE_POINT:
				if (data != null) {
					long routePointId = data.getLongExtra(RoutePointActivity.EXTRA_ROUTE_POINT_ID, 0l);
					if (routePointId != 0l) {
						showRoutePointById(routePointId);
					}
				}
				break;
			case RoutePointEditActivity.REQUEST_EDIT_ROUTE_POINT:
				if (resultCode == RESULT_OK) {
					
					RoutePointFragment f = getRoutePointFragment();
					if (f != null) {
						try {
							f.updateContent(mRoutePointAdapter.getItemById(f.getRoutePointId()));
						} catch (Throwable e) {
							Log.e(TAG, e.toString());
						}
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
		inflater.inflate(R.menu.menu_route, menu);

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
			case android.R.id.home: {
					Intent intent=new Intent();
					setResult(RESULT_CANCELED, intent);
					finish();
				}
				return true;
			case R.id.menu_item_copy_from_template: {
					if (!mRouteService.isRouteTemplateOnDateExist(getRouteFragment().getRouteDate())){
						Toast.makeText(this, R.string.alert_route_template_not_found, Toast.LENGTH_LONG).show();
					} else {
						mRouteService.copyRouteFromTemplate(getRouteFragment().getRouteDate());
						getSupportLoaderManager().restartLoader(LOADER_ID_ROUTE_POINTS, null, this);
					}	
					return true;
				}				
			case R.id.menu_item_add: {
				mPaneMode.onAddRoutePoint();
					return true;
				}				
			case R.id.menu_item_delete:
				RoutePointFragment frag = getRoutePointFragment();
				if (frag != null) {
					try {
						mRoutePointAdapter.delete(getRoutePointFragment().getRoutePoint());
					} catch (Throwable e) {						
						
					}
					getSupportLoaderManager().restartLoader(LOADER_ID_ROUTE_POINTS, null, this);
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

	@Override
	public void onRouteDateChanged(Date date) {
		getSupportLoaderManager().restartLoader(LOADER_ID_ROUTE_POINTS, null, this);
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
				Log.e(TAG, e.toString());
			}

			getSupportLoaderManager().restartLoader(LOADER_ID_ROUTE_POINTS, null, this);

			mode.finish();
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		mActionMode = null;

		//FragmentManager manager = getSupportFragmentManager();
		//RouteFragment frag = (RouteFragment) manager.findFragmentById(R.id.fragment_route);
		//RoutePointAdapter adapter = (RoutePointAdapter) frag.getListAdapter();
	}

	@Override
	public Loader<List<RoutePoint>> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_ID_ROUTE_POINTS:
			try {
				return new RouteLoader(this, getRouteFragment().getRouteDate());
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<RoutePoint>> loader, List<RoutePoint> data) {
		switch (loader.getId()) {
		case LOADER_ID_ROUTE_POINTS:
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
			Log.e(TAG, e.toString());
		}
	}

	public void showRoutePointByPosition(int position) {
		if (mRoutePointAdapter.getCount() > 0) {
			int nextPosition = position;
			if (nextPosition >= mRoutePointAdapter.getCount()) {
				// Decrease the position since we don't have enough route points in the list

				nextPosition = mRoutePointAdapter.getCount() - 1;
			}
			RoutePointFragment f = RoutePointFragment.newInstance((RoutePoint) mRoutePointAdapter.getItem(nextPosition));

			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_route_point, f).commitAllowingStateLoss();
		} else {
			// There are no route points, remove the side fragment

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
			RoutePointAdapter routeAdapter = mWeakActivity.get().mRoutePointAdapter;
			routeAdapter.swapData(data);
			mWeakActivity.get().getRouteFragment().setListAdapter(routeAdapter);
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
				// We don't need the RoutePointFragment in OnePaneMode so we get rid of it

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
			DateFormat format = SimpleDateFormat.getDateInstance();
			intent.putExtra(RoutePointEditActivity.KEY_ROUTE_DATE, format.format(activity.getRouteFragment().getRouteDate()));
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
			DateFormat format = SimpleDateFormat.getDateInstance();
			intent.putExtra(RoutePointEditActivity.KEY_ROUTE_DATE, format.format(activity.getRouteFragment().getRouteDate()));
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
