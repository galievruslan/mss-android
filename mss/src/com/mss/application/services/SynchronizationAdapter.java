/*
 * Copyright (C) 2010 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mss.application.services;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Order;
import com.mss.domain.models.OrderItem;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.*;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.WebServer;
import com.mss.infrastructure.web.WebServer.PostResult;
import com.mss.infrastructure.web.dtos.Dto;
import com.mss.infrastructure.web.dtos.ISO8601Utils;
import com.mss.infrastructure.web.dtos.translators.*;
import com.mss.infrastructure.web.dtos.translators.Translator;
import com.mss.infrastructure.web.repositories.*;
import com.mss.utils.IterableHelpers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SyncAdapter implementation for syncing sample SyncAdapter contacts to the
 * platform ContactOperations provider.
 */
public class SynchronizationAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = "SyncAdapter";

    private final AccountManager mAccountManager;
    private final Context mContext;

    private Date mLastUpdated;

    public SynchronizationAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
        ContentProviderClient provider, SyncResult syncResult) {
        //String authtoken = null;
         try {
             // use the account manager to request the credentials
        	 String authtoken =
                mAccountManager.blockingGetAuthToken(account,
                    Constants.AUTHTOKEN_TYPE, true /* notifyAuthFailure */);

             SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);        
             String server = sharedPreferences.getString("server_address", "");
             
             String lastSyncStr = sharedPreferences.getString("last_sync", "");
     		 DateFormat format = SimpleDateFormat.getDateTimeInstance();
     		 Date lastSync = format.parse(lastSyncStr);
             
             WebServer webServer = new WebServer(server);
             webServer.connect(account.name, authtoken);
             
             DatabaseHelper databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
             
             int pageSize = 250;
             Boolean fullSync = extras.getBoolean("full_sync");
             
             PostGreetings(webServer);
             PostRoutes(webServer, 
            		 new OrmliteRouteRepository(databaseHelper), 
            		 new OrmliteRoutePointRepository(databaseHelper));
             PostOrders(webServer, 
            		 new OrmliteOrderRepository(databaseHelper), 
            		 new OrmliteOrderItemRepository(databaseHelper));
             
             if (fullSync) {
 				databaseHelper.clear();
 				lastSync = null;
 			}
 			
 			Date serverTimestamp = webServer.getTime();
 			
 			Sync(new CategoryWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteCategoryRepository(databaseHelper), 
 					new CategoryTranslator(), lastSync, pageSize);
 			
 			Sync(new CustomerWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteCustomerRepository(databaseHelper), 
 					new CustomerTranslator(), lastSync, pageSize);
 			
 			Sync(new ShippingAddressWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteShippingAddressRepository(databaseHelper), 
 					new ShippingAddressTranslator(), lastSync, pageSize);
 			
 			Sync(new ProductWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteProductRepository(databaseHelper), 
 					new ProductTranslator(), lastSync, pageSize);
 			
 			Sync(new PriceListWebRepository(webServer.getCurrentConnection()), 
 					new OrmlitePriceListRepository(databaseHelper), 
 					new PriceListTranslator(), lastSync, pageSize);
 			
 			Sync(new PriceListLineWebRepository(webServer.getCurrentConnection()), 
 					new OrmlitePriceListLineRepository(databaseHelper), 
 					new PriceListLineTranslator(), lastSync, pageSize);
 			
 			Sync(new UnitOfMeasureWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteUnitOfMeasureRepository(databaseHelper), 
 					new UnitOfMeasureTranslator(), lastSync, pageSize);
 			
 			Sync(new ProductUoMWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteProductUnitOfMeasureRepository(databaseHelper), 
 					new ProductUnitOfMeasureTranslator(), lastSync, pageSize);
 			
 			Sync(new StatusWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteStatusRepository(databaseHelper), 
 					new StatusTranslator(), lastSync, pageSize);
 			
 			Sync(new WarehouseWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteWarehouseRepository(databaseHelper), 
 					new WarehouseTranslator(), lastSync, pageSize);
 			
 			Sync(new RouteTemplateWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteRouteTemplateRepository(databaseHelper), 
 					new RouteTemplateTranslator(), lastSync, pageSize);
 			
 			Sync(new RoutePointTemplateWebRepository(webServer.getCurrentConnection()), 
 					new OrmliteRoutePointTemplateRepository(databaseHelper), 
 					new RoutePointTemplateTranslator(), lastSync, pageSize);
 			
 			Sync(new PreferencesWebRepository(webServer.getCurrentConnection()), 
 					new OrmlitePreferencesRepository(databaseHelper), 
 					new PreferencesTranslator(), lastSync, pageSize);
 			
 			SharedPreferences.Editor editor = sharedPreferences.edit();	
 			editor.putString("last_sync", format.format(serverTimestamp));
 			editor.commit();
             
        } catch (final AuthenticatorException e) {
            syncResult.stats.numParseExceptions++;
            Log.e(TAG, "AuthenticatorException", e);
        } catch (final OperationCanceledException e) {
            Log.e(TAG, "OperationCanceledExcetpion", e);
        } catch (final IOException e) {
            Log.e(TAG, "IOException", e);
            syncResult.stats.numIoExceptions++;
        } catch (final ParseException e) {
            syncResult.stats.numParseExceptions++;
            Log.e(TAG, "ParseException", e);
        }  catch (final Throwable e) {
        	syncResult.stats.numParseExceptions++;
        	Log.e(TAG, "Unknown exception", e);
        }
    }
    
    private <TDto extends Dto, TModel extends IEntity> void Sync(
    		WebRepository<TDto> webRepo, 
    		IRepository<TModel> modelRepo, 
    		Translator<TDto, TModel> translator, 
    		Date lastSync, 
    		Integer pageSize) throws Throwable {
		Integer pageNo = 1;
		
		List<TDto> dtos;
			
		do {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("page", pageNo.toString()));
			params.add(new BasicNameValuePair("page_size", pageSize.toString()));
			if (lastSync != null) {
				params.add(new BasicNameValuePair("updated_at", ISO8601Utils.format(lastSync)));
			}
				
			dtos = webRepo.find(params);
			for (TDto dto : dtos) {
				if (!dto.getIsValid()) {
					TModel invalidModel = modelRepo.getById(dto.getId());
					if (invalidModel != null)
						modelRepo.delete(invalidModel);
				}					
			
				modelRepo.save(translator.Translate(dto));			
			}
				
			pageNo ++;
		}
		while (dtos.size() == pageSize);
	}
    
    private void PostGreetings(WebServer webServer) throws Throwable {
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("client_type", "Android (" + SystemService.getSystemVersion() + ")"));
		nameValuePairs.add(new BasicNameValuePair("client_version",SystemService.getApplicationVersion()));
		webServer.Post("/synchronization/client_information.json", nameValuePairs);
    }
    
    private void PostRoutes(WebServer webServer,
			OrmliteRouteRepository routeRepo,
			OrmliteRoutePointRepository routePointRepo) throws Throwable{
    	Iterable<Route> routes = routeRepo.findNotSynchronized();
		for (Route route : routes) {
			Iterable<RoutePoint> points = routePointRepo.findByRouteId(route.getId());			
			PostResult result = 
					webServer.Post("/synchronization/routes.json", ToPostParams(route, IterableHelpers.toArray(RoutePoint.class, points)));
			result.getStatusCode();
			
			Pattern pattern = Pattern.compile("\"code\":100|\"code\":101");
			Matcher matcher = pattern.matcher(result.getContent());
			if (matcher.find()) {
				for (RoutePoint routePoint : points) {
					routePoint.setIsSynchronized(true);
					routePointRepo.save(routePoint);
				}
			} else {
				throw new Exception();
			}
		}
    }
    
    private List<NameValuePair> ToPostParams(Route route, RoutePoint points[]) {		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nameValuePairs.add(new BasicNameValuePair("route[date]", dateFormat.format(route.getDate())));
				
		for (int i = 0; i < points.length; i++) {
			nameValuePairs.add(
					new BasicNameValuePair(
							"route[route_points_attributes][" + String.valueOf(i) + "][shipping_address_id]" , 
							String.valueOf(points[i].getShippingAddressId())));			
			nameValuePairs.add(
					new BasicNameValuePair(
							"route[route_points_attributes][" + String.valueOf(i) + "][status_id]" , 
							String.valueOf(points[i].getStatusId())));
		}
				
		return nameValuePairs;		
	}
    
    private void PostOrders(WebServer webServer,
			OrmliteOrderRepository orderRepo,
			OrmliteOrderItemRepository orderItemRepo) throws Throwable{
    	Iterable<Order> orders = orderRepo.findNotSynchronized();
		for (Order order : orders) {
			Iterable<OrderItem> items = orderItemRepo.findByOrderId(order.getId());			
			PostResult result = 
					webServer.Post("/synchronization/orders.json", ToPostParams(order, IterableHelpers.toArray(OrderItem.class, items)));
			result.getStatusCode();
			
			Pattern pattern = Pattern.compile("\"code\":100|\"code\":101");
			Matcher matcher = pattern.matcher(result.getContent());
			if (matcher.find()) {
				order.setIsSynchronized(true);
				orderRepo.save(order);
			} else {
				throw new Exception();
			}
		}
    }
    
    private List<NameValuePair> ToPostParams(Order order, OrderItem items[]) {		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nameValuePairs.add(new BasicNameValuePair("order[date]", dateFormat.format(order.getOrderDate())));
		nameValuePairs.add(new BasicNameValuePair("order[shipping_date]", dateFormat.format(order.getShippingDate())));
		nameValuePairs.add(new BasicNameValuePair("order[shipping_address_id]", String.valueOf(order.getShippingAddressId())));
		nameValuePairs.add(new BasicNameValuePair("order[guid]", String.valueOf(order.getUid())));
		nameValuePairs.add(new BasicNameValuePair("order[warehouse_id]", String.valueOf(order.getWarehouseId())));
		nameValuePairs.add(new BasicNameValuePair("order[price_list_id]", String.valueOf(order.getPriceListId())));
		nameValuePairs.add(new BasicNameValuePair("order[comment]", order.getNote()));
				
		for (int i = 0; i < items.length; i++) {
			nameValuePairs.add(
					new BasicNameValuePair(
							"order[order_items_attributes][" + String.valueOf(i) + "][product_id]" , 
							String.valueOf(items[i].getProductId())));			
			nameValuePairs.add(
					new BasicNameValuePair(
							"order[order_items_attributes][" + String.valueOf(i) + "][unit_of_measure_id]" , 
							String.valueOf(items[i].getUnitOfMeasureId())));
			nameValuePairs.add(
					new BasicNameValuePair(
							"order[order_items_attributes][" + String.valueOf(i) + "][quantity]" , 
							String.valueOf(items[i].getCount())));
		}
				
		return nameValuePairs;		
	}
}
