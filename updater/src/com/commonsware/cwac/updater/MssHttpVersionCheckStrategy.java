/***
Copyright (c) 2012 CommonsWare, LLC

Licensed under the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License. You may obtain
a copy of the License at
  http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.commonsware.cwac.updater;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.mss.infrastructure.web.WebServer;

/**
 * VersionCheckStrategy implementation that downloads
 * a public-visible JSON document via HTTP and extracts
 * information about the available version from it.
 * 
 * The JSON document needs to be a JSON object containing
 * a versionCode and an updateURL value. The versionCode
 * should be the android:versionCode value of the latest
 * APK available for download. The updateURL can provide
 * information to your chosen DownloadStrategy of where
 * to download the APK. For example, the updateURL could
 * be a URL to a publicly-visible APK for download. The
 * JSON document can have other contents if desired, but
 * they will be ignored.
 * 
 * This implementation is fairly simplistic, just blindly
 * downloading the document. In particular, it will not
 * handle a failover (e.g., drop off WiFi and fail over
 * to 3G).
 *
 */
public class MssHttpVersionCheckStrategy implements VersionCheckStrategy {
	private static final String JSON_VERSION_CODE="version";
	private static final String JSON_UPDATE_URL="file";
	protected String url=null;	
	protected String login=null;
	protected String password=null;
	protected String updateURL=null;
	
	protected String currentVersion=null;

	/**
	 * Basic constructor
	 * @param url Location of the JSON document to download
	 */
	public MssHttpVersionCheckStrategy(String url, String login, String password, String currentVersion) {
		this.url=url;
		this.login = login;
		this.password = password;	
		this.currentVersion = currentVersion;
	}

	/**
	 * Constructor for use with Parcelable
	 * @param in Parcel from which to reconstitute this object
	 */
	private MssHttpVersionCheckStrategy(Parcel in) {
		String[] array = new String[4];
		in.readStringArray(array);
		url = array[0];
		login = array[1];
		password = array[2];
		currentVersion = array[3];
	}

	/* (non-Javadoc)
	 * @see com.commonsware.cwac.updater.VersionCheckStrategy#getVersionCode()
	 */
	public String getAvailableVersionCode() throws Exception {
		
		WebServer webServer;
		try {
			webServer = new WebServer(url);
			webServer.connect(login, password);
		  
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_type", "android"));
			String response = webServer.get("/synchronization/mobile_client.json", params);

			JSONObject json=new JSONObject(response);

			updateURL=json.getString(JSON_UPDATE_URL);
			return(json.getString(JSON_VERSION_CODE));
						
		} finally {}
	}
	
	public String getCurrentVersionCode() {
		return currentVersion;
	}
  
	/* (non-Javadoc)
	 * @see com.commonsware.cwac.updater.VersionCheckStrategy#getUpdateURL()
	 */
	public String getUpdateURL() {
		return('/' + updateURL);
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	public int describeContents() {
		return(0);
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {url, login, password, currentVersion});
	}

	/**
	 * Required to complete Parcelable interface. Creates
	 * an SimpleHttpVersionCheckStrategy instance or array
	 * upon demand.
	 */
	public static final Parcelable.Creator<MssHttpVersionCheckStrategy> CREATOR=
		new Parcelable.Creator<MssHttpVersionCheckStrategy>() {
        	public MssHttpVersionCheckStrategy createFromParcel(Parcel in) {
        		return(new MssHttpVersionCheckStrategy(in));
        	}

        	public MssHttpVersionCheckStrategy[] newArray(int size) {
        		return(new MssHttpVersionCheckStrategy[size]);
        	}
      };
}
