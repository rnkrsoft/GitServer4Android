package com.rnkrsoft.gitserver.receiver;

import com.rnkrsoft.gitserver.app.GitServerApplication;
import com.rnkrsoft.gitserver.dns.DynamicDNSManager;
import com.rnkrsoft.gitserver.ui.util.Commons;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DynamicDNSReceiver extends BroadcastReceiver {

	private final static String TAG = DynamicDNSReceiver.class.getSimpleName();
	
	public final static int STANDART_REQUEST = 0;
	public final static int SCHEDULED_REQUEST = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		GitServerApplication application = (GitServerApplication)context.getApplicationContext();
		
    	long lastDynDnsUpdateTime = application.getUpdateDynDnsTime();
    	boolean scheduled = intent.getBooleanExtra("scheduled", false);
		if(scheduled || ((System.currentTimeMillis() - lastDynDnsUpdateTime > GitServerApplication.UPDATE_DYNDNS_INTERVAL)
    			&& Commons.isWifiConnected(context))) {
			
			Log.i(TAG, "DynamicDNSReceiver ready to update!");
			
			DynamicDNSManager dynDnsManager = new DynamicDNSManager(context);
			dynDnsManager.update();
			application.setUpdateDynDnsTime(System.currentTimeMillis());
		}
		
	}

}
