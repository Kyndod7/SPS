package edu.ucf.thesis.app.push;

import android.content.Context;
import android.net.wifi.WifiManager;

public class WifiSwitch {
	
	private Context mContext;
	
	public WifiSwitch(Context context) {
		mContext = context.getApplicationContext();
	}
	
	public void on() {
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE); 
		wifiManager.setWifiEnabled(true);
	}
	
	public void off() {
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE); 
		wifiManager.setWifiEnabled(false);
	}

}
