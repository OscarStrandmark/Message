package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.MediaMessage;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Connection {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private Controller controller;
	private String address;
	private int port;
	
	public Connection(String address, int port, Controller controller) {
		this.controller = controller;
		this.address = address;
		this.port = port;
	}
	
	public void sendObject(Object obj) {
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connect(User user) {
		try {
			socket = new Socket(address,port);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(user);
			oos.flush();
			new ServerListener();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void disconnect() {
		
	}
	
	private class ServerListener extends Thread {
		public void run() {
			while(true) {
				try {
					Message msg = (Message) ois.readObject();
					
					
					if(msg instanceof MediaMessage) {
						controller.incomingMessage((MediaMessage)msg);
					}
					
					else 
						
					if(msg instanceof UpdateMessage) {
						controller.updateConnectedList(((UpdateMessage) msg).getList());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
