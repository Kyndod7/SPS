package edu.ucf.thesis.app;

import android.app.Application;

public class App extends Application {

	public static final String PROJECT_ID = "<YOUR PROJECT ID>";
	private static App mInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

    public static App getApp() {
    	return mInstance;
    }

}
