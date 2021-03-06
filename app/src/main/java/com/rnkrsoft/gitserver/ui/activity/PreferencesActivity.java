package com.rnkrsoft.gitserver.ui.activity;

import com.rnkrsoft.gitserver.R;
import com.rnkrsoft.gitserver.ui.util.C;
import com.rnkrsoft.gitserver.ui.util.Commons;
import com.rnkrsoft.gitserver.ui.util.PrefsConstants;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class PreferencesActivity extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener {

	private EditTextPreference sshPortPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		
		sshPortPreferences = (EditTextPreference) getPreferenceScreen().findPreference(PrefsConstants.SSH_PORT.getKey());
		
		Preference helpButton = (Preference)findPreference(PrefsConstants.HELP.getKey());
		helpButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference arg0) { 
					Commons.showTutorialDialog(PreferencesActivity.this);
				    return true;
				}
			});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(C.action.START_HOME_ACTIVITY);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			finish();
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
		
		sshPortPreferences.setSummary("SSH服务器端口: " + prefs.getString(PrefsConstants.SSH_PORT.getKey(), PrefsConstants.SSH_PORT.getDefaultValue()));
		
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(key.equals(PrefsConstants.SSH_PORT)) {
			sshPortPreferences.setSummary("SSH服务器端口: " + sharedPreferences.getString(
					PrefsConstants.SSH_PORT.getKey(), PrefsConstants.SSH_PORT.getDefaultValue()));
		}
	}

}
