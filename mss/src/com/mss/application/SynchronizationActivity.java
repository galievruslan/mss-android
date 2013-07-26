package com.mss.application;

import java.net.URISyntaxException;
import java.util.Date;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.mss.application.tasks.*;
import com.mss.infrastructure.ormlite.*;
import com.mss.infrastructure.web.AuthenticationFailedException;
import com.mss.infrastructure.web.WebConnectionException;
import com.mss.infrastructure.web.WebServer;
import com.mss.infrastructure.web.repositories.*;
import com.mss.infrastucture.web.dtos.translators.CategoryTranslator;
import com.mss.infrastucture.web.dtos.translators.CustomerTranslator;
import com.mss.infrastucture.web.dtos.translators.PriceListLineTranslator;
import com.mss.infrastucture.web.dtos.translators.PriceListTranslator;
import com.mss.infrastucture.web.dtos.translators.ProductTranslator;
import com.mss.infrastucture.web.dtos.translators.ProductUnitOfMeasureTranslator;
import com.mss.infrastucture.web.dtos.translators.RoutePointTemplateTranslator;
import com.mss.infrastucture.web.dtos.translators.RouteTemplateTranslator;
import com.mss.infrastucture.web.dtos.translators.ShippingAddressTranslator;
import com.mss.infrastucture.web.dtos.translators.StatusTranslator;
import com.mss.infrastucture.web.dtos.translators.UnitOfMeasureTranslator;
import com.mss.infrastucture.web.dtos.translators.WarehouseTranslator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class SynchronizationActivity extends OrmLiteBaseActivity<DatabaseHelper> {  

	private SharedPreferences sharedPreferences = null;		
    private SynchronizationTask mSyncTask = null;

    // Values for email and password at the time of the login attempt.
    private boolean mFullSync;

    // UI references.
    private CheckBox mFullSyncView;
    private TextView mLastSync;
    private View mSyncFormView;
    private View mSyncStatusView;
    private TextView mSyncStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        // Set up the login form.
        mFullSyncView = (CheckBox) findViewById(R.id.full_synchronization_checkbox);
        mLastSync = (TextView) findViewById(R.id.last_sync_text_view);
        
        String lastSync = sharedPreferences.getString("last_sync", "");
        mLastSync.setText(lastSync);

        mSyncFormView = findViewById(R.id.sync_form);
        mSyncStatusView = findViewById(R.id.sync_status);
        mSyncStatusMessageView = (TextView) findViewById(R.id.sync_status_message);

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
        if (mSyncTask != null) {
            return;
        }
        
        mFullSyncView = (CheckBox)findViewById(R.id.full_synchronization_checkbox);
        mFullSync = mFullSyncView.isChecked();

        mSyncStatusMessageView.setText(R.string.sync_progress);
        showProgress(true);
        mSyncTask = new SynchronizationTask(getHelper(), mFullSync);
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
    public class SynchronizationTask extends AsyncTask<Void, Integer, Boolean> {
    	    	
    	private DatabaseHelper databaseHelper;
    	private boolean isFull;
    	public SynchronizationTask(DatabaseHelper databaseHelper, boolean isFull) {
    		this.databaseHelper = databaseHelper;
    		this.isFull = isFull;
    	}
    	
        @Override
        protected Boolean doInBackground(Void... params) { 
        	String serverAddress = sharedPreferences.getString("server_address", "");
        	WebServer webServer = new WebServer(serverAddress);

           	try {
				webServer.connect("manager", "423200");				
				int pageSize = 250;
				
				Date lastSyncDate = null;
				try {
					String lastSync = sharedPreferences.getString("last_sync", "");
					lastSyncDate = new Date(Date.parse(lastSync));
				} catch (IllegalArgumentException e) {					
					isFull = true;
				}
				
				if (isFull) {
					publishProgress(R.string.sync_clear_storage);
					databaseHelper.clear();
				}
				
				Date serverTimestamp = webServer.getTime();
				
				publishProgress(R.string.sync_categories);
				SyncCategories syncCategories = null;
				if (isFull) {
					syncCategories = new SyncCategories(
							new CategoryWebRepository(webServer.getCurrentConnection()), 
							new OrmliteCategoryRepository(databaseHelper),
							new CategoryTranslator(),
							pageSize);
				} else {
					syncCategories = new SyncCategories(
							new CategoryWebRepository(webServer.getCurrentConnection()), 
							new OrmliteCategoryRepository(databaseHelper),
							new CategoryTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncCategories.execute((Void)null).get();
				
				publishProgress(R.string.sync_customers);
				SyncCustomers syncCustomers = null;
				if (isFull) {
					syncCustomers = new SyncCustomers(
							new CustomerWebRepository(webServer.getCurrentConnection()), 
							new OrmliteCustomerRepository(databaseHelper),
							new CustomerTranslator(),
							pageSize);
				} else {
					syncCustomers = new SyncCustomers(
							new CustomerWebRepository(webServer.getCurrentConnection()), 
							new OrmliteCustomerRepository(databaseHelper),
							new CustomerTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncCustomers.execute((Void)null).get();
				
				publishProgress(R.string.sync_shipping_addresses);
				SyncShippingAddresses syncShippingAddresses = null;
				if (isFull) {
					syncShippingAddresses = new SyncShippingAddresses(
							new ShippingAddressWebRepository(webServer.getCurrentConnection()), 
							new OrmliteShippingAddressRepository(databaseHelper),
							new ShippingAddressTranslator(),
							pageSize);
				} else {
					syncShippingAddresses = new SyncShippingAddresses(
							new ShippingAddressWebRepository(webServer.getCurrentConnection()), 
							new OrmliteShippingAddressRepository(databaseHelper),
							new ShippingAddressTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncShippingAddresses.execute((Void)null).get();
				
				publishProgress(R.string.sync_products);
				SyncProducts syncProducts = new SyncProducts(
						new ProductWebRepository(webServer.getCurrentConnection()), 
						new OrmliteProductRepository(databaseHelper),
						new ProductTranslator(),
						pageSize);
				syncProducts.execute((Void)null).get();
				
				publishProgress(R.string.sync_price_lists);
				SyncPriceLists syncPriceLists = null;
				if (isFull) {
					syncPriceLists = new SyncPriceLists(
							new PriceListWebRepository(webServer.getCurrentConnection()), 
							new OrmlitePriceListRepository(databaseHelper),
							new PriceListTranslator(),
							pageSize);
				} else {
					syncPriceLists = new SyncPriceLists(
							new PriceListWebRepository(webServer.getCurrentConnection()), 
							new OrmlitePriceListRepository(databaseHelper),
							new PriceListTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncPriceLists.execute((Void)null).get();
				
				publishProgress(R.string.sync_price_lists_lines);
				SyncPriceListsLines syncPriceListsLines = null;
				if (isFull) {
					syncPriceListsLines = new SyncPriceListsLines(
							new PriceListLineWebRepository(webServer.getCurrentConnection()), 
							new OrmlitePriceListLineRepository(databaseHelper),
							new PriceListLineTranslator(),
							pageSize);
				} else {
					syncPriceListsLines = new SyncPriceListsLines(
							new PriceListLineWebRepository(webServer.getCurrentConnection()), 
							new OrmlitePriceListLineRepository(databaseHelper),
							new PriceListLineTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncPriceListsLines.execute((Void)null).get();
				
				publishProgress(R.string.sync_units_of_measures);
				SyncUnitsOfMeasures syncUnitsOfMeasures = null;
				if (isFull) {
					syncUnitsOfMeasures = new SyncUnitsOfMeasures(
							new UnitOfMeasureWebRepository(webServer.getCurrentConnection()), 
							new OrmliteUnitOfMeasureRepository(databaseHelper),
							new UnitOfMeasureTranslator(),
							pageSize); 
				} else {
					syncUnitsOfMeasures = new SyncUnitsOfMeasures(
							new UnitOfMeasureWebRepository(webServer.getCurrentConnection()), 
							new OrmliteUnitOfMeasureRepository(databaseHelper),
							new UnitOfMeasureTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncUnitsOfMeasures.execute((Void)null).get();
				
				publishProgress(R.string.sync_products_units_of_measures);
				SyncProductsUnitsOfMeasures syncProductsUnitsOfMeasures = null;
				if (isFull) {
					syncProductsUnitsOfMeasures = new SyncProductsUnitsOfMeasures(
							new ProductUoMWebRepository(webServer.getCurrentConnection()), 
							new OrmliteProductUnitOfMeasureRepository(databaseHelper),
							new ProductUnitOfMeasureTranslator(),
							pageSize);
				} else {
					syncProductsUnitsOfMeasures = new SyncProductsUnitsOfMeasures(
							new ProductUoMWebRepository(webServer.getCurrentConnection()), 
							new OrmliteProductUnitOfMeasureRepository(databaseHelper),
							new ProductUnitOfMeasureTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncProductsUnitsOfMeasures.execute((Void)null).get();
				
				publishProgress(R.string.sync_statuses);
				SyncStatuses syncStatuses = null;
				if (isFull) {
					syncStatuses = new SyncStatuses(
							new StatusWebRepository(webServer.getCurrentConnection()), 
							new OrmliteStatusRepository(databaseHelper),
							new StatusTranslator(),
							pageSize); 
				} else {
					syncStatuses = new SyncStatuses(
							new StatusWebRepository(webServer.getCurrentConnection()), 
							new OrmliteStatusRepository(databaseHelper),
							new StatusTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncStatuses.execute((Void)null).get();
				
				publishProgress(R.string.sync_warehouses);
				SyncWarehouses syncWarehouses = null;
				if (isFull) {
					syncWarehouses = new SyncWarehouses(
							new WarehouseWebRepository(webServer.getCurrentConnection()), 
							new OrmliteWarehouseRepository(databaseHelper),
							new WarehouseTranslator(),
							pageSize);
				} else {
					syncWarehouses = new SyncWarehouses(
							new WarehouseWebRepository(webServer.getCurrentConnection()), 
							new OrmliteWarehouseRepository(databaseHelper),
							new WarehouseTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncWarehouses.execute((Void)null).get();
				
				publishProgress(R.string.sync_route_templates);
				SyncRouteTemplates syncRouteTemplates = null;
				if (isFull) {
					syncRouteTemplates = new SyncRouteTemplates(
							new RouteTemplateWebRepository(webServer.getCurrentConnection()), 
							new OrmliteRouteTemplateRepository(databaseHelper),
							new RouteTemplateTranslator(),
							pageSize);
				} else {
					syncRouteTemplates = new SyncRouteTemplates(
							new RouteTemplateWebRepository(webServer.getCurrentConnection()), 
							new OrmliteRouteTemplateRepository(databaseHelper),
							new RouteTemplateTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncRouteTemplates.execute((Void)null).get();
				
				publishProgress(R.string.sync_route_points_templates);
				SyncRoutePointsTemplates syncRoutePointsTemplates = null;
				if (isFull) {
					syncRoutePointsTemplates = new SyncRoutePointsTemplates(
							new RoutePointTemplateWebRepository(webServer.getCurrentConnection()), 
							new OrmliteRoutePointTemplateRepository(databaseHelper),
							new RoutePointTemplateTranslator(),
							pageSize);
				} else {
					syncRoutePointsTemplates = new SyncRoutePointsTemplates(
							new RoutePointTemplateWebRepository(webServer.getCurrentConnection()), 
							new OrmliteRoutePointTemplateRepository(databaseHelper),
							new RoutePointTemplateTranslator(),
							pageSize,
							lastSyncDate);
				}
				syncRoutePointsTemplates.execute((Void)null).get();
				
				
				SharedPreferences.Editor editor = sharedPreferences.edit();	
				editor.putString("last_sync", serverTimestamp.toLocaleString());
				editor.commit();
				
			} catch (WebConnectionException e) {
				e.printStackTrace();
				return false;
			} catch (AuthenticationFailedException e) {
				e.printStackTrace();
				return false;
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} catch (Throwable e) {
				e.printStackTrace();
				return false;
			}

            return true;
        }
        
        //this method is used to publish progress; it is
        //called automatically after each call to publishProgress()
        //runs on the UI thread
        protected void onProgressUpdate(Integer... progress) {
            //you need to cast the params to the type you want!
        	Integer resId = (Integer)progress[0];
            //now update the progress dialog
        	mSyncStatusMessageView.setText(resId);
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
