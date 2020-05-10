package com.rnkrsoft.gitserver.app;

import com.rnkrsoft.gitserver.ui.util.C;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class GitServerApplication extends Application {
	private final static String TAG = GitServerApplication.class.getSimpleName();
	
	public final static long UPDATE_DYNDNS_INTERVAL = 10L * 60L * 1000L;
	private static GitServerApplication instance;
	
	private long updateDynDnsTime = 0;
	
	public GitServerApplication() {
		GitServerApplication.instance = this;
	}
	
	public synchronized static GitServerApplication getInstance() {
		if(instance == null) {
			throw new IllegalStateException("There is no Application initialized!");
		}
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "[App] Started!");
		
		Intent intent = new Intent(C.action.UPDATE_DYNAMIC_DNS_ADDRESS);
		sendBroadcast(intent);
	}
	
	public void setUpdateDynDnsTime(long updateDynDnsTime) {
		this.updateDynDnsTime = updateDynDnsTime;
	}
	
	public long getUpdateDynDnsTime() {
		return updateDynDnsTime;
	}
	
}
