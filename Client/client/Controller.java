package client;

import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import UI.UIHandler;
import shared.LoginMessage;
import shared.MediaMessage;
import shared.Message;
import shared.User;

public class Controller {

	private static final String SERVERADDRESS = "localhost";
	private static final int PORT = 720;

	private UIHandler ui;
	private Connection connection;
	private ArrayList<User> connectedUsers;
	private ArrayList<User> contacts;
	private ArrayList<MediaMessage> messages;
	private User me;

	public Controller() {
		connectedUsers = new ArrayList<User>();
		connection = new Connection(SERVERADDRESS, PORT, this);
		ui = new UIHandler(this);
		messages = new ArrayList<MediaMessage>();

	}

	public void sendMessage(String text, ImageIcon img, List<User> recipients) {
		Message msg = new MediaMessage(me, recipients, text, img);
		connection.sendMessage(msg);
	}

	public void login(String username, ImageIcon img) {
		me = new User(username, img);
		connection.connect(me);
	}

	public List<User> getConnectedUsers() {
		return connectedUsers;
	}

	public List<User> getContacts() {
		return contacts;
	}

	public void setContacts(List<User> list) {
		// TODO
	}

	public void updateConnectedList(List<User> list) {
		connectedUsers.clear();
		for (User u : list) {
			connectedUsers.add(u);
		}
	}

	public void incomingMessage(Message msg) {
		messages.add((MediaMessage) msg);
		ui.incomingMessage();
	}

	public ArrayList<MediaMessage> getMessageList() {
		return messages;
	}
}
