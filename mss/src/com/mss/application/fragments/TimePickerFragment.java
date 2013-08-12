package com.mss.application.fragments;

import android.os.Bundle;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment {
	 OnTimeSetListener onTimeSet;

	 public TimePickerFragment() {
	 }

	 public void setCallBack(OnTimeSetListener onTime) {
		 onTimeSet = onTime;
	 }

	 private int hour, minute;

	 @Override
	 public void setArguments(Bundle args) {
	  super.setArguments(args);
	  hour = args.getInt("hour");
	  minute = args.getInt("minute");
	 }

	 @Override
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
	  return new TimePickerDialog(getActivity(), onTimeSet, hour, minute,DateFormat.is24HourFormat(getActivity()));
	 }
}