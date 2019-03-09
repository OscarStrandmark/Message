package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import shared.Buffer;
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
			socket = new Socket(address,port);
			new ServerSender().start();
			new ServerListener().start();
			messageBuffer.put(new LoginMessage(user));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No server found");
		}
	}
	
	private class ServerSender extends Thread {
		public void run() {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			while(true) {
				try {
					Message msg = messageBuffer.get();
					oos.writeObject(msg);
					oos.flush();
				} catch (Exception e) {
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
			while(true) {
				try {
					Message msg = (Message) ois.readObject();
					
					
					if(msg instanceof MediaMessage) {
						controller.incomingMessage((MediaMessage)msg);
					}
					
					else 
						
					if(msg instanceof UpdateMessage) {
						List<User> list = ((UpdateMessage) msg).getList();
						controller.updateConnectedList(list);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
