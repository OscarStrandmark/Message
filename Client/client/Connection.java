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
		this.address = address;
		this.port = port;
	}
	
	public void sendObject(Object obj) {
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void connect(User user) {
		try {
			socket = new Socket(address,port);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(user);
			oos.flush();
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void disconnect() {
		
	}
	
	private class ServerListener extends Thread {
		
	}
}
