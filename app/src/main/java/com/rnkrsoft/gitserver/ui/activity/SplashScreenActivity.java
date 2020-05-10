package com.rnkrsoft.gitserver.ui.activity;

import com.rnkrsoft.gitserver.R;
import com.rnkrsoft.gitserver.ui.util.C;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.actionbarsherlock.view.Window;

public class SplashScreenActivity extends BaseActivity {

	private Handler handler;
	private Runnable timeoutRunnable = new Runnable() {
        public void run() {
        	Intent intent = new Intent(C.action.START_HOME_ACTIVITY);

        	SplashScreenActivity.this.startActivity(intent);
        	SplashScreenActivity.this.finish();
        }
    };
	
	@Override
	protected void setup() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);
	}

	@Override
	protected void initComponents(Bundle savedInstanceState) {
		handler = new Handler();
		handler.postDelayed(timeoutRunnable, 3L * 1000L);
	}

	@Override
	public void onClick(View v) {
	}
}
