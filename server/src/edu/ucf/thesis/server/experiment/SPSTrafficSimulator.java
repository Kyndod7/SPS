package edu.ucf.thesis.server.experiment;

import edu.ucf.thesis.server.push.SPSNotification;

public class SPSTrafficSimulator extends TrafficSimulator {
	
	public SPSTrafficSimulator(UserType userType) {
		super(userType);
	}

	@Override
	public void push() {
		SPSNotification notification = new SPSNotification();
		notification.send();	
	}

}
