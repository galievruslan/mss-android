package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.commonsware.cwac.updater.DownloadStrategy;
import com.commonsware.cwac.updater.ImmediateConfirmationStrategy;
import com.commonsware.cwac.updater.InternalHttpDownloadStrategy;
import com.commonsware.cwac.updater.MssHttpDownloadStrategy;
import com.commonsware.cwac.updater.MssHttpVersionCheckStrategy;
import com.commonsware.cwac.updater.UpdateRequest;
import com.commonsware.cwac.updater.UpdateService;
import com.mss.application.fragments.MainMenuFragment;
import com.mss.application.fragments.MainMenuFragment.OnMenuSelectedListener;
import com.mss.application.services.Constants;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity implements OnMenuSelectedListener {	
	private static final String TAG = CustomersActivity.class.getSimpleName();
	
	private static boolean IN_UPDATE = false;
	
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
			case MainMenuAdapter.UPDATES_MENU: {
				if (!IN_UPDATE) {
					IN_UPDATE = true;
					
					try {
						UpdateRequest.Builder builder=new UpdateRequest.Builder(this);

						PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);	
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
						String serverAddress = sharedPreferences.getString("server_address", "");
		        
						AccountManager accountManager = AccountManager.get(this);
						Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
		        
						String login = "";
						String password = "";
						if (accounts.length > 0) {
							Account account = accounts[0];   
							if (account != null) {
								login = account.name;
								password = accountManager.blockingGetAuthToken(account,
										Constants.AUTHTOKEN_TYPE, true);
							}
						}
					
						PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
						String version = pInfo.versionName;
		        
						DownloadStrategy downloadStrategy = null;
						if (Build.VERSION.SDK_INT>=11) {
							downloadStrategy = new InternalHttpDownloadStrategy(serverAddress, login, password);
						} else {			    
							downloadStrategy = new MssHttpDownloadStrategy(serverAddress, login, password);
						}
		        
						builder.setVersionCheckStrategy(new MssHttpVersionCheckStrategy(serverAddress, login, password, version))
							.setPreDownloadConfirmationStrategy(new ImmediateConfirmationStrategy())
							.setDownloadStrategy(downloadStrategy)
							.setPreInstallConfirmationStrategy(new ImmediateConfirmationStrategy())
							.execute();
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
						IN_UPDATE = false;
					}
				} else {
					Toast.makeText(this, R.string.notify_update_in_progress, Toast.LENGTH_LONG).show();
				}
			} 
		}
	}
	
	protected MainMenuFragment getMainMenuFragment() {
		return (MainMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_menu_list);
	}
	
	@Override
    public void onResume() {
      super.onResume();
      LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
          new IntentFilter(UpdateService.UPDATE_EVENT_KEY));      
    }
    
    @Override
    public void onPause() {
        super.onPause(); 
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
    
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    	    // Extract data included in the Intent
    	    int messageId = intent.getIntExtra(UpdateService.MESSAGE_KEY, 0);
    	    switch (messageId) {
			case UpdateService.EVENT_CHECK_FOR_UPDATES:
	    	    Toast.makeText(context, R.string.notify_check_if_updates_available, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_UPDATES_AVAILABLE:
	    	    Toast.makeText(context, R.string.notify_updates_available, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_NEWEST_VERSION_IS_INSTALLED:
				IN_UPDATE = false;
	    	    Toast.makeText(context, R.string.notify_lastests_version_is_installed, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_DOWNLOAD_UPDATES:
	    	    Toast.makeText(context, R.string.notify_download_updates, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_INSTALL_UPDATES:
	    	    Toast.makeText(context, R.string.notify_install_updates, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_UPDATE_FAILED:
				IN_UPDATE = false;
	    	    Toast.makeText(context, R.string.notify_update_failed, Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
    	}
    };
}