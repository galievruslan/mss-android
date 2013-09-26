package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mss.application.fragments.MainMenuFragment;
import com.mss.application.fragments.MainMenuFragment.OnMenuSelectedListener;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends SherlockFragmentActivity implements OnMenuSelectedListener {

	private static final String TAG = CustomersActivity.class.getSimpleName();
	
	MainMenuAdapter mMainMenuAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			mMainMenuAdapter = new MainMenuAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		MainMenuFragment fragmentMenu = getMainMenuFragment();
		fragmentMenu.addOnMenuSelectedListener(this);
		fragmentMenu.setListAdapter(mMainMenuAdapter);
	}
	
	@Override
	public void onMenuSelected(MainMenuAdapter.MenuItem menuItem, int position, long id) {
		switch (menuItem.getId()) {
			case MainMenuAdapter.ROUTES_MENU: {
				Intent mainActivity = new Intent(getApplicationContext(), RouteActivity.class);
		    	startActivity(mainActivity);
				break;
			} 
			case MainMenuAdapter.ORDERS_MENU: {
				Intent ordersActivity = new Intent(getApplicationContext(), DailyOrdersActivity.class);
		    	startActivity(ordersActivity);
				break;
			} 
			case MainMenuAdapter.SETTINGS_MENU: {
				Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
		    	startActivity(settingsActivity);
				break;
			} 
			case MainMenuAdapter.SYNC_MENU: {
				Intent syncActivity = new Intent(getApplicationContext(), SynchronizationActivity.class);
		    	startActivity(syncActivity);
				break;
			} 
		}
	}
	
	protected MainMenuFragment getMainMenuFragment() {
		return (MainMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_menu_list);
	}
}