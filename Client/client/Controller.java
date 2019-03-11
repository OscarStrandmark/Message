package client;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ArrayList<MediaMessage> messages;
	private User me;

	private static final String FILEPATH_CONTACTS = "C:\\dev\\ServerLogFile\\contacts.dat";
	private ArrayList<User> contacts;

	public Controller() {
		connectedUsers = new ArrayList<User>();
		connection = new Connection(SERVERADDRESS, PORT, this);
		ui = new UIHandler(this);
		messages = new ArrayList<MediaMessage>();
		readContactsFromFile();
	}

	private void readContactsFromFile() {
		File file = new File(FILEPATH_CONTACTS);
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(FILEPATH_CONTACTS)))) {

				while (true) {
					User u = (User) ois.readObject();
					contacts.add(u);
				}
			} catch (EOFException e) {
				// EOFException is always throwns after all objects are read.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else {
			file.mkdirs();
		}

	}

	/**
	 * Called when a user is added to the contacts;
	 * 
	 * @param user
	 */
	public void writeContacts(ArrayList<User> users) {

		contacts.clear();

		for (User u : users) {
			contacts.add(u);
		}

		File oldContacts = new File(FILEPATH_CONTACTS);
		oldContacts.delete();

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(FILEPATH_CONTACTS)));
			for (User u : users) {
				oos.writeObject(u);
			}
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String text, ImageIcon img, List<User> recipients) {
		Message msg = new MediaMessage(me, recipients, text, img);
		connection.sendMessage(msg);
	}

	public void login(String username, ImageIcon img) {
		me = new User(username, img);
		connection.connect(me);
	}

	public ArrayList<User> getConnectedUsers() {
		return connectedUsers;
	}

	public ArrayList<User> getContacts() {
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
