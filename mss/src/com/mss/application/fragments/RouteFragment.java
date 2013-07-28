package com.mss.application.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.application.R;
import com.mss.application.RoutePointAdapter;
import com.mss.domain.models.RoutePoint;

public class RouteFragment extends SherlockListFragment {
	private static final String TAG = RouteFragment.class.getSimpleName();
	
	private final Set<OnRouteDateChangedListener> mOnRouteDateChangedListeners = 
			new HashSet<RouteFragment.OnRouteDateChangedListener>(1);
	private final Set<OnRoutePointSelectedListener> mOnRoutePointSelectedListeners = 
			new HashSet<RouteFragment.OnRoutePointSelectedListener>(1);
	
	private Date mRouteDate = new Date(); 
	private TextView mRouteDateTextView;
	private int mLastPosition;

	public RouteFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_route, container, false);
		
		mRouteDateTextView = (TextView) v.findViewById(R.id.route_date_view_text);
		mRouteDateTextView.setText(DateFormat.getDateFormat(v.getContext()).format(mRouteDate));	
		mRouteDateTextView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				showDatePicker();
			}
		});
		
	    try {
			setListAdapter(new RoutePointAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}
	
	public Date getRouteDate(){
		return mRouteDate;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnRoutePointSelectedListener listener : mOnRoutePointSelectedListeners) {
			listener.onRoutePointSelected((RoutePoint) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnRouteDateChangedListener(OnRouteDateChangedListener listener) {
		mOnRouteDateChangedListeners.add(listener);
	}

	public boolean removeOnRouteDateChangedListener(OnRouteDateChangedListener listener) {
		return mOnRouteDateChangedListeners.remove(listener);
	}
	
	public void addOnRoutePointSelectedListener(OnRoutePointSelectedListener listener) {
		mOnRoutePointSelectedListeners.add(listener);
	}

	public boolean removeOnRoutePointSelectedListener(OnRoutePointSelectedListener listener) {
		return mOnRoutePointSelectedListeners.remove(listener);
	}

	public interface OnRouteDateChangedListener {
		/**
		 * Called when the user selects a note entry on the list
		 * 
		 * @param n
		 *            {@link Note} instance
		 * @param position
		 *            its position on the list
		 * @param id
		 *            its id
		 */
		void onRouteDateChanged(Date date);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnRoutePointSelectedListener {
		/**
		 * Called when the user selects a note entry on the list
		 * 
		 * @param n
		 *            {@link Note} instance
		 * @param position
		 *            its position on the list
		 * @param id
		 *            its id
		 */
		void onRoutePointSelected(RoutePoint routePoint, int position, long id);
	}
	
	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();
		/**
	     * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		calender.setTime(mRouteDate);
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getActivity().getSupportFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mRouteDateTextView = (TextView) getView().findViewById(R.id.route_date_view_text);
			final Calendar c = Calendar.getInstance();
			c.set(year, monthOfYear, dayOfMonth);
			mRouteDate = c.getTime();

			mRouteDateTextView.setText(DateFormat.getDateFormat(getView().getContext()).format(mRouteDate));
			for (OnRouteDateChangedListener listener : mOnRouteDateChangedListeners) {
				listener.onRouteDateChanged(mRouteDate);
			}
		}
	};
}
