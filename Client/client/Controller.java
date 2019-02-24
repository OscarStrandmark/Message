package client;

import java.util.List;

import javax.swing.ImageIcon;

import UI.UIHandler;
import shared.LoginMessage;
import shared.User;

public class Controller {

	private UIHandler ui;
	private Connection connection;
	private List<User> connectedUsers;
	private List<User> contacts;
	
	public Controller() {
	
	}
	
	public void login(String username, ImageIcon img) {
		LoginMessage msg = new LoginMessage(new User(username, img));
		connection.sendObject(msg);
	}
	
	public List<User> getContacts() {
		return contacts;
	}
	
	public void setContacts(List<User> list) {
		this.contacts = list;
	}
}
