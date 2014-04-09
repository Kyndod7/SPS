package edu.ucf.thesis.app.push;

public abstract class Notification {

	public static final String SERVER_HELLO = "Server Hello";
	public static final String FORMAT_STRING = "%s;%d";
	private static int mNotificationIdCounter = 1;
	private int mNotificationId;
	
	public Notification() {
		mNotificationId = mNotificationIdCounter++;
	}	
	
	public int getNotificationId() {
		return mNotificationId;
	}
	
	@Override
	public String toString() {
		return String.format(FORMAT_STRING, SERVER_HELLO, getNotificationId());
	}
	
	public abstract void send();
	
}
