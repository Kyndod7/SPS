package edu.ucf.thesis.server.experiment;

import edu.ucf.thesis.server.push.DataServer;
import edu.ucf.thesis.server.push.GCMNotification;

public class GCMSessionTester {

	public GCMSessionTester() {
		// do nothing
	}
	
	public void start() {
		DataServer dataServer = new DataServer();
		dataServer.start();
		GCMNotification notification = new GCMNotification();
		notification.send();	
		dataServer.stop();
	}
	
}
