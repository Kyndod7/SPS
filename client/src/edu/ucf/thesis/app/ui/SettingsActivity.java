package edu.ucf.thesis.app.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import edu.ucf.thesis.app.R;

public class SettingsActivity extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_settings);
	}

}
