package com.rnkrsoft.gitserver.receiver;

import com.rnkrsoft.gitserver.ui.util.C;
import com.rnkrsoft.gitserver.ui.util.Commons;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ToggleSSHServerBroadcastReceiver extends BroadcastReceiver {
	private final static String TAG = ToggleSSHServerBroadcastReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();

		if (action.equals(C.action.TOGGLE_SSH_SERVER)) {
			if(Commons.isSshServiceRunning(context)) {
				context.stopService(new Intent(C.action.START_SSH_SERVER));
				Log.i(TAG, "Broadcast - stop service!");
			} else if (!Commons.isWifiReady(context)) {
//				Toast.makeText(context, "WiFi is NOT connected!", Toast.LENGTH_SHORT).show();
//				Log.i(TAG, "Broadcast failed - wifi is NOT connected!");
			} else {
				context.startService(new Intent(C.action.START_SSH_SERVER));
				Log.i(TAG, "Broadcast - start service!");
			}
		}
		
	}
	
}
