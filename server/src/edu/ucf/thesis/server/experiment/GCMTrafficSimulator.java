package edu.ucf.thesis.server.experiment;

import edu.ucf.thesis.server.push.GCMNotification;

public class GCMTrafficSimulator extends TrafficSimulator {
	
	public GCMTrafficSimulator(UserType userType) {
		super(userType);
	}

	@Override
	public void push() {
		GCMNotification notification = new GCMNotification();
		notification.send();		
	}

}
