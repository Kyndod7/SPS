package edu.ucf.thesis.app.ui;

import com.google.android.gcm.GCMRegistrar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import edu.ucf.thesis.app.App;
import edu.ucf.thesis.app.R;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		TabHost.TabSpec batteryTabSpec = mTabHost.newTabSpec("controls");
		batteryTabSpec.setIndicator("Controls");
		mTabHost.addTab(batteryTabSpec, ControlsFragment.class, null);

		TabHost.TabSpec signalTabSpec = mTabHost.newTabSpec("logs");
		signalTabSpec.setIndicator("Logs");
		mTabHost.addTab(signalTabSpec, LogsFragment.class, null);

		registerClient();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent settingIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingIntent);
			break;
		}
		return true;
	}

	@Override
	public void onDestroy() {
		GCMRegistrar.onDestroy(this);
		super.onDestroy();
	}

	/**
	 * This registerClient() method checks the current device, checks the
	 * manifest for the appropriate rights, and then retrieves a registration id
	 * from the GCM cloud. If there is no registration id, GCMRegistrar will
	 * register this device for the specified project, which will return a
	 * registration id.
	 */
	public void registerClient() {
		String registrationId = "";
		String registrationStatus = "Not registered";		
		try {
			// check that the device supports GCM
			GCMRegistrar.checkDevice(this);
			// check the manifest has all the required permissions
			GCMRegistrar.checkManifest(this);
			// get existing registration ID if it exists
			registrationId = GCMRegistrar.getRegistrationId(this);

			if (registrationId.equals("")) {
				// register this device for this project
				GCMRegistrar.register(this, App.PROJECT_ID);
				registrationId = GCMRegistrar.getRegistrationId(this);
				registrationStatus = "Registered";
				// sendRegistrationToServer();
			} else {
				registrationStatus = "Already registered";
			}
		} catch (Exception e) {
			e.printStackTrace();
			registrationStatus = e.getMessage();
		}

		Log.d(TAG, registrationStatus);
		Log.d(TAG, registrationId);
	}

}
