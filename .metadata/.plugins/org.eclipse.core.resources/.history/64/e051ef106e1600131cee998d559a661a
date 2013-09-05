package com.mss.application;

import com.mss.application.services.Constants;
import com.mss.application.services.DummyProvider;
import com.mss.application.services.SynchronizationAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class SynchronizationActivity extends Activity {  

    private Account mAccount;
    private boolean mFullSync;

    // UI references.
    private CheckBox mFullSyncView;
    private TextView mLastSync;
    private View mSyncFormView;
    private View mSyncStatusView;
    private TextView mSyncStatusMessageTextView;
    
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
                
        // Set up the login form.
        mFullSyncView = (CheckBox) findViewById(R.id.full_synchronization_checkbox);
        mLastSync = (TextView) findViewById(R.id.last_sync_text_view);
        
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String lastSync = mSharedPreferences.getString("last_sync", "");
        mLastSync.setText(lastSync);

        mSyncFormView = findViewById(R.id.sync_form);
        mSyncStatusView = findViewById(R.id.sync_status);
        mSyncStatusMessageTextView = (TextView)findViewById(R.id.sync_status_message);
        
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        mAccount = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE)[0];
        
        findViewById(R.id.sync_run_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	try {					
            		mFullSyncView = (CheckBox)findViewById(R.id.full_synchronization_checkbox);
                    mFullSync = mFullSyncView.isChecked();

                    Bundle bundle = new Bundle();
                    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
                    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                    bundle.putBoolean("full_sync", mFullSync);
                    ContentResolver.requestSync(mAccount, DummyProvider.getAuthority(), bundle);                    
                    showProgress(true);
				} catch (Throwable e) {
					e.printStackTrace();
				}
            }
        });
        
        showProgress(ContentResolver.isSyncActive(mAccount, DummyProvider.getAuthority()));
    }
    
    @Override
    public void onResume() {
      super.onResume();

      // Register mMessageReceiver to receive messages.
      LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
          new IntentFilter(SynchronizationAdapter.SYNC_EVENT_KEY));
    }
    
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    	    // Extract data included in the Intent
    	    int messageId = intent.getIntExtra(SynchronizationAdapter.MESSAGE_KEY, 0);
    	    String status = intent.getStringExtra(SynchronizationAdapter.STATUS_KEY);
    	    if (status == null || !status.equals(SynchronizationAdapter.SYNC_FINISHED_KEY)) {    	    	
    	    	mSyncStatusMessageTextView.setText(getString(messageId));    	    
    	    } else {
    	    	Log.d("receiver", "Got status: " + status);
    	    	String lastSync = mSharedPreferences.getString("last_sync", "");    	    	
                mLastSync.setText(lastSync);
    	    	showProgress(false);
    	    }
    	}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_synchronization, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.menu_item_cancel_sync:
			ContentResolver.cancelSync(mAccount, DummyProvider.getAuthority());
    		showProgress(false);		
    		return true;
		default:
			break;
		}
		
		return false;
	}

	/**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSyncStatusView.setVisibility(View.VISIBLE);
            mSyncStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        	mSyncStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mSyncFormView.setVisibility(View.VISIBLE);
            mSyncFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        	mSyncFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
        	mSyncStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
        	mSyncFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}


