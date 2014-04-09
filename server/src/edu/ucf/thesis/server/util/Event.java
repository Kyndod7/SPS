package edu.ucf.thesis.server.util;

public class Event {
	
	private EventType mEventType;
	private PushType mPushType;
	private int mNotificationId;
	private String mEventDetails;
	
	public enum EventType {
		SENDING_NOTIFICATION, NOTIFICATION_SENT, SYNC_REQUEST_RECEIVED, SYNC_DATA_SENT;
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
	
	public Event parseSyncRequest(String syncRequest) {
		setEventType(EventType.SYNC_REQUEST_RECEIVED);
		setEventDetails(syncRequest);
		
		String[] notificationParameters = syncRequest.split(";");
		setPushType(PushType.valueOf(notificationParameters[1]));
		setNotificationId(Integer.parseInt(notificationParameters[2]));
		return this;
	}
	
	public String formatEvent() {
		StringBuffer sb = new StringBuffer();
		if(mPushType != null) {
			sb.append(mPushType);
			sb.append(EventFormatter.SEPARATOR);
		}
		if(mNotificationId > 0) {
			sb.append(mNotificationId);
			sb.append(EventFormatter.SEPARATOR);
		}
		if(mEventType != null) {
			sb.append(mEventType);
			sb.append(EventFormatter.SEPARATOR);
		}
		if(mEventDetails != null) {
			sb.append(mEventDetails);
		}
		return sb.toString();
	}

}
