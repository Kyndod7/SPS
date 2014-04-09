package edu.ucf.thesis.server.push;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ucf.thesis.server.util.Event;
import edu.ucf.thesis.server.util.Event.EventType;
import edu.ucf.thesis.server.util.ServerLogger;
import edu.ucf.thesis.server.util.ServerProperties;

public class DataServer {

	private final static ServerLogger LOGGER = ServerLogger.getInstance();
	private ServerSocket mServerSocket;
	private Thread mServerThread;
	private String mData = "<data>";
	
	public DataServer() {
		initialize();
	}
	
	private void initialize() {
		try {
			int port = ServerProperties.getInstance().getServerPort();
			mServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public void start() {
		mServerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while(!mServerThread.isInterrupted()) {
					try {
						Socket clientSocket = mServerSocket.accept();
						new SessionHandler(clientSocket);
					} catch (IOException e) {
						LOGGER.severe(e.getMessage());
					}
				}
			}
			
		});
		mServerThread.start();
	}

	public void setData(String data) {
		mData = data;
	}
	
	public void stop() {
		mServerThread.interrupt();
//		try {
//			mServerSocket.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private class SessionHandler implements Runnable {

		private Socket mClientSocket;
		
		private SessionHandler(Socket clientSocket) {
			mClientSocket = clientSocket;
			new Thread(this, SessionHandler.class.getSimpleName()).start();
		}

		@Override
		public void run() {
			Event syncRequestReceivedEvent = new Event();
			syncRequestReceivedEvent.parseSyncRequest(getSyncRequest());
			LOGGER.event(syncRequestReceivedEvent);
			
			Event syncDataSentEvent = new Event()
			.setPushType(syncRequestReceivedEvent.getPushType())
			.setEventType(EventType.SYNC_DATA_SENT)
			.setNotificationId(syncRequestReceivedEvent.getNotificationId())
			.setEventDetails(mData);
			sendSyncData();
			LOGGER.event(syncDataSentEvent);
		}
		
		private String getSyncRequest() {
			String clientMessage = "";
			try {
				// reads client message
				ObjectInputStream ois = new ObjectInputStream(mClientSocket.getInputStream());
				clientMessage = (String) ois.readObject();
			} catch (IOException e) {
				LOGGER.warning(e.getMessage());
			} catch (ClassNotFoundException e) {
				LOGGER.warning(e.getMessage());
			}
			return clientMessage;
		}
		
		private void sendSyncData() {
			try {
				// sends server responses=
				ObjectOutputStream oos = new ObjectOutputStream(mClientSocket.getOutputStream());
				oos.writeObject(mData);
				// closes connection
				mClientSocket.close();
			} catch (IOException e) {
				LOGGER.warning(e.getMessage());
			}
		}
	}

}
