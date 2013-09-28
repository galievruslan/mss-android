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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.mss.infrastructure.web.WebServer;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class MssHttpDownloadStrategy implements DownloadStrategy {

	protected String url=null;	
	protected String login=null;
	protected String password=null;
	
	public MssHttpDownloadStrategy(String url, String login, String password) {
		this.url = url;
		this.login = login;
		this.password = password;
	}
	
	/**
	 * Constructor for use with Parcelable
	 * @param in Parcel from which to reconstitute this object
	 */
	private MssHttpDownloadStrategy(Parcel in) {
		String[] array = new String[3];
		in.readStringArray(array);
		url = array[0];
		login = array[1];
		password = array[2];
	}
	
  public Uri downloadAPK(Context ctxt, String fileUrl) throws Exception {
	  File zip = getDownloadFile(ctxt);
	  if (zip.exists()) {
		  zip.delete();
	  }

	  WebServer webServer = new WebServer(url);
	  webServer.connect(login, password);
	  webServer.download(fileUrl, zip.getAbsolutePath());

	  File apk = getApkFile(ctxt);
	  if (apk.exists()) {
		  apk.delete();
	  }
	  
	  unpackZip(apk.getAbsolutePath(), zip.getAbsolutePath());	  
	  return(getDownloadUri(ctxt, apk));
  }

  public int describeContents() {
    return(0);
  }

  public void writeToParcel(Parcel dest, int flags) {
	  dest.writeStringArray(new String[] {url, login, password});
  }

    protected File getDownloadFile(Context ctxt) {
    	File updateDir=
        new File(ctxt.getExternalFilesDir(null), ".CWAC-Update");

    	updateDir.mkdirs();

    	return(new File(updateDir, "update.zip"));
    }
  
    protected File getApkFile(Context ctxt) {
	    File updateDir=
	        new File(ctxt.getExternalFilesDir(null), ".CWAC-Update");

	    updateDir.mkdirs();

	    return(new File(updateDir, "update.apk"));
    }
  
    protected OutputStream openDownloadFile(Context ctxt, File apk) throws FileNotFoundException {
    	return(new FileOutputStream(apk));
    }
  
    protected Uri getDownloadUri(Context ctxt, File apk) {
    	return(Uri.fromFile(apk));
    }

    public static final Parcelable.Creator<MssHttpDownloadStrategy> CREATOR=
    	new Parcelable.Creator<MssHttpDownloadStrategy>() {
        public MssHttpDownloadStrategy createFromParcel(Parcel in) {
        	return(new MssHttpDownloadStrategy(in));
        }

        public MssHttpDownloadStrategy[] newArray(int size) {
        	return(new MssHttpDownloadStrategy[size]);
        }
    };
  
    private boolean unpackZip(String unzippedpath, String zippath)
    {       
       InputStream is;
       ZipInputStream zis;
       try 
       {
           is = new FileInputStream(zippath);
           zis = new ZipInputStream(new BufferedInputStream(is));          
           ZipEntry ze;

           while((ze = zis.getNextEntry()) != null) 
           {
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               byte[] buffer = new byte[1024];
               int count;

               FileOutputStream fout = new FileOutputStream(unzippedpath);

               // reading and writing
               while((count = zis.read(buffer)) != -1) 
               {
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
       catch(IOException e)
       {
           e.printStackTrace();
           return false;
       }

       return true;
    }
}