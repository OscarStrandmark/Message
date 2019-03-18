package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import shared.Buffer;
import shared.DisconnectMessage;
import shared.MediaMessage;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Connection {

	private Controller controller;
	private ConcurrentHashMap<User,ArrayList<Message>> unsentMessages;
	private ConcurrentHashMap<User, Client> connections;
	private Buffer<Message> messageBuffer;
	
	private int port;
	
	public Connection(Controller controller,int port) {
		this.connections = new ConcurrentHashMap<User, Client>();
		this.messageBuffer = new Buffer<Message>();
		this.unsentMessages = new ConcurrentHashMap<User,ArrayList<Message>>();
		this.controller = controller;
		this.port = port;
		new ClientAccepter().start();
		new SendMessageHandler().start();
	}
	
	public void addConnection(User user, Client client) {
		connections.put(user, client);
	}
	
	public void sendMessage(Message msg) {
		messageBuffer.put(msg);
	}
	
	public void checkForUnsentMessages(User u) {
		
		if(unsentMessages.containsKey(u)) {
			ArrayList<Message> messages = unsentMessages.get(u);
			
			for(Message m : messages) {
				MediaMessage msg = (MediaMessage)m;
				ArrayList<User> userlist = new ArrayList<User>();
				userlist.add(u);
				msg.setReceivers(userlist);
				messageBuffer.put(msg);
			}
		}
	}
	
	private class ClientAccepter extends Thread {
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)){
				System.out.println("Server started on port: " + port);
				while(true) {
					try {
						Socket socket = serverSocket.accept();
						System.out.println("Client accepted");
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
			while(true) {
				try {
					Message msg = messageBuffer.get();
					
					if(msg instanceof MediaMessage) {
						
						List<User> receivers = ((MediaMessage) msg).getReceivers();
						Iterator<User> receiverIter = receivers.iterator();
						KeySetView<User,Client> keys = connections.keySet();
						
						while(receiverIter.hasNext()) {
							
							Iterator<User> keyIter = keys.iterator();
							String userString = receiverIter.next().getUsername();
							
							while(keyIter.hasNext()) {
								
								User keyUser = keyIter.next();
								
								if(userString.equals(keyUser.getUsername())) {//Recipient online
									Client c = connections.get(keyUser);
									c.sendTo(msg);
								} else {//Recipient offline
									//add to unsent map
									KeySetView<User,ArrayList<Message>> unsentUsersKeys = unsentMessages.keySet();
									Iterator<User> unsentUsersIter = unsentUsersKeys.iterator();
									while(unsentUsersIter.hasNext()) {
										User u = unsentUsersIter.next();
										
										if(u.getUsername().equals(userString)) {//If user already exists in unsent messages map.
											ArrayList<Message> list = unsentMessages.get(u);
											list.add(msg);
										} else { //If user DOES NOT exist in unsent messages map.
											ArrayList<Message> list = new ArrayList<Message>();
											list.add(msg);
											unsentMessages.put(u, list);
										}
									}
									
								}
							}
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
					
					else
					
					if(msg instanceof DisconnectMessage) {
						User u = msg.getSender();
						connections.get(u).kill();
						connections.remove(u);
						controller.removeUser(u);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
