package edu.ucf.thesis.server.push;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import edu.ucf.thesis.server.util.Event;
import edu.ucf.thesis.server.util.Event.EventType;
import edu.ucf.thesis.server.util.Event.PushType;
import edu.ucf.thesis.server.util.ServerLogger;
import edu.ucf.thesis.server.util.ServerProperties;

public class GCMNotification extends Notification {
	
	private static final ServerLogger LOGGER = ServerLogger.getInstance();
	
	public GCMNotification() {
		super();
	}
	
	@Override
	public void send() {
    	Event sendingEvent = new Event()
		.setPushType(PushType.GCM)
		.setEventType(EventType.SENDING_NOTIFICATION)
		.setNotificationId(getNotificationId())
		.setEventDetails(toString());
		
		Event sentEvent = new Event()
		.setPushType(PushType.GCM)
		.setEventType(EventType.NOTIFICATION_SENT)
		.setNotificationId(getNotificationId());

    	Message message = new Message.Builder()
    	.addData("message", toString())
    	.delayWhileIdle(false)
    	.build();
    	
		ServerProperties properties = ServerProperties.getInstance();
    	Sender sender = new Sender(properties.getServerKey());
		
    	try {
    		LOGGER.event(sendingEvent);
	    	Result result = sender.send(message, properties.getGcmClientId(), 1);
        	LOGGER.event(sentEvent.setEventDetails(result.getMessageId()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
