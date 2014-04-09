package edu.ucf.thesis.app.util;

import java.io.IOException;

public class TrafficGenerator {
	
	private static final String PING_COMMAND = "ping -c 1 -w 5 google.com";
	private static final int PING_INTERVAL_MS = 120000; 
	private Thread mThread;
	
	public void start() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while(!mThread.interrupted()) {
					ping();
					try {
						Thread.sleep(PING_INTERVAL_MS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
	
		});
		mThread.start();
	}
	
	private void ping() {
		try {
			Runtime.getRuntime().exec(PING_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		mThread.interrupt();
	}

}
