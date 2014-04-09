package edu.ucf.thesis.server.experiment;

import edu.ucf.thesis.server.push.DataServer;
import edu.ucf.thesis.server.util.ServerProperties;

public abstract class TrafficSimulator {
	
	private UserType mUserType;
	private ServerProperties mProperties;
	
	public enum UserType {
		BUSINESS, SOCIAL;
	}
	
	public TrafficSimulator(UserType userType) {
		mUserType = userType;
		initialize();
	}
	
	private void initialize() {
		mProperties = ServerProperties.getInstance();
	}
	
	public void start() {
		DataServer dataServer = new DataServer();
		dataServer.start();
		
		float currentTime = 0f;
		while(currentTime < mProperties.getSimulationDuration()) {
			float nextTime = getNextEventTime();
			currentTime += nextTime;

			try {
				Thread.sleep((long) (nextTime*60*60*1000));
			} catch (InterruptedException e) {
				// do nothing
			}
			
			push();
		}
		
		dataServer.stop();
	}
	
	private float getNextEventTime() {
		int arrivalRate = 0; 
		switch(mUserType) {
		case BUSINESS:
			arrivalRate = mProperties.getBusinessUserArrivalRate();
			break;
		case SOCIAL:
			arrivalRate = mProperties.getSocialUserArrivalRate();
			break;
		}
		
		float arrivalRatePerHour = (float) arrivalRate / (float) mProperties.getSimulationActiveHours();
		return (float) Math.abs( Math.log(Math.random()) / arrivalRatePerHour );
	}
	
	public abstract void push();

}
