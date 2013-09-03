package com.mss.utils;

import com.mss.application.AuthenticatorActivity;
import com.mss.infrastructure.web.WebConnectionException;
import com.mss.infrastructure.web.WebServer;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class NetworkHelpers {
	private static final String TAG = "NetworkUtilities";
	public static final String PARAM_SERVER = "server";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";

    /**
     * Executes the network requests on a separate thread.
     * 
     * @param runnable The runnable instance containing network mOperations to
     *        be executed.
     */
    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }

    /**
     * Connects to the Voiper server, authenticates the provided username and
     * password.
     * 
     * @param username The user's username
     * @param password The user's password
     * @param handler The hander instance from the calling UI thread.
     * @param context The context of the calling Activity.
     * @return boolean The boolean result indicating whether the user was
     *         successfully authenticated.
     */
    public static boolean authenticate(String address, String username, String password,
        Handler handler, final Context context) {
    	
        try {
        	WebServer webServer = new WebServer(address);
        	webServer.connect(username, password);
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
            	Log.v(TAG, "Successful authentication");
            }
            sendResult(true, handler, context);
            return true;
        } catch (WebConnectionException e) {
        	if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Error authenticating");
            }
            sendResult(false, handler, context);
            return false;
        }catch (final Throwable e) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Unknown exception when getting authtoken", e);
            }
            sendResult(false, handler, context);
            return false;
        } finally {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "getAuthtoken completing");
            }
        }
    }

    /**
     * Sends the authentication response from server back to the caller main UI
     * thread through its handler.
     * 
     * @param result The boolean holding authentication result
     * @param handler The main UI thread's handler instance.
     * @param context The caller Activity's context.
     */
    private static void sendResult(final Boolean result, final Handler handler,
        final Context context) {
        if (handler == null || context == null) {
            return;
        }
        handler.post(new Runnable() {
            public void run() {
                ((AuthenticatorActivity) context).onAuthenticationResult(result);
            }
        });
    }

    /**
     * Attempts to authenticate the user credentials on the server.
     * 
     * @param username The user's username
     * @param password The user's password to be authenticated
     * @param handler The main UI thread's handler instance.
     * @param context The caller Activity's context
     * @return Thread The thread on which the network mOperations are executed.
     */
    public static Thread attemptAuth(final String server, final String username,
        final String password, final Handler handler, final Context context) {
        final Runnable runnable = new Runnable() {
            public void run() {
                authenticate(server, username, password, handler, context);
            }
        };
        // run on background thread.
        return NetworkHelpers.performOnBackgroundThread(runnable);
    }
}
