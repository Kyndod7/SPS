package edu.ucf.thesis.app.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.ucf.thesis.app.util.AppLogger;
import edu.ucf.thesis.app.util.Event;
import edu.ucf.thesis.app.util.Event.EventType;
import edu.ucf.thesis.app.util.Event.PushType;

public class SPSSyncer {
	
	private final static AppLogger LOGGER = AppLogger.getInstance();
	private CellularDataSwitch mDataInterface;
	private DataClient mDataClient;
	private int mNotificationId;
	
	public SPSSyncer(Context context, int notificationId) {
		mNotificationId = notificationId;
		initialize(context);
	}
	
	private void initialize(Context context) {
		mDataInterface = new CellularDataSwitch(context);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String serverIp = pref.getString("setting_server_ip", "");
		int serverPort = Integer.parseInt(pref.getString("setting_server_port", "0"));
		mDataClient = new DataClient(serverIp, serverPort);
	}
	
	public void getData() {
		Event syncSentEvent = new Event()
		.setEventType(EventType.SYNC_REQUEST_SENT)
		.setPushType(PushType.SPS)
		.setNotificationId(mNotificationId);
		Event syncReceivedEvent = new Event()
		.setEventType(EventType.SYNC_DATA_RECEIVED)
		.setPushType(PushType.SPS)
		.setNotificationId(mNotificationId);
		
		mDataInterface.on();
		LOGGER.event(syncSentEvent);
		String data = mDataClient.retrieveData(syncSentEvent.getPushType() + ";" + syncSentEvent.getNotificationId());
		LOGGER.event(syncReceivedEvent.setEventDetails(data));
		mDataInterface.off();
	}

}
