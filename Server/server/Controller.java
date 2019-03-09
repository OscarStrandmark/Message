package server;

import java.util.ArrayList;
import java.util.List;
import server.Logger;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Controller {

	private static final int PORT = 720;
	private static final String LOGFILEPATH = "";
	private List<User> connectedUsers = new ArrayList<User>();
	private Connection connection;
	private Logger log;
	
	public Controller() {
//		new Connection(this,PORT);
		
		connection = new Connection(this, PORT);
//		log = new Logger(LOGFILEPATH);
	}
	
	public void addnewUser(User user, Client client) {
		connectedUsers.add(user);
		System.out.println(" Controller: Userobjekt "+user.toString()+" har lagts in i ConnectedUsers");

		connection.addConnection(user,client);
		System.out.println("Controller: Userobjekt "+user.toString()+" har lagts in i connections i Connection genom addConnections()");

		sendUserList();
		System.out.println("Controller: SendUserList() exekverad...");
	}

	public void sendUserList() {
		connection.sendMessage(new UpdateMessage(new User("SERVER",null), connectedUsers));
	}
	
	public void logMessage(Message msg) {
		//todo
	}
	
	public synchronized void processMessage(Message msg) {
		logMessage(msg);
		System.out.println("Controller: Meddelande "+msg.toString()+" är i controller/processMessage()");

		connection.sendMessage(msg);
	}
}