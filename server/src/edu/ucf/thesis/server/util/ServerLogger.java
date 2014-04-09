package edu.ucf.thesis.server.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLogger {
	
	private static final String LOGGER_NAME = "server";
	private static ServerLogger mInstance;
	private Logger mLogger;
	
	public static ServerLogger getInstance() {
		if(mInstance == null) {
			mInstance = new ServerLogger();
		}
		return mInstance;
	}
	
	private ServerLogger() {
		initializeFile();
	}

	private void initializeFile() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			FileHandler fh = new FileHandler("events_" + sdf.format(new Date())+ ".log");
			fh.setFormatter(new EventFormatter());
			mLogger = Logger.getLogger(LOGGER_NAME);
			mLogger.addHandler(fh);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void event(Event event) {
		info(event.formatEvent());
	}
	
	public void severe(Exception ex) {
		info(ex.getMessage());
	}
	
	public void severe(String msg) {
		log(Level.SEVERE, msg);
	}
	
	public void warning(Exception ex) {
		info(ex.getMessage());
	}
	
	public void warning(String msg) {
		log(Level.WARNING, msg);
	}
	
	public void info(Exception ex) {
		info(ex.getMessage());
	}
	
	public void info(String msg) {
		log(Level.INFO, msg);
	}
	
	private void log(Level level, String msg) {
		mLogger.log(level, msg);
	}

}
