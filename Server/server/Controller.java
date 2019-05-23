package server;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import server.Logger;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class Controller {

	private static final int PORT = 720;
	private ArrayList<User> connectedUsers = new ArrayList<User>();
	private Connection connection;
	private static Logger logger = Logger.getInstance();
	private KillSwitchUI killSwitch;


	public Controller() {
		logger.setUI(new LoggerUI(logger));
		connection = new Connection(this, PORT);
		killSwitch = new KillSwitchUI(this);
	}

	public void kill() {
		logger.write();
		System.exit(1);
	}
	
	public void addnewUser(User user, Client client) {
		connectedUsers.add(user);
		connection.addConnection(user,client);
		connection.checkForUnsentMessages(user);
		sendUserList();
		logger.logConnect(user.getUsername());
	}

	public void removeUser(User user) {
		connectedUsers.remove(user);
		sendUserList();
		logger.logDisconnect(user.getUsername());
	}

	public synchronized void sendUserList() {
		connection.sendMessage(new UpdateMessage(new User("SERVER",null), connectedUsers));
	}

	public synchronized void processMessage(Message msg) {
		connection.sendMessage(msg);
		logger.logMessage(msg);
	}
}
