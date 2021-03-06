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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.application.R;
import com.mss.application.RoutePointAdapter;
import com.mss.domain.models.Order;

public class DailyOrdersFragment extends SherlockListFragment {
	private static final String TAG = DailyOrdersFragment.class.getSimpleName();
	
	private final Set<OnDateChangedListener> mOnDateChangedListeners = 
			new HashSet<DailyOrdersFragment.OnDateChangedListener>(1);
	private final Set<OnOrderSelectedListener> mOnOrderSelectedListeners = 
			new HashSet<DailyOrdersFragment.OnOrderSelectedListener>(1);
	
	private Date mDate = new Date(); 
	private EditText mDateEditText;
	private TextView mCountTextView;
	private TextView mAmountTextView;
	
	private int mLastPosition;

	public DailyOrdersFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_orders, container, false);
		
		mCountTextView = (TextView) v.findViewById(R.id.count_text_view);
		mAmountTextView = (TextView) v.findViewById(R.id.amount_text_view);
		
		mDateEditText = (EditText) v.findViewById(R.id.date_edit_text);
		mDateEditText.setText(DateFormat.getDateFormat(v.getContext()).format(mDate));	
		mDateEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				showDatePicker();
			}
		});
		
		mDateEditText.setKeyListener(null);
		
	    try {
			setListAdapter(new RoutePointAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}
	
	public Date getDate(){
		return mDate;
	}
	
	public void setCount(int count) {
		mCountTextView.setText(String.valueOf(count));
	}
	
	public void setAmount(double amount) {
		mAmountTextView.setText(String.valueOf(amount));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnOrderSelectedListener listener : mOnOrderSelectedListeners) {
			listener.onOrderSelected((Order) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnDateChangedListener(OnDateChangedListener listener) {
		mOnDateChangedListeners.add(listener);
	}

	public boolean removeOnDateChangedListener(OnDateChangedListener listener) {
		return mOnDateChangedListeners.remove(listener);
	}
	
	public void addOnOrderSelectedListener(OnOrderSelectedListener listener) {
		mOnOrderSelectedListeners.add(listener);
	}

	public boolean removeOnOrderSelectedListener(OnOrderSelectedListener listener) {
		return mOnOrderSelectedListeners.remove(listener);
	}

	public interface OnDateChangedListener {
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
		void onDateChanged(Date date);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnOrderSelectedListener {
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
		void onOrderSelected(Order order, int position, long id);
	}
	
	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();
		/**
	     * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		calender.setTime(mDate);
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
			mDateEditText = (EditText) getView().findViewById(R.id.date_edit_text);
			final Calendar c = Calendar.getInstance();
			c.set(year, monthOfYear, dayOfMonth);
			mDate = c.getTime();

			mDateEditText.setText(DateFormat.getDateFormat(getView().getContext()).format(mDate));
			for (OnDateChangedListener listener : mOnDateChangedListeners) {
				listener.onDateChanged(mDate);
			}
		}
	};
}
