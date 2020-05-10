package com.rnkrsoft.gitserver.ui.widget;

import com.rnkrsoft.gitserver.R;
import com.rnkrsoft.gitserver.ui.util.C;
import com.rnkrsoft.gitserver.ui.util.Commons;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.widget.RemoteViews;

public class ToggleAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		context.startService(new Intent(context, MyUpdateService.class));
	}
	
	public static class MyUpdateService extends Service {
		
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// Update the widget
			buildRemoteView(this);

			// No more updates so stop the service and free resources
			stopSelf();
			
			return Service.START_NOT_STICKY;
		}
		
		public void buildRemoteView(Context context) {
			RemoteViews updateView = null;

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(C.action.TOGGLE_SSH_SERVER), PendingIntent.FLAG_UPDATE_CURRENT);
			
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.toggle_widget);
			views.setOnClickPendingIntent(R.id.toggleWidgetButton, pendingIntent);
			
			if(Commons.isSshServiceRunning(context)) {
				views.setImageViewResource(R.id.toggleWidgetButton, R.drawable.ic_widget_active);
			} else {
				views.setImageViewResource(R.id.toggleWidgetButton, R.drawable.ic_widget_inactive);
			}

			ComponentName myWidget = new ComponentName(this, ToggleAppWidgetProvider.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(myWidget, updateView);
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			int oldOrientation = this.getResources().getConfiguration().orientation;

			if (newConfig.orientation != oldOrientation) {
				// Update the widget
				buildRemoteView(this);
			}
		}

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	}

}
