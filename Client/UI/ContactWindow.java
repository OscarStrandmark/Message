package UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import client.Controller;
import shared.User;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;

public class ContactWindow {

	private JFrame frame = new JFrame("Contacts");
	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollPane_1 = new JScrollPane();

	private JList<String> listContacts = new JList<String>();
	private JLabel lblKontakter = new JLabel("Contacts");

	private JLabel lblAnslutnaAnvndare = new JLabel("Connected Users");
	private JList<String> listConnected = new JList<String>();

	private HashMap<String, User> userMap;

	private JButton btnFromContacts = new JButton("----->");
	private JButton btnFromConnected = new JButton("<-----");
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

		scrollPane_1.setViewportView(listConnected);
		lblAnslutnaAnvndare.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAnslutnaAnvndare.setBounds(572, 47, 207, 20);
		frame.getContentPane().add(lblAnslutnaAnvndare);

		btnFromContacts.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFromContacts.setBounds(339, 197, 115, 29);
		btnFromContacts.addActionListener(listener);
		frame.getContentPane().add(btnFromContacts);

		btnFromConnected.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFromConnected.setBounds(339, 272, 115, 29);
		btnFromConnected.addActionListener(listener);
		frame.getContentPane().add(btnFromConnected);
		
	}

	private void initializeLists() {
		ArrayList<User> connected = controller.getConnectedUsers();
		ArrayList<User> contacts = controller.getContacts();
		DefaultListModel<String> modelConnected = new DefaultListModel<String>();
		DefaultListModel<String> modelContacts = new DefaultListModel<String>();

		userMap = new HashMap<String,User>();
		
		for(User u : contacts) {
			connected.remove(u);
			userMap.put(u.getUsername(), u);
			modelContacts.addElement(u.getUsername());
		}
		
		for(User u : connected) {
			userMap.put(u.getUsername(),u);
			modelConnected.addElement(u.getUsername());
		}

		listConnected.setModel(modelConnected);
		listConnected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		listContacts.setModel(modelContacts);
		listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
				
				controller.writeContacts(newContacts);
				frame.dispose();
			}

			if (e.getSource() == btnFromContacts) {
				
				int index = listContacts.getSelectedIndex();
				
				ListModel<String> oldModelConnected = listConnected.getModel();
				DefaultListModel<String> newModelConnected = new DefaultListModel<String>();
			
				ListModel<String> oldModelContacts = listContacts.getModel();
				DefaultListModel<String> newModelContacts = new DefaultListModel<String>();
				
				for (int i = 0; i < oldModelConnected.getSize(); i++) {
					newModelConnected.addElement(oldModelConnected.getElementAt(i));
				}
				newModelConnected.addElement(oldModelContacts.getElementAt(index));
				
				for (int i = 0; i < oldModelContacts.getSize(); i++) {
					if(i != index) {
						newModelContacts.addElement(oldModelContacts.getElementAt(i));
					}
				}
				
				listConnected.setModel(newModelConnected);
				listContacts.setModel(newModelContacts);
				
			}

			if (e.getSource() == btnFromConnected) {
				
				int index = listConnected.getSelectedIndex();
				
				ListModel<String> oldModelConnected = listConnected.getModel();
				DefaultListModel<String> newModelConnected = new DefaultListModel<String>();
			
				ListModel<String> oldModelContacts = listContacts.getModel();
				DefaultListModel<String> newModelContacts = new DefaultListModel<String>();
				
				
				for (int i = 0; i < oldModelContacts.getSize(); i++) {
					newModelContacts.addElement(oldModelContacts.getElementAt(i));
				}
				
				newModelContacts.addElement(oldModelConnected.getElementAt(index));
				
				for (int i = 0; i < oldModelConnected.getSize(); i++) {
					if(i != index) {
						newModelConnected.addElement(oldModelConnected.getElementAt(i));
					}
				}
				
				listConnected.setModel(newModelConnected);
				listContacts.setModel(newModelContacts);
				
			}
		}
	}
}
