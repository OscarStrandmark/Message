package client;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
	public User me;

	private static final String FILEPATH_CONTACTS = "C:\\dev\\ServerLogFile\\contacts.dat";
	private static final String FILEPATH_CONTACTS_FOLDER = "C:\\dev\\ServerLogFile";

	private ArrayList<User> contacts;

	public Controller() {
		connectedUsers = new ArrayList<User>();
		connection = new Connection(SERVERADDRESS, PORT, this);
		contacts = new ArrayList<User>();
		ui = new UIHandler(this);
		messages = new ArrayList<MediaMessage>();
		readContactsFromFile();
	}

	private void readContactsFromFile() {
		File folders = new File(FILEPATH_CONTACTS_FOLDER);
		if (!folders.exists()) {
			folders.mkdirs();
		}

		File contact = new File(FILEPATH_CONTACTS);

		if (contact.isFile()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(contact));
				while (true) {
					User u = (User) ois.readObject();
					contacts.add(u);
				}
			} catch (EOFException EOFE) {
				// Exception is thrown and caught here when all user objects are read.
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("contacts file did not exist");
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
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(FILEPATH_CONTACTS)));
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

	public void disconnect() {
		connection.disconnect(me);
	}
	public ArrayList<User> getConnectedUsers() {
		return connectedUsers;
	}

	public ArrayList<User> getContacts() {
		return contacts;
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
