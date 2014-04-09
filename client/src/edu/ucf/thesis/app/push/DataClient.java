package edu.ucf.thesis.app.push;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import edu.ucf.thesis.app.util.AppLogger;

public class DataClient {
	
	private static final AppLogger LOGGER = AppLogger.getInstance();
	private static final String CLIENT_HELLO = "Client Hello";
	private final String mServerIp;
	private final int mServerPort;
	
	public DataClient(String serverIp, int serverPort) {
		this.mServerIp = serverIp;
		this.mServerPort = serverPort;
	}
	
	public String retrieveData() {
		return retrieveData("");
	}
	
	public String retrieveData(String message) {
		String response = null;
		String request = CLIENT_HELLO;
		
		if(!message.equals("")) {
			request = request + ";" + message;
		}
		
		try {
			InetAddress host = InetAddress.getByName(mServerIp);
			Socket socket = new Socket(host.getHostAddress(), mServerPort);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(request);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			response = (String) ois.readObject();
			socket.close();
		} catch (IOException e) {
			LOGGER.severe(e);
		} catch (ClassNotFoundException e) {
			LOGGER.severe(e);
		}
		return response;
	}

}
