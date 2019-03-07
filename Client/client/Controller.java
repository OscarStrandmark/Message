package client;

import java.util.List;

import javax.swing.ImageIcon;

import UI.UIHandler;
import shared.LoginMessage;
import shared.MediaMessage;
import shared.Message;
import shared.User;

public class Controller {

	private static final String SERVERADDRESS = "localhost";
	private static final int PORT = 42069;
	
	private UIHandler ui;
	private Connection connection;
	private List<User> connectedUsers;
	private List<User> contacts;
	
	private User me;
	
	
	public Controller() {
		connection = new Connection(SERVERADDRESS, PORT);
		ui = new UIHandler(this);
		
	}
	
	public void sendMessage(String text, ImageIcon img, List<User> recipients) {
		Message msg = new MediaMessage(me, recipients, text, img);
		connection.sendObject(msg);
	}
	
	public void login(String username, ImageIcon img) {
		me = new User(username, img);
		LoginMessage msg = new LoginMessage(me);
		connection.sendObject(msg);
	}
	
	public List<User> getConnectedUsers(){
		return connectedUsers;
	}
	public List<User> getContacts() {
		return contacts;
	}
	
	public void setContacts(List<User> list) {
		this.contacts = list;
	}
}
