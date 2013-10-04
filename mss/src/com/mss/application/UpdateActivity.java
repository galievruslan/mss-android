package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mss.application.services.Constants;
import com.mss.application.services.DummyProvider;
import com.mss.application.services.SynchronizationAdapter;
import com.mss.application.services.SystemService;
import com.mss.application.services.UpdateService;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends SherlockFragmentActivity {  

    private Account mAccount;

    // UI references.
    private TextView mVersionTextView;
    private View mUpdateFormView;
    private View mUpdateStatusView;
    private TextView mUpdateStatusMessageTextView;

    SystemService mSystemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
                        
        mSystemService = new SystemService(this);
        mVersionTextView = (TextView)findViewById(R.id.version_text_view); 
        mVersionTextView.setText(mSystemService.getApplicationVersion());

        mUpdateFormView = findViewById(R.id.update_form);
        mUpdateStatusView = findViewById(R.id.update_status);
        mUpdateStatusMessageTextView = (TextView)findViewById(R.id.update_status_message);
        
        findViewById(R.id.update_run_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	try {
            		AccountManager accountManager = AccountManager.get(getApplicationContext());
                    Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
                    if (accounts.length == 0) {
                    	startActivity(new Intent(Settings.ACTION_ADD_ACCOUNT));
                    	return;
                    }
                    
                    Intent intent = new Intent(getApplicationContext(), UpdateService.class);
                    intent.putExtra(UpdateService.VERSION, mSystemService.getApplicationVersion());
                    startService(intent);
                    
                    showProgress(true);
				} catch (Throwable e) {
					e.printStackTrace();
				}
            }
        });
        
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        if (accounts.length > 0) {
        	mAccount = accounts[0];        
        	showProgress(UpdateService.IS_RUNNED);
        }
        
        if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    public void onResume() {
      super.onResume();
      
      AccountManager accountManager = AccountManager.get(getApplicationContext());
      Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
      if (accounts.length > 0) {
    	  mAccount = accounts[0];      
    	  showProgress(UpdateService.IS_RUNNED);
    	  if (!UpdateService.IS_RUNNED) {	
              mVersionTextView.setText(mSystemService.getApplicationVersion());
    	  }
      }

      // Register mMessageReceiver to receive messages.
      LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
          new IntentFilter(UpdateService.UPDATE_EVENT_KEY));      
    }
    
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
    
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    	    // Extract data included in the Intent
    	    int messageId = intent.getIntExtra(SynchronizationAdapter.MESSAGE_KEY, 0);
    	    String status = intent.getStringExtra(SynchronizationAdapter.STATUS_KEY);
    	    if (status == null || 
    	    	(!status.equals(UpdateService.UPDATE_FINISHED_KEY) &&
    	    	!status.equals(UpdateService.UPDATE_FAILED_KEY))) {    	    	
    	    	mUpdateStatusMessageTextView.setText(getString(messageId));    	    
    	    } else {
    	    	Log.d("receiver", "Got status: " + status);    	
                mVersionTextView.setText(mSystemService.getApplicationVersion());
    	    	showProgress(false);
    	    	
    	    	if (status.equals(UpdateService.UPDATE_FINISHED_KEY)) {
    	    		if (messageId != 0) {
    	    			Toast.makeText(context, getString(messageId), Toast.LENGTH_LONG).show();
    	    		}
    	    	} else if (status.equals(UpdateService.UPDATE_FAILED_KEY)) {
    	    		Toast.makeText(context, getString(R.string.update_failed), Toast.LENGTH_LONG).show();
    	    	}
    	    }
    	}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_update, menu);
		
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_item_cancel_update:
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

            mUpdateStatusView.setVisibility(View.VISIBLE);
            mUpdateStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        	mUpdateStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mUpdateFormView.setVisibility(View.VISIBLE);
            mUpdateFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        	mUpdateFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
        	mUpdateStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
        	mUpdateFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}


