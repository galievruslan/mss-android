package com.mss.application;

import java.util.Calendar;

import com.mss.domain.models.RoutePoint;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class RouteActivity extends Activity  {

	private TextView mDateView;
	private ImageButton mButtonPickUpRouteDate;
	private ListView mListView;
		
	private int year, month, day;
	static final int DATE_DIALOG_ID = 999;
	
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    setContentView(R.layout.activity_route);
	    setCurrentDate();
	    
	    mButtonPickUpRouteDate = (ImageButton) findViewById(R.id.button_pick_up_route_date);
	    mButtonPickUpRouteDate.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {	    		
	    		showDialog(DATE_DIALOG_ID);
	    	}
	    });
	    
	    RoutePoint[] values = new RoutePoint[] { new RoutePoint(), new RoutePoint() };
	    RoutePointsAdapter adapter = new RoutePointsAdapter(this, values);
	    
	    mListView = (ListView) findViewById(R.id.list);
	    mListView.setAdapter(adapter);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DATE_DIALOG_ID:
				// set date picker as current date
				return new DatePickerDialog(this, datePickerListener, year, month, day);
		}
		
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			final Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			mDateView.setText(DateFormat.getDateFormat(getApplicationContext()).format(calendar.getTime()));
		}
	};

	// Set current date on datePicker
	public void setCurrentDate() {
		mDateView = (TextView) findViewById(R.id.label_route_date);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DATE);

		mDateView.setText(DateFormat.getDateFormat(getApplicationContext()).format(c.getTime()));
	}
}
