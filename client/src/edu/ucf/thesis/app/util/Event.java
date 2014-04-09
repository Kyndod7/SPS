package edu.ucf.thesis.app.util;

public class Event {
	
	private static final String SEPARATOR = ",";
	private EventType mEventType;
	private PushType mPushType;
	private int mNotificationId;
	private String mEventDetails;
	
	public enum EventType {
		NOTIFICATION_RECEIVED, SYNC_REQUEST_SENT, SYNC_DATA_RECEIVED;
	}
	
	public enum PushType {
		SPS, GCM;
	}
		
	public Event() {
		// do nothing
	}
	
	public Event setEventType(EventType eventType) {
		mEventType = eventType;
		return this;
	}
	
	public EventType getEventType() {
		return mEventType;
	}
	
	public Event setPushType(PushType pushType) {
		mPushType = pushType;
		return this;
	}
	
	public PushType getPushType() {
		return mPushType;
	}
	
	public Event setNotificationId(int notificationId) {
		mNotificationId = notificationId;
		return this;
	}
	
	public int getNotificationId() {
		return mNotificationId;
	}
	
	public Event setEventDetails(String eventDetails) {
		mEventDetails = eventDetails;
		return this;
	}
	
	public String getEventDetails(String eventDetails) {
		return mEventDetails;
	}
	
	public Event parseNotification(String notification) {
		setEventType(EventType.NOTIFICATION_RECEIVED);
		setEventDetails(notification);
		String[] notificationParameters = notification.split(";");
		setNotificationId(Integer.parseInt(notificationParameters[1]));
		return this;
	}
	
	public String formatEvent() {
		StringBuffer sb = new StringBuffer();
		if(mPushType != null) {
			sb.append(mPushType);
			sb.append(SEPARATOR);
		}
		if(mNotificationId > 0) {
			sb.append(mNotificationId);
			sb.append(SEPARATOR);
		}
		if(mEventType != null) {
			sb.append(mEventType);
			sb.append(SEPARATOR);
		}
		if(mEventDetails != null) {
			sb.append(mEventDetails);
		}
		return sb.toString();
	}

}
