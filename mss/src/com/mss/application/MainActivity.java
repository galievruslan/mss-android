package com.mss.application;

import java.io.IOException;

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

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

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
			case MainMenuAdapter.UPDATES_MENU: {
				UpdateTask updateTask = new UpdateTask(this);
				updateTask.execute(new Void[0]);
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
	    	    Toast.makeText(context, R.string.notify_lastests_version_is_installed, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_DOWNLOAD_UPDATES:
	    	    Toast.makeText(context, R.string.notify_download_updates, Toast.LENGTH_SHORT).show();
				break;
			case UpdateService.EVENT_INSTALL_UPDATES:
	    	    Toast.makeText(context, R.string.notify_install_updates, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
    	}
    };
    
    private static class UpdateTask extends AsyncTask<Void, Void, Void> {

    	private Context mContext;
    	private boolean mInProcess = false;
    	
    	public UpdateTask(Context context){
    		mContext = context;
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			if (mInProcess)
				return null;
			
			try {
				mInProcess = true;
				UpdateRequest.Builder builder=new UpdateRequest.Builder(mContext);

				PreferenceManager.setDefaultValues(mContext, R.xml.pref_data_sync, false);	
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
				String serverAddress = sharedPreferences.getString("server_address", "");
	        
				AccountManager accountManager = AccountManager.get(mContext);
				Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
	        
				String login = "";
				String password = "";
				if (accounts.length > 0) {
					Account account = accounts[0];   
					if (account != null) {
						login = account.name;
	        		
						try {
							password = accountManager.blockingGetAuthToken(account,
						            Constants.AUTHTOKEN_TYPE, true);
						} catch (OperationCanceledException e) {
							e.printStackTrace();
						} catch (AuthenticatorException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
	        
				DownloadStrategy downloadStrategy = null;
				if (Build.VERSION.SDK_INT>=11) {
					downloadStrategy = new InternalHttpDownloadStrategy(serverAddress, login, password);
				} else {			    
					downloadStrategy = new MssHttpDownloadStrategy(serverAddress, login, password);
				}
	        
				builder.setVersionCheckStrategy(new MssHttpVersionCheckStrategy(serverAddress, login, password, "0.0.9"))
					.setPreDownloadConfirmationStrategy(new ImmediateConfirmationStrategy())
					.setDownloadStrategy(downloadStrategy)
					.setPreInstallConfirmationStrategy(new ImmediateConfirmationStrategy())
					.execute();
		    
			}
			catch (Exception exception) {
				Log.e(TAG, exception.getMessage());
			}
			finally {
				mInProcess = false;
			}
			
			return null;
		}    	
    }
}