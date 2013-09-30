package com.mss.application.services;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class SystemService {
	private Context mContext;
	public SystemService(Context context) {
		mContext = context;
	}
	
	public String getSystemVersion(){
		String version = android.os.Build.MODEL + ";" +					
				android.os.Build.VERSION.RELEASE + ";" +
				android.os.Build.VERSION.SDK_INT;
		
		return version;
	}
	
	public String getApplicationVersion() {
		try {
			PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
