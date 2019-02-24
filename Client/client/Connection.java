package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.User;

public class Connection {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private String address;
	private int port;
	
	public Connection(String address, int port) {
		
	}
	
	public void sendObject(Object obj) {
		
	}
	
	public void connect(User user) {
		
	}
	
	public void disconnect() {
		
	}
	
	private class ServerListener extends Thread {
		
	}
}
