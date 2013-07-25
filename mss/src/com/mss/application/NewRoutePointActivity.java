package com.mss.application;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class NewRoutePointActivity extends Activity {

	private TextView mCustomer;
	private TextView mShippingAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_route_point);
		
		mCustomer = (TextView) findViewById(R.id.customer_edit_text);
		mCustomer.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	    	}
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_route_point, menu);
		return true;
	}

}
