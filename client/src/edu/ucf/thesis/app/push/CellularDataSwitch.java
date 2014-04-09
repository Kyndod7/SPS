package edu.ucf.thesis.app.push;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import edu.ucf.thesis.app.util.AppLogger;

public class CellularDataSwitch {

	private final static AppLogger LOGGER = AppLogger.getInstance();
	private static final String EVENT_PREFIX = "Data Interface: ";
	private static final int TRANSITION_TIMEOUT_MS = 2000;
	private final Context mContext;
	private Object miConnectivityManager;
	private Method mSetMobileDataEnabledMethod;
	private Object mSignal;
	private boolean mSuccessfulTransmission;

	public CellularDataSwitch(Context context) {
		mContext = context.getApplicationContext();
		initialize();
	}

	private void initialize() {
		mSignal = new Object();
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
			Field iConnectivityManagerField = connectivityManagerClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			miConnectivityManager = iConnectivityManagerField.get(connectivityManager);
			Class iConnectivityManagerClass = Class.forName(miConnectivityManager.getClass().getName());
			mSetMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			mSetMobileDataEnabledMethod.setAccessible(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public boolean on() {
		LOGGER.info(EVENT_PREFIX + "turning on");
		boolean successfulTransition = enableTransceiver(true);
		if (successfulTransition) {
			LOGGER.info(EVENT_PREFIX + "on");
		} else {
			LOGGER.info(EVENT_PREFIX + "failed to turn on");
		}
		return successfulTransition;
	}

	public void off() {
		LOGGER.info(EVENT_PREFIX + "turning off");
		enableTransceiver(false);
		LOGGER.info(EVENT_PREFIX + "off");
	}
	
	private boolean enableTransceiver(boolean enable) {
		mSuccessfulTransmission = false;
		HandlerThread connectionMonitorThread = registerConnectionMonitor();
		setMobileDataEnabled(enable);
		synchronized(mSignal) {
			try {
				mSignal.wait(TRANSITION_TIMEOUT_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		connectionMonitorThread.quit();
		return mSuccessfulTransmission;
	}
	
	private HandlerThread registerConnectionMonitor() {
		ConnectionMonitor connectionMonitor = new ConnectionMonitor();
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		HandlerThread connectionMonitorThread = new HandlerThread("ConnectionMonitorThread");
		connectionMonitorThread.start();
		Handler handler = new Handler(connectionMonitorThread.getLooper());
		mContext.registerReceiver(connectionMonitor, intentFilter, null, handler);
		return connectionMonitorThread;
	}

	private void setMobileDataEnabled(boolean enable) {
		try {
			mSetMobileDataEnabledMethod.invoke(miConnectivityManager, enable);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private class ConnectionMonitor extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if (!noConnectivity) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					mSuccessfulTransmission = true;
					synchronized(mSignal) {
						mSignal.notifyAll();
					}
				}
			}
		}
		
	}

}
