package com.mss.application;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.mss.application.tasks.*;
import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.*;
import com.mss.infrastructure.web.AuthenticationFailedException;
import com.mss.infrastructure.web.WebConnectionException;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.WebServer;
import com.mss.infrastructure.web.dtos.Customer;
import com.mss.infrastructure.web.repositories.*;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class SynchronizationActivity extends OrmLiteBaseActivity<DatabaseHelper> {  

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private SynchronizationTask mSyncTask = null;

    // Values for email and password at the time of the login attempt.
    private boolean mFullSync;

    // UI references.
    private CheckBox mFullSyncView;
    private View mSyncFormView;
    private View mSyncStatusView;
    private TextView mSyncStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_synchronization);
        
        // Set up the login form.
        mFullSyncView = (CheckBox) findViewById(R.id.full_synchronization_checkbox);

        mSyncFormView = findViewById(R.id.sync_form);
        mSyncStatusView = findViewById(R.id.sync_status);
        mSyncStatusMessageView = (TextView) findViewById(R.id.sync_status_message);

        findViewById(R.id.synchronize_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	try {					
					attemptSync();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
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
        if (mSyncTask != null) {
            return;
        }

        mSyncStatusMessageView.setText(R.string.sync_progress);
        showProgress(true);
        mSyncTask = new SynchronizationTask(getHelper());
        mSyncTask.execute((Void) null);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SynchronizationTask extends AsyncTask<Void, Void, Boolean> {
    	    	
    	DatabaseHelper databaseHelper;
    	public SynchronizationTask(DatabaseHelper databaseHelper) {
    		this.databaseHelper = databaseHelper;
    	}
    	
        @Override
        protected Boolean doInBackground(Void... params) {
        	WebServer webServer = new WebServer("http://mss.alkotorg.com:3000/");

           	try {
				webServer.connect("manager", "423200");				

				OrmliteCategoryRepository categoryRepo = new OrmliteCategoryRepository(databaseHelper);
				CategoryWebRepository categoryWebRepo = new CategoryWebRepository(webServer.getCurrentConnection());
				SyncCategories syncCategories = new SyncCategories(categoryWebRepo, categoryRepo);
				syncCategories.execute((Void)null).get();
				
				OrmliteCustomerRepository customerRepo = new OrmliteCustomerRepository(databaseHelper);
				CustomerWebRepository customerWebRepo = new CustomerWebRepository(webServer.getCurrentConnection());
				SyncCustomers syncCustomers = new SyncCustomers(customerWebRepo, customerRepo);
				syncCustomers.execute((Void)null).get();
				
			} catch (WebConnectionException e) {
				e.printStackTrace();
				return false;
			} catch (AuthenticationFailedException e) {
				e.printStackTrace();
				return false;
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSyncTask = null;
            showProgress(false);

            if (success) {
            	
            	Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            	startActivity(mainActivity);
            	
                finish();
            }
        }

        @Override
        protected void onCancelled() {
        	mSyncTask = null;
            showProgress(false);
        }
    }
}
