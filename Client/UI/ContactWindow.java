package UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import client.Controller;
import shared.User;

import javax.swing.JButton;
import java.awt.Font;

public class ContactWindow {

	private JFrame frame = new JFrame("Contacts");
	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollPane_1 = new JScrollPane();

	private JList<String> listContacts = new JList<String>();
	private JLabel lblKontakter = new JLabel("Contacts");

	private JLabel lblAnslutnaAnvndare = new JLabel("Connected Users");
	private JList<String> listUsers = new JList<String>();

	private HashMap<String, User> userMap;

	private JButton btnFromContacts = new JButton("----->");
	private JButton btnFromUsers = new JButton("<-----");
	private JButton btnClose = new JButton("Save and Close");

	private Controller controller;

	private ArrayList<String> contacts;

	public ContactWindow(Controller controller) {
		this.controller = controller;
		initialize();
		initializeLists();
	}

	private void initialize() {
		frame.setVisible(true);
		frame.setBounds(100, 100, 830, 683);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Listener listener = new Listener();

		scrollPane.setBounds(15, 83, 268, 389);
		frame.getContentPane().add(scrollPane);

		scrollPane.setViewportView(listContacts);
		lblKontakter.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblKontakter.setBounds(101, 47, 115, 20);
		frame.getContentPane().add(lblKontakter);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 21));

		btnClose.setBounds(300, 529, 205, 74);
		frame.getContentPane().add(btnClose);
		scrollPane_1.setBounds(511, 83, 268, 389);
		btnClose.addActionListener(listener);
		frame.getContentPane().add(scrollPane_1);

		scrollPane_1.setViewportView(listUsers);
		lblAnslutnaAnvndare.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAnslutnaAnvndare.setBounds(572, 47, 207, 20);
		frame.getContentPane().add(lblAnslutnaAnvndare);

		btnFromContacts.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFromContacts.setBounds(339, 197, 115, 29);
		frame.getContentPane().add(btnFromContacts);

		btnFromUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFromUsers.setBounds(339, 272, 115, 29);
		frame.getContentPane().add(btnFromUsers);
	}

	private void initializeLists() {
		ArrayList<User> contacts = controller.getContacts();
		ArrayList<User> connected = controller.getConnectedUsers();

		userMap = new HashMap<String, User>();

		for (User u : contacts) {
			if (connected.contains(u)) {
				connected.remove(u);
			}
		}
		ArrayList<String> contactString = new ArrayList<String>();
		ArrayList<String> connectedString = new ArrayList<String>();

		for (User u : contacts) {
			userMap.put(u.getUsername(), u);
			contactString.add(u.getUsername());
		}

		for (User u : connected) {
			userMap.put(u.getUsername(), u);
			connectedString.add(u.getUsername());
		}

		listUsers = new JList<String>((String[]) contactString.toArray());
		listContacts = new JList<String>((String[]) contactString.toArray());
		listUsers.updateUI();
		listContacts.updateUI();
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnClose) {
				ListModel<String> model = listContacts.getModel();
				ArrayList<User> newContacts = new ArrayList<User>();
				for (int i = 0; i < model.getSize(); i++) {
					String username = model.getElementAt(i);
					newContacts.add(userMap.get(username));
				}
				controller.setContacts(newContacts);
				frame.dispose();
			}

			if (e.getSource() == btnFromContacts) {
				int selectedIndex = listContacts.getSelectedIndex();
				String selectedUser = listContacts.getModel().getElementAt(selectedIndex);

				ArrayList<User> contacts = controller.getContacts();
				ArrayList<User> connected = controller.getConnectedUsers();

				userMap = new HashMap<String, User>();

				for (User u : contacts) {
					if (connected.contains(u)) {
						connected.remove(u);
					}
				}
				ArrayList<String> contactString = new ArrayList<String>();
				ArrayList<String> connectedString = new ArrayList<String>();

				for (User u : contacts) {
					userMap.put(u.getUsername(), u);
					contactString.add(u.getUsername());
				}

				for (User u : connected) {
					userMap.put(u.getUsername(), u);
					connectedString.add(u.getUsername());
				}

				contactString.remove(selectedUser);
				connectedString.add(selectedUser);

				listUsers = new JList<String>((String[]) contactString.toArray());
				listContacts = new JList<String>((String[]) contactString.toArray());
				listUsers.updateUI();
				listContacts.updateUI();
			}

			if (e.getSource() == btnFromUsers) {
				int selectedIndex = listUsers.getSelectedIndex();
				String selectedUser = listUsers.getModel().getElementAt(selectedIndex);

				ArrayList<User> contacts = controller.getContacts();
				ArrayList<User> connected = controller.getConnectedUsers();

				userMap = new HashMap<String, User>();

				for (User u : contacts) {
					if (connected.contains(u)) {
						connected.remove(u);
					}
				}
				ArrayList<String> contactString = new ArrayList<String>();
				ArrayList<String> connectedString = new ArrayList<String>();

				for (User u : contacts) {
					userMap.put(u.getUsername(), u);
					contactString.add(u.getUsername());
				}

				for (User u : connected) {
					userMap.put(u.getUsername(), u);
					connectedString.add(u.getUsername());
				}

				contactString.add(selectedUser);
				connectedString.remove(selectedUser);

				listUsers = new JList<String>((String[]) contactString.toArray());
				listContacts = new JList<String>((String[]) contactString.toArray());
				listUsers.updateUI();
				listContacts.updateUI();
			}
		}
	}
}
