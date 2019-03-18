package server;

import java.util.ArrayList;
import java.util.List;
import server.Logger;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Controller {

	private static final int PORT = 720;
	private List<User> connectedUsers = new ArrayList<User>();
	private Connection connection;
	
	public Controller() {		
		connection = new Connection(this, PORT);
	}
	
	public void addnewUser(User user, Client client) {
		connectedUsers.add(user);
		connection.addConnection(user,client);
		connection.checkForUnsentMessages(user);
		sendUserList();
	}

	public void removeUser(User user) {
		connectedUsers.remove(user);
		
		sendUserList();
	}
	
	public synchronized void sendUserList() {
		connection.sendMessage(new UpdateMessage(new User("SERVER",null), connectedUsers));
	}
	
	public synchronized void processMessage(Message msg) {
		connection.sendMessage(msg);
	}
}