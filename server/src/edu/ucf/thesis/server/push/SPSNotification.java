package edu.ucf.thesis.server.push;

import edu.ucf.thesis.server.util.Event;
import edu.ucf.thesis.server.util.ServerLogger;
import edu.ucf.thesis.server.util.ServerProperties;
import edu.ucf.thesis.server.util.Event.EventType;
import edu.ucf.thesis.server.util.Event.PushType;

public class SPSNotification extends Notification {

	private static final ServerLogger LOGGER = ServerLogger.getInstance();
	
	public SPSNotification() {
		super();
	}

	@Override
	public void send() {
    	Event sendingEvent = new Event()
		.setPushType(PushType.SPS)
		.setEventType(EventType.SENDING_NOTIFICATION)
		.setNotificationId(getNotificationId())
		.setEventDetails(toString());
		
		Event sentEvent = new Event()
		.setPushType(PushType.SPS)
		.setEventType(EventType.NOTIFICATION_SENT)
		.setNotificationId(getNotificationId());
		
		String clientId = ServerProperties.getInstance().getSpsClientId();
		SMSSender smsSender = SMSSender.getInstace();
		LOGGER.event(sendingEvent);
		smsSender.send(toString(), clientId);
        LOGGER.event(sentEvent);
	}
	
}
