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

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.commonsware.cwac.wakeful.WakefulIntentService;

public class UpdateService extends WakefulIntentService {
	public static final String UPDATE_EVENT_KEY = "update_status";
	public static final String MESSAGE_KEY = "message_id";
	public static final int EVENT_CHECK_FOR_UPDATES = 0;
	public static final int EVENT_UPDATES_AVAILABLE = 1;
	public static final int EVENT_NEWEST_VERSION_IS_INSTALLED = 2;
	public static final int EVENT_DOWNLOAD_UPDATES = 3;
	public static final int EVENT_INSTALL_UPDATES = 4;
	public static final int EVENT_UPDATE_FAILED = 5;
	
	Intent event;
    LocalBroadcastManager localBroadcastManager;
	
	public UpdateService() {
		super("UpdateService");
				
		event = new Intent(UPDATE_EVENT_KEY);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
	}
	
	@Override
	protected void doWakefulWork(Intent cmd) {
		UpdateRequest req = new UpdateRequest(cmd);
		VersionCheckStrategy vcs = req.getVersionCheckStrategy();

		try {
			if (req.getPhase() == UpdateRequest.PHASE_DOWNLOAD) {
				event.putExtra(MESSAGE_KEY, EVENT_DOWNLOAD_UPDATES);
	            localBroadcastManager.sendBroadcast(event);
				downloadAndInstall(cmd, req, req.getUpdateURL());
			}
			else if (req.getPhase() == UpdateRequest.PHASE_INSTALL) {
				event.putExtra(MESSAGE_KEY, EVENT_INSTALL_UPDATES);
	            localBroadcastManager.sendBroadcast(event);
				install(req, req.getInstallUri());
			}
			else {
				
				event.putExtra(MESSAGE_KEY, EVENT_CHECK_FOR_UPDATES);
	            localBroadcastManager.sendBroadcast(event);
				String availableVersionCode = vcs.getAvailableVersionCode();
				String[] availableVersionFragments = availableVersionCode.split("\\.");
        
				String currentVersionCode= vcs.getCurrentVersionCode();
				String[] currentVersionFragments = currentVersionCode.split("\\.");

				boolean updatesAvailable = false; 
				for (int i = 0; i < availableVersionFragments.length; i++) {
					if (Integer.parseInt(availableVersionFragments[i]) >
						Integer.parseInt(currentVersionFragments[i])) {
				
						updatesAvailable = true;
						break;
					} else if (Integer.parseInt(availableVersionFragments[i]) <
						Integer.parseInt(currentVersionFragments[i])) {
						break;
					}
				}
        
				if (updatesAvailable) {
					event.putExtra(MESSAGE_KEY, EVENT_UPDATES_AVAILABLE);
		            localBroadcastManager.sendBroadcast(event);
					
					ConfirmationStrategy strategy=
							req.getPreDownloadConfirmationStrategy();

					if (strategy == null
							|| strategy.confirm(this,
									buildDownloadPhase(cmd,
											vcs.getUpdateURL()))) {
						downloadAndInstall(cmd, req, vcs.getUpdateURL());
					}
				} else {
					event.putExtra(MESSAGE_KEY, EVENT_NEWEST_VERSION_IS_INSTALLED);
		            localBroadcastManager.sendBroadcast(event);
				}
			}
    	}
    	catch (Exception e) {
    		event.putExtra(MESSAGE_KEY, EVENT_UPDATE_FAILED);
            localBroadcastManager.sendBroadcast(event);
    		Log.e("CWAC-Update", "Exception in applying update", e);
    	}
  	}

	private void downloadAndInstall(Intent cmd, UpdateRequest req,
			String updateURL) throws Exception {
		DownloadStrategy ds=req.getDownloadStrategy();
		Uri apk=ds.downloadAPK(this, updateURL);

		if (apk != null) {
			confirmAndInstall(cmd, req, apk);
		}
	}

	private void confirmAndInstall(Intent cmd, UpdateRequest req, Uri apk)
			throws Exception {
		ConfirmationStrategy strategy=
				req.getPreInstallConfirmationStrategy();

		if (strategy == null
				|| strategy.confirm(this, buildInstallPhase(cmd, apk))) {
			install(req, apk);
		}
	}

	private void install(UpdateRequest req, Uri apk) {
		Intent i;
    
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			i=new Intent(Intent.ACTION_INSTALL_PACKAGE);
			i.putExtra(Intent.EXTRA_ALLOW_REPLACE, true);
		}
		else {
			i=new Intent(Intent.ACTION_VIEW);
		}

		i.setDataAndType(apk, "application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(i);
	}

	private PendingIntent buildDownloadPhase(Intent cmd, String updateURL) {
		UpdateRequest.Builder builder=new UpdateRequest.Builder(this, cmd);

		builder.setPhase(UpdateRequest.PHASE_DOWNLOAD);
		builder.setUpdateURL(updateURL);

		return(builder.buildPendingIntent());
	}

	private PendingIntent buildInstallPhase(Intent cmd, Uri apk) {
		UpdateRequest.Builder builder=new UpdateRequest.Builder(this, cmd);

		builder.setPhase(UpdateRequest.PHASE_INSTALL);
		builder.setInstallUri(apk);

		return(builder.buildPendingIntent());
	}
}
