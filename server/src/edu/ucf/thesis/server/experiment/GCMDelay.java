package edu.ucf.thesis.server.experiment;

import edu.ucf.thesis.server.push.DataServer;
import edu.ucf.thesis.server.push.GCMNotification;
import edu.ucf.thesis.server.util.ServerProperties;

public class GCMDelay {

	public GCMDelay() {
		// do nothing
	}
	
	public void start() {
		DataServer dataServer = new DataServer();
		dataServer.start();
		
		ServerProperties properties = ServerProperties.getInstance();
		int numSessions = properties.getNumberSessions();
		int sessionGapMs = properties.getSessionGapMs();
		
		for(int i = 0; i < numSessions; i++) {
			GCMNotification notification = new GCMNotification();
			notification.send();
			
			try {Thread.sleep(sessionGapMs);} catch (InterruptedException e) {}
		}
		
		dataServer.stop();
	}

}
