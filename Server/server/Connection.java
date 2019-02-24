package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import shared.Buffer;
import shared.MediaMessage;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Connection {

	private Controller controller;
	
	private ConcurrentHashMap<User, Client> connections;
	private Buffer<Message> messageBuffer;
	
	private int port;
	
	public Connection(Controller controller,int port) {
		this.controller = controller;
		this.port = port;
		new ClientAccepter().start();
		new SendMessageHandler().start();
	}
	
	public void addConnection(User user, Client client) {
		connections.put(user, client);
	}
	
	public void sendMessage(UpdateMessage msg) {
		messageBuffer.put(msg);
	}
	
	private class ClientAccepter extends Thread {
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)){
				System.out.println("Server started on port: " + port);
				while(true) {
					try {
						Socket socket = serverSocket.accept();
						new Client(controller,socket);
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			} catch (Exception e) {
				System.err.println(e);
				System.out.println("Server closing down");
			}
		}
	}

	private class SendMessageHandler extends Thread {
		public void run() {
			Message msg = null;
			while(true) {
				try {
					msg = messageBuffer.get();
					controller.logMessage(msg);
					if(msg instanceof MediaMessage) {
						List<User> receivers = ((MediaMessage) msg).getReceivers();
						Iterator<User> receiverIter = receivers.iterator();
						while(receiverIter.hasNext()) {
							Object user = receiverIter.next();
							Client client = connections.get(user);
							client.sendTo(msg);
						}
					} 
					
					else 
					
					if(msg instanceof UpdateMessage) {
						KeySetView<User, Client> keys = connections.keySet();
						Iterator<User> keyIter = keys.iterator();
						while(keyIter.hasNext()) {
							Object key = keyIter.next();
							Client client = connections.get(key);
							client.sendTo(msg);
						}
					}
					
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		}
	}
}
