package com.mss.application;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.route_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	showRoute();
            }
        });
		
		findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	showSettings();
            }
        });
		
		findViewById(R.id.sync_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	showSynchronization();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void showRoute(){
		Intent mainActivity = new Intent(getApplicationContext(), RouteActivity.class);
    	startActivity(mainActivity);
	}
	
	private void showSettings(){
		
	}
	
	private void showSynchronization(){
		Intent mainActivity = new Intent(getApplicationContext(), SynchronizationActivity.class);
    	startActivity(mainActivity);
	}
}
