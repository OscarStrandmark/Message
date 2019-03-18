package client;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import javax.swing.JOptionPane;

import shared.Buffer;
import shared.DisconnectMessage;
import shared.LoginMessage;
import shared.MediaMessage;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Connection {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Buffer<Message> messageBuffer;
	private Controller controller;
	private String address;
	private int port;
	
	boolean alive;
	
	private Thread sender;
	private Thread listener;
	
	public Connection(String address, int port, Controller controller) {
		messageBuffer = new Buffer<Message>();
		this.controller = controller;
		this.address = address;
		this.port = port;
	}
	
	public void sendMessage(Message msg) {
		messageBuffer.put(msg);
	}
	
	public void connect(User user) {
		try {
			this.alive = true;
			socket = new Socket(address,port);
			sender = new ServerSender();
			listener = new ServerListener();
			sender.start();
			listener.start();
			messageBuffer.put(new LoginMessage(user));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No server found");
		}
	}
	
	public void disconnect(User me) {
		alive = false;
		messageBuffer.put(new DisconnectMessage(me));
		listener.interrupt();
		sender.interrupt();
	}
	
	private class ServerSender extends Thread {
		public void run() {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			while(alive) {
				try {
					Message msg = messageBuffer.get();
					oos.writeObject(msg);
					oos.flush();
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}
	}
	
	private class ServerListener extends Thread {
		public void run() {
			try {
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			while(alive) {
				try {
					Message msg = (Message) ois.readObject();
					
					if(msg instanceof MediaMessage) {
						controller.incomingMessage((MediaMessage)msg);
					}
					
					else 
						
					if(msg instanceof UpdateMessage) {
						List<User> list = ((UpdateMessage) msg).getList();
						System.out.println();
						controller.updateConnectedList(list);
					}
					
				} catch (EOFException EOFE) {
					//Thrown when stream is closed on program shutting down.
				} catch (SocketException se) {
					if(se.getMessage().contains("Connection reset")) {
						//Server probably closed down.
					} else {
						se.printStackTrace();
					}
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}
	}
}
