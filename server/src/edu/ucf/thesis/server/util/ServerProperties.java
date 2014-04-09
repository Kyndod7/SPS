package edu.ucf.thesis.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {
	
	private static final String FILE_NAME = "server.properties";
	private static ServerProperties mInstance;
	private Properties mProperties;
	private int mSimulationDuration;
	private int mServerPort;
	private int mBusinessUserArrivalRate;
	private int mSocialUserArrivalRate;
	private int mNumberSessions;
	private int mSessionGapMs;
	private float mSimulationActiveHours;
	private String mServerKey;
	private String mProxyEmailUsername;
	private String mProxyEmailPassword;
	private String mSpsClientId;
	private String mGcmClientId;
	
	public static ServerProperties getInstance() {
		if (mInstance == null) {
			mInstance = new ServerProperties();
		}
		return mInstance;
	}
	
	private ServerProperties() {
		initialize();
	}
	
	private void initialize() {
		try {
			mProperties = new Properties();
			mProperties.load(new FileInputStream(FILE_NAME));
			loadProperties();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadProperties() {
		mSimulationDuration = Integer.parseInt(mProperties.getProperty("simulation_duration"));
		mSimulationActiveHours = Float.parseFloat(mProperties.getProperty("simulation_active_hours"));
		mServerPort = Integer.parseInt(mProperties.getProperty("server_port"));
		mServerKey = mProperties.getProperty("server_key");
		mProxyEmailUsername = mProperties.getProperty("proxy_email_username");
		mProxyEmailPassword = mProperties.getProperty("proxy_email_password");
		mSpsClientId = mProperties.getProperty("sps_client_id");
		mGcmClientId = mProperties.getProperty("gcm_client_id");
		mBusinessUserArrivalRate = Integer.parseInt(mProperties.getProperty("business_user_arrival_rate"));
		mSocialUserArrivalRate = Integer.parseInt(mProperties.getProperty("social_user_arrival_rate"));
		mNumberSessions = Integer.parseInt(mProperties.getProperty("experiment_delay_number_sessions"));
		mSessionGapMs = Integer.parseInt(mProperties.getProperty("experiment_delay_sessions_gap_ms"));
	}

	public Properties getProperties() {
		return mProperties;
	}

	public int getSimulationDuration() {
		return mSimulationDuration;
	}

	public int getServerPort() {
		return mServerPort;
	}

	public float getSimulationActiveHours() {
		return mSimulationActiveHours;
	}

	public String getServerKey() {
		return mServerKey;
	}

	public String getProxyEmailUsername() {
		return mProxyEmailUsername;
	}

	public String getProxyEmailPassword() {
		return mProxyEmailPassword;
	}

	public String getSpsClientId() {
		return mSpsClientId;
	}

	public String getGcmClientId() {
		return mGcmClientId;
	}

	public int getBusinessUserArrivalRate() {
		return mBusinessUserArrivalRate;
	}

	public int getSocialUserArrivalRate() {
		return mSocialUserArrivalRate;
	}
	
	public int getNumberSessions() {
		return mNumberSessions;
	}

	public int getSessionGapMs() {
		return mSessionGapMs;
	}

}
