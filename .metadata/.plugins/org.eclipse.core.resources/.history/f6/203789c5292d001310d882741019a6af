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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.mss.application.R;
import com.mss.infrastructure.web.WebServer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Service to handle application update.
 */
public class UpdateService extends IntentService {
	public UpdateService() {
		super("stub");
	}

	public static boolean IS_RUNNED = false;
	
	public static final String VERSION = "version";
	
	public static final String UPDATE_EVENT_KEY = "update_event";
    public static final String UPDATE_FINISHED_KEY = "update_finished";
    public static final String UPDATE_FAILED_KEY = "update_failed";
    public static final String MESSAGE_KEY = "message_id";
    public static final String STATUS_KEY = "status";
    
    private AvailableVersionInfo getAvailableVersion(WebServer webServer) throws GetVersionException {
    	final String JSON_VERSION_CODE="version";
    	final String JSON_UPDATE_URL="file";
    	
		try {
			
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_type", "android"));
			String response = webServer.get("/synchronization/mobile_client.json", params);

			JSONObject json = new JSONObject(response);
			AvailableVersionInfo availableVersionInfo = 
					new AvailableVersionInfo(json.getString(JSON_VERSION_CODE), 
											 json.getString(JSON_UPDATE_URL));
			
			return availableVersionInfo;
		}
		catch (Throwable e) {
			throw new GetVersionException();
		} finally {}
    }
    
    private boolean getIsUpdatesAvailable(VersionInfo currentVersion, VersionInfo availableVersion){
    	boolean updatesAvailable = false;
    	if (availableVersion.getHugeReleaseNo() > currentVersion.getHugeReleaseNo()) {
    		updatesAvailable = true;
    	} else if (availableVersion.getHugeReleaseNo() == currentVersion.getHugeReleaseNo()) {
    		if (availableVersion.getSmallReleaseNo() > currentVersion.getSmallReleaseNo()) {
    			updatesAvailable = true;
    		} else if (availableVersion.getSmallReleaseNo() == currentVersion.getSmallReleaseNo()) {
    			if (availableVersion.getBuildNo() > currentVersion.getBuildNo()) {
    				updatesAvailable = true;
    			}
    		}
    	}
    	
    	return updatesAvailable;
    }
    
    private File downloadUpdates(WebServer webServer, AvailableVersionInfo availableVersionInfo) throws DownloadUpdatesException {    	
    	File updateDir = new File(getExternalFilesDir(null), ".MSS-Update");

    	updateDir.mkdirs();
    	
    	File zip = new File(updateDir, "update.zip");
  	  	if (zip.exists()) {
  	  		zip.delete();
  	  	}
  	  	
  	  	try {
  	  		webServer.download(availableVersionInfo.getUrl(), zip.getAbsolutePath());
  	  		return zip;
  	  	} catch (Throwable e) {
  	  		throw new DownloadUpdatesException();
  	  	}
    }
    
    private File extractUpdates(File zippedUpdates) throws ExtractUpdatesException {
    	File updateDir = new File(getExternalFilesDir(null), ".MSS-Update");
   	    updateDir.mkdirs();
   	    
   	    File apk = new File(updateDir, "update.apk");
   	    if (apk.exists()) {
   	    	apk.delete();
   	    }
   	    
   	    if (!unpackZip(apk.getAbsolutePath(), zippedUpdates.getAbsolutePath())) {
   	    	throw new ExtractUpdatesException();
   	    }
   	    
   	    return apk;
    }
    
    private boolean unpackZip(String unzippedpath, String zippath) {
    	InputStream is;
        ZipInputStream zis;
        
        try {
        	is = new FileInputStream(zippath);
            zis = new ZipInputStream(new BufferedInputStream(is)); 

            while((zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                FileOutputStream fout = new FileOutputStream(unzippedpath);

                // reading and writing
                while((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                    byte[] bytes = baos.toByteArray();
                    fout.write(bytes);             
                    baos.reset();
                }

                fout.close();               
                zis.closeEntry();
            }

            zis.close();
        } 
        catch(IOException e) {
            return false;
        }

        return true;     	
    }
    
    private void installUpdates(File apk){
    	Intent i;
        
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			i = new Intent(Intent.ACTION_INSTALL_PACKAGE);
			i.putExtra(Intent.EXTRA_ALLOW_REPLACE, true);
		}
		else {
			i = new Intent(Intent.ACTION_VIEW);
		}

		i.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(i);
    }
    
    private class VersionInfo {
    	
    	/*
    	 * Version format x.x.x is expected
    	*/
    	public VersionInfo(String version) {
			String[] versionFragments = version.split("\\.");
			if (versionFragments.length > 0) {
				hugeReleaseNo = Integer.parseInt(versionFragments[0]);
			} 
			if (versionFragments.length >= 1) {
				smallReleaseNo = Integer.parseInt(versionFragments[1]);
			} 
			if (versionFragments.length >= 2) {
				buildNo = Integer.parseInt(versionFragments[2]);
			} 
    	}
    	
    	private int hugeReleaseNo = 0;
    	public int getHugeReleaseNo() {
    		return hugeReleaseNo;
    	}
    	
    	private int smallReleaseNo = 0;
    	public int getSmallReleaseNo() {
    		return smallReleaseNo;
    	}
    	
    	private int buildNo = 0;
    	public int getBuildNo() {
    		return buildNo;
    	}
    }
    
    private class AvailableVersionInfo extends VersionInfo {
    	
    	public AvailableVersionInfo(String version, String url) {
    		super(version);
    		this.url = url;
    	}
    	
    	private String url;
    	public String getUrl() {
    		return url;
    	}
    }
    
    private class GetVersionException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}

    private class DownloadUpdatesException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
    
    private class ExtractUpdatesException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		IS_RUNNED = true;
		
		Intent event = new Intent(UPDATE_EVENT_KEY); 
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
		
		try {
			AccountManager accountManager = AccountManager.get(getApplicationContext());
            Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);    		                    
            String authtoken =
            		accountManager.blockingGetAuthToken(accounts[0],
                        Constants.AUTHTOKEN_TYPE, true);
			
			String version = intent.getStringExtra(VERSION);
			VersionInfo currentVersion = new VersionInfo(version);
			
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);        
            String serverAddress = sharedPreferences.getString("server_address", "");
			
            event.putExtra(MESSAGE_KEY, R.string.update_connect);
            localBroadcastManager.sendBroadcast(event);
			WebServer webServer = new WebServer(serverAddress);
			webServer.connect(accounts[0].name , authtoken);
			
			event.putExtra(MESSAGE_KEY, R.string.update_check_for_updates);
			localBroadcastManager.sendBroadcast(event);
			AvailableVersionInfo availableVersion = getAvailableVersion(webServer);
			if (getIsUpdatesAvailable(currentVersion, availableVersion)) {
				event.putExtra(MESSAGE_KEY, R.string.update_updates_available);
				localBroadcastManager.sendBroadcast(event);
				
				File zipArchive = downloadUpdates(webServer, availableVersion);
				event.putExtra(MESSAGE_KEY, R.string.update_download);
				localBroadcastManager.sendBroadcast(event);
				
				File apk = extractUpdates(zipArchive);
				event.putExtra(MESSAGE_KEY, R.string.update_install);
				localBroadcastManager.sendBroadcast(event);
				installUpdates(apk);
			} else {
				event.putExtra(STATUS_KEY, UPDATE_FINISHED_KEY);
				event.putExtra(MESSAGE_KEY, R.string.update_nothing_to_update);
				localBroadcastManager.sendBroadcast(event);
			}
		}
		catch (Exception e) {
			event.putExtra(STATUS_KEY, UPDATE_FAILED_KEY);
	        localBroadcastManager.sendBroadcast(event);
		}
		finally{
			IS_RUNNED = false;			            
		}		
	}
}
