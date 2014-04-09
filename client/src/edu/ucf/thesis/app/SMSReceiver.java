package edu.ucf.thesis.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import edu.ucf.thesis.app.push.SPSSyncer;
import edu.ucf.thesis.app.util.AppLogger;
import edu.ucf.thesis.app.util.Event;
import edu.ucf.thesis.app.util.Event.PushType;

public class SMSReceiver extends BroadcastReceiver {

	private static final AppLogger LOGGER = AppLogger.getInstance();
	private static final String SERVER_HELLO = "Server Hello";
	private String mSmsMessage;

	public void onReceive(Context context, Intent intent) {
		if(isServerHello(intent)) {
			Event notificationEvent = new Event()
			.parseNotification(mSmsMessage)
			.setPushType(PushType.SPS);
			LOGGER.event(notificationEvent);
			
			(new SPSSyncer(context, notificationEvent.getNotificationId())).getData();
			abortBroadcast();
		}
	}

	private boolean isServerHello(Intent intent) {
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] pdusObj = (Object[]) bundle.get("pdus");
			for (int i = 0; i < pdusObj.length; i++) {
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
				mSmsMessage = smsMessage.getDisplayMessageBody();
				if(mSmsMessage.startsWith(SERVER_HELLO)) {
					return true;
				}
			}
		}
		return false;
	}

}
