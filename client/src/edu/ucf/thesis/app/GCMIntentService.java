package edu.ucf.thesis.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import edu.ucf.thesis.app.push.GCMSyncer;
import edu.ucf.thesis.app.util.AppLogger;
import edu.ucf.thesis.app.util.Event;
import edu.ucf.thesis.app.util.Event.PushType;

public class GCMIntentService extends GCMBaseIntentService {

	private final static AppLogger LOGGER = AppLogger.getInstance();
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(App.PROJECT_ID);
	}

	@Override
	protected void onError(Context ctx, String sError) {
		LOGGER.info("GCM error: " + sError);
		Log.d(TAG, "GCM error: " + sError);
	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		String message = intent.getStringExtra("message");
		
		Event notificationEvent = new Event()
		.parseNotification(message)
		.setPushType(PushType.GCM);
		LOGGER.event(notificationEvent);
		Log.d(TAG, "GCM notification received: " + message);
		
		(new GCMSyncer(ctx, notificationEvent.getNotificationId())).getData();
	}

	@Override
	protected void onRegistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send regId to application server
	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send notification to application server to remove regId
	}

}
