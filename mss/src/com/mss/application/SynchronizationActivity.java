package com.mss.application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mss.application.services.AuthenticationService;
import com.mss.application.services.Constants;
import com.mss.application.services.DummyProvider;
import com.mss.application.tasks.*;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class SynchronizationActivity extends Activity implements SyncStatusObserver {  

    // Values for email and password at the time of the login attempt.
    private boolean mFullSync;

    // UI references.
    private CheckBox mFullSyncView;
    private TextView mLastSync;
    private View mSyncFormView;
    private View mSyncStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        
        
        // Set up the login form.
        mFullSyncView = (CheckBox) findViewById(R.id.full_synchronization_checkbox);
        mLastSync = (TextView) findViewById(R.id.last_sync_text_view);
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String lastSync = sharedPreferences.getString("last_sync", "");
        mLastSync.setText(lastSync);

        mSyncFormView = findViewById(R.id.sync_form);
        mSyncStatusView = findViewById(R.id.sync_status);
        
        findViewById(R.id.synchronize_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	try {					
					attemptSync();
				} catch (Throwable e) {
					e.printStackTrace();
				}
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptSync() {
    	//AuthenticationService authenticationService = new AuthenticationService(getApplicationContext());
    	//if (!authenticationService.isAuthenticated()) {
    	//	Intent intent = new Intent(this, LoginActivity.class);
    	//	startActivityForResult(intent, 0);
    	//	return;
    	//}
    	    	
        mFullSyncView = (CheckBox)findViewById(R.id.full_synchronization_checkbox);
        mFullSync = mFullSyncView.isChecked();

        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account account = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE)[0];
        
        //mSyncStatusMessageView.setText(R.string.sync_progress);
        //showProgress(true);
        //mSyncTask = new SynchronizationTask(this);
        //mSyncTask.setFull(mFullSync);
        //mSyncTask.execute((Void) null);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean("full_sync", mFullSync);
        ContentResolver.requestSync(account, DummyProvider.getAuthority(), bundle);
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

	@Override
	public void onStatusChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
}


