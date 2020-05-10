package com.rnkrsoft.gitserver.receiver;

import com.rnkrsoft.gitserver.app.GitServerApplication;
import com.rnkrsoft.gitserver.dns.DynamicDNSManager;
import com.rnkrsoft.gitserver.service.SSHDaemonService;
import com.rnkrsoft.gitserver.ui.util.Commons;
import com.rnkrsoft.gitserver.ui.util.PrefsConstants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class ConnectivityChangeBroadcastReceiver extends BroadcastReceiver {
	private final static String TAG = ConnectivityChangeBroadcastReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		final String action = intent.getAction();

		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			boolean autostartOnWifiOn = prefs.getBoolean(PrefsConstants.AUTOSTART_ON_WIFI_ON.getKey(), false);
			boolean autostopOnWifiOff = prefs.getBoolean(PrefsConstants.AUTOSTOP_ON_WIFI_OFF.getKey(), false);
			
			if (Commons.isWifiReady(context)) {
	        	Log.i(TAG, "[" + Commons.getWifiSSID(context) + "] WiFi is active!");

	        	final GitServerApplication application = (GitServerApplication)context.getApplicationContext();
	        	long lastDynDnsUpdateTime = application.getUpdateDynDnsTime();
	        	if((System.currentTimeMillis() - lastDynDnsUpdateTime > GitServerApplication.UPDATE_DYNDNS_INTERVAL)) {
					new DynamicDNSManager(context).update();
	        		application.setUpdateDynDnsTime(System.currentTimeMillis());
	        	}
	        	
	        	if(autostartOnWifiOn && !Commons.isSshServiceRunning(context)) {
	        		context.startService(new Intent(context, SSHDaemonService.class));
	        		Commons.makeStatusBarNotification(context);
	        	}
			} else {
				Log.i(TAG, "WiFi is NOT active!");
//				Intent startServiceIntent = new Intent(context, SSHDaemonService.class);
//				if(autostopOnWifiOff && Commons.isSshServiceRunning(context)) {
//					context.stopService(startServiceIntent);
//					Commons.stopStatusBarNotification(context);
//				}
			}
		}
		
	}
	
}
