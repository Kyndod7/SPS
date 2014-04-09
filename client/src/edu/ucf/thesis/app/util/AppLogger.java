package edu.ucf.thesis.app.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import android.content.Intent;
import android.os.Environment;
import edu.ucf.thesis.app.App;

public class AppLogger {
	
	private static final String LOGGER_NAME = "app";
	private static final String FILE_NAME = "app_events.log";
	private static final int MAX_SIZE_KB = 256;
	private static AppLogger mSingleton;
	private Logger mLogger;
	private String mFilePath;
	private FileHandler mFileHandler;
	private EventFormatter mEventFormatter;
	
	public static AppLogger getInstance() {
		if(mSingleton == null) {
			mSingleton = new AppLogger();
		}
		return mSingleton;
	}
	
	private AppLogger() {
		initializeFile();
	}

	private void initializeFile() {
		mFilePath = null;
	    if (Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED) == 0) {
	    	mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
	    } else {
	    	mFilePath = Environment.getDataDirectory().getAbsolutePath();
	    }
	    mFilePath = mFilePath + "/" + FILE_NAME;
	    
		try {
			mFileHandler = new FileHandler(mFilePath, MAX_SIZE_KB*1024, 1, true);
			mFileHandler.setFormatter(mEventFormatter = new EventFormatter());
			mLogger = Logger.getLogger(LOGGER_NAME);
			mLogger.addHandler(mFileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		mFileHandler.close();
		(new File(mFilePath)).delete();
		initializeFile();
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
		LogRecord logRecord = new LogRecord(level, msg);
		Intent intent = new Intent();
		intent.setAction("LOG_EVENT_ACTION");
		intent.putExtra("event", mEventFormatter.format(logRecord));
		App.getApp().sendBroadcast(intent);
		mLogger.log(logRecord);
	}

}
