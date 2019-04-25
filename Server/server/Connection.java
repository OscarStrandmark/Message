package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
	private HashMap<User,ArrayList<Message>> unsentMessages;
	private ConcurrentHashMap<User, Client> connections;
	private Buffer<Message> messageBuffer;

	private int port;

	public Connection(Controller controller,int port) {
		this.connections = new ConcurrentHashMap<User, Client>();
		this.messageBuffer = new Buffer<Message>();
		this.unsentMessages = new HashMap<User,ArrayList<Message>>();
		this.controller = controller;
		this.port = port;
		new ClientAccepter().start();
		new SendMessageHandler().start();
	}

	//Add a new connection to the servers list of connected clients.
	public void addConnection(User user, Client client) {
		connections.put(user, client);
	}

	public void sendMessage(Message msg) {
		messageBuffer.put(msg);
	}

	//Check for messages sent to the parameter user while they were offline. Called when a user connects to the server.
	public void checkForUnsentMessages(User u) {

		Iterator<User> iter = unsentMessages.keySet().iterator();
		User theUser = null;
		while(iter.hasNext()) {
			User user = iter.next();
			if(u.getUsername().equals(user.getUsername())) {
				theUser = user;
				ArrayList<Message> messages = unsentMessages.get(user);
				for(Message m : messages) {
					MediaMessage msg = (MediaMessage)m;
					ArrayList<User> userList = new ArrayList<User>();
					userList.add(u);
					msg.setReceivers(userList);
					messageBuffer.put(msg);
				}
			}
		}
		if(theUser != null) {
			unsentMessages.remove(theUser);
		}
	}

	//Class that handles accepting new connections to the server.
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

	//Class that handles outgoing messages on the server.
	private class SendMessageHandler extends Thread {
		public void run() {
			while(true) {
				try {
					Message msg = messageBuffer.get();

					if(msg instanceof MediaMessage) {

						List<User> receivers = ((MediaMessage) msg).getReceivers();
						Iterator<User> receiverIter = receivers.iterator();
						KeySetView<User,Client> keys = connections.keySet();

						while(receiverIter.hasNext()) { //Send message to all users.

							Iterator<User> keyIter = keys.iterator();
							String userString = receiverIter.next().getUsername();

							while(keyIter.hasNext()) { //Check if user is online or offline.

								User keyUser = keyIter.next();

								if(userString.equals(keyUser.getUsername())) {//Recipient online
									Client c = connections.get(keyUser);
									c.sendTo(msg);
								} else {//Recipient offline
									//add to unsent map
									Set<User> unsentUsersKeys = unsentMessages.keySet();
									Iterator<User> unsentUsersIter = unsentUsersKeys.iterator();
									boolean empty = true;
									//If unsentMessages is empty, the boolean empty variable will be kept as true and handled in the if conditional on line 140.
									//If this boolean wasn't here, the iterator would never enter the while-loop below since there are nothing to iterate over

									while(unsentUsersIter.hasNext()) {
										empty = false;
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
									//conditional mentioned in comment above.
									if(empty) {
										ArrayList<Message> list = new ArrayList<Message>();
										list.add(msg);
										unsentMessages.put(new User(userString, null), list);
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
