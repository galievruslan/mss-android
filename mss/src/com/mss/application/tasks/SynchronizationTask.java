package com.mss.application.tasks;

import java.net.URISyntaxException;
import java.util.Date;

import android.content.Intent;
import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.MainActivity;
import com.mss.application.R;
import com.mss.application.SynchronizationActivity;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteCategoryRepository;
import com.mss.infrastructure.ormlite.OrmliteCustomerRepository;
import com.mss.infrastructure.ormlite.OrmliteOrderItemRepository;
import com.mss.infrastructure.ormlite.OrmliteOrderRepository;
import com.mss.infrastructure.ormlite.OrmlitePreferencesRepository;
import com.mss.infrastructure.ormlite.OrmlitePriceListLineRepository;
import com.mss.infrastructure.ormlite.OrmlitePriceListRepository;
import com.mss.infrastructure.ormlite.OrmliteProductRepository;
import com.mss.infrastructure.ormlite.OrmliteProductUnitOfMeasureRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointTemplateRepository;
import com.mss.infrastructure.ormlite.OrmliteRouteRepository;
import com.mss.infrastructure.ormlite.OrmliteRouteTemplateRepository;
import com.mss.infrastructure.ormlite.OrmliteShippingAddressRepository;
import com.mss.infrastructure.ormlite.OrmliteStatusRepository;
import com.mss.infrastructure.ormlite.OrmliteUnitOfMeasureRepository;
import com.mss.infrastructure.ormlite.OrmliteWarehouseRepository;
import com.mss.infrastructure.web.AuthenticationFailedException;
import com.mss.infrastructure.web.WebConnectionException;
import com.mss.infrastructure.web.WebServer;
import com.mss.infrastructure.web.dtos.translators.CategoryTranslator;
import com.mss.infrastructure.web.dtos.translators.CustomerTranslator;
import com.mss.infrastructure.web.dtos.translators.PreferencesTranslator;
import com.mss.infrastructure.web.dtos.translators.PriceListLineTranslator;
import com.mss.infrastructure.web.dtos.translators.PriceListTranslator;
import com.mss.infrastructure.web.dtos.translators.ProductTranslator;
import com.mss.infrastructure.web.dtos.translators.ProductUnitOfMeasureTranslator;
import com.mss.infrastructure.web.dtos.translators.RoutePointTemplateTranslator;
import com.mss.infrastructure.web.dtos.translators.RouteTemplateTranslator;
import com.mss.infrastructure.web.dtos.translators.ShippingAddressTranslator;
import com.mss.infrastructure.web.dtos.translators.StatusTranslator;
import com.mss.infrastructure.web.dtos.translators.UnitOfMeasureTranslator;
import com.mss.infrastructure.web.dtos.translators.WarehouseTranslator;
import com.mss.infrastructure.web.repositories.CategoryWebRepository;
import com.mss.infrastructure.web.repositories.CustomerWebRepository;
import com.mss.infrastructure.web.repositories.PreferencesWebRepository;
import com.mss.infrastructure.web.repositories.PriceListLineWebRepository;
import com.mss.infrastructure.web.repositories.PriceListWebRepository;
import com.mss.infrastructure.web.repositories.ProductUoMWebRepository;
import com.mss.infrastructure.web.repositories.ProductWebRepository;
import com.mss.infrastructure.web.repositories.RoutePointTemplateWebRepository;
import com.mss.infrastructure.web.repositories.RouteTemplateWebRepository;
import com.mss.infrastructure.web.repositories.ShippingAddressWebRepository;
import com.mss.infrastructure.web.repositories.StatusWebRepository;
import com.mss.infrastructure.web.repositories.UnitOfMeasureWebRepository;
import com.mss.infrastructure.web.repositories.WarehouseWebRepository;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class SynchronizationTask extends AsyncTask<Void, Integer, Boolean> {
	    	
	private SynchronizationActivity synchronizationActivity;
	private DatabaseHelper databaseHelper;
	private boolean isFull;
	
	private String serverAddress;
	private Date lastSyncDate = null;
	private Date serverTimestamp = null;
	
	public SynchronizationTask(SynchronizationActivity synchronizationActivity) {
		this.synchronizationActivity = synchronizationActivity;
		this.databaseHelper = OpenHelperManager.getHelper(synchronizationActivity, DatabaseHelper.class);
		
		//serverAddress = synchronizationActivity.getServerAddress();
		try {
			//lastSyncDate = synchronizationActivity.getLastSyncTime();
		} catch (Throwable e) {					
			isFull = true;
		}
	}
	
	public void setFull(boolean isFull){
		this.isFull = isFull;
	} 
	
    @Override
    protected Boolean doInBackground(Void... params) {     	
    	WebServer webServer = new WebServer(serverAddress);

       	try {
			webServer.connect("manager", "423200");				
			int pageSize = 250;
			
			publishProgress(R.string.greetings);
			PostGreetings postGreetings = 
					new PostGreetings(webServer, "/synchronization/client_information.json");
			postGreetings.execute((Void)null).get();
			
			publishProgress(R.string.sync_routes);
			PostRoutes postRoutes = new 
					PostRoutes(webServer, 
							"/synchronization/routes.json", 
							new OrmliteRouteRepository(databaseHelper), 
							new OrmliteRoutePointRepository(databaseHelper));
			postRoutes.execute((Void)null).get();
			
			publishProgress(R.string.sync_orders);
			PostOrders postOrders = new 
					PostOrders(webServer, 
							"/synchronization/orders.json", 
							new OrmliteOrderRepository(databaseHelper), 
							new OrmliteOrderItemRepository(databaseHelper));
			postOrders.execute((Void)null).get();
			
			if (isFull) {
				publishProgress(R.string.sync_clear_storage);
				databaseHelper.clear();
			}
			
			serverTimestamp = webServer.getTime();
			
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
			SyncProducts syncProducts = null;
			if (isFull) {
				syncProducts = new SyncProducts(
						new ProductWebRepository(webServer.getCurrentConnection()), 
						new OrmliteProductRepository(databaseHelper),
						new ProductTranslator(),
						pageSize);
			} else {
				syncProducts = new SyncProducts(
						new ProductWebRepository(webServer.getCurrentConnection()), 
						new OrmliteProductRepository(databaseHelper),
						new ProductTranslator(),
						pageSize,
						lastSyncDate);
			}			
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
			
			publishProgress(R.string.sync_preferences);
			SyncPreferences syncPreferences = null;
			if (isFull) {
				syncPreferences = new SyncPreferences(
						new PreferencesWebRepository(webServer.getCurrentConnection()), 
						new OrmlitePreferencesRepository(databaseHelper),
						new PreferencesTranslator(),
						pageSize);
			} else {
				syncPreferences = new SyncPreferences(
						new PreferencesWebRepository(webServer.getCurrentConnection()), 
						new OrmlitePreferencesRepository(databaseHelper),
						new PreferencesTranslator(),
						pageSize,
						lastSyncDate);
			}
			syncPreferences.execute((Void)null).get();
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
    
    private int lastStatusResId;
    
    //this method is used to publish progress; it is
    //called automatically after each call to publishProgress()
    //runs on the UI thread
    protected void onProgressUpdate(Integer... progress) {
        //you need to cast the params to the type you want!
    	Integer resId = (Integer)progress[0];
    	
    	lastStatusResId = resId;
    	if (synchronizationActivity != null) {
    		//now update the progress dialog
    		//synchronizationActivity.setStatusMessage(resId);
    	}
    }
    
    public Integer getLastStatusMessageResId(){
    	return lastStatusResId;
    }
    
    @Override
    protected void onPostExecute(final Boolean success) {
    	
    	if (synchronizationActivity != null) {
    		//synchronizationActivity.setLastSyncTime(serverTimestamp);
    		//synchronizationActivity.setSynchronizationTask(null);
    		synchronizationActivity.showProgress(false);

        	if (success) {        	
        		Intent mainActivity = new Intent(synchronizationActivity.getApplicationContext(), MainActivity.class);
        		synchronizationActivity.startActivity(mainActivity);        	
        		synchronizationActivity.finish();
        	}
    	}
    }
    
    public Date getServerTimestamp(){
    	return serverTimestamp;
    }
    
    public void detach() {
    	synchronizationActivity = null;
    }
      
    public void attach(SynchronizationActivity synchronizationActivity) {
        this.synchronizationActivity = synchronizationActivity;
    }

    @Override
    protected void onCancelled() {
    	if (synchronizationActivity != null) {
    		//synchronizationActivity.setSynchronizationTask(null);
    		synchronizationActivity.showProgress(false);
    	}
    }
}