package edu.ucf.thesis.server.experiment;

import edu.ucf.thesis.server.push.DataServer;
import edu.ucf.thesis.server.push.SPSNotification;

public class SPSSessionTester {
	
	public SPSSessionTester() {
		// do nothing
	}
	
	public void start() {
		DataServer dataServer = new DataServer();
		dataServer.start();
		SPSNotification notification = new SPSNotification();
		notification.send();	
		dataServer.stop();
	}

}
