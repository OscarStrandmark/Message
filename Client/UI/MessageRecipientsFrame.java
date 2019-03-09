package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import client.Controller;
import shared.User;

public class MessageRecipientsFrame extends JFrame {

	private JPanel contentPane;

	private JList<User> listConnectedUsers;
	private JList<User> listContacts;
	private JList<User> listRecipients;

	private Controller controller;
	private MessageFrame msgWindow;
	private JFrame thisWindow = this;
	
	private JButton btnDone;
	private JButton btnAdd;
	private JButton btnRemove;
	
	private List<User> recipients;
	
	public MessageRecipientsFrame(Controller controller, MessageFrame msgWindow) {
		this.controller = controller;
		this.msgWindow = msgWindow;
		init();
		//TODO
		//initLists();
		
	}

	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Drag users who should recieve the message to the list to the right");
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 237, 615);
		panel.add(scrollPane);

		listConnectedUsers = new JList<User>();
		scrollPane.setViewportView(listConnectedUsers);

		JLabel lblConnected = new JLabel("Connected Users");
		lblConnected.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblConnected);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(270, 11, 237, 615);
		panel.add(scrollPane_1);

		listContacts = new JList<User>();
		scrollPane_1.setViewportView(listContacts);

		JLabel lblContacts = new JLabel("Contacts");
		lblContacts.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblContacts);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(600, 11, 237, 615);
		panel.add(scrollPane_2);

		listRecipients = new JList<User>();
		scrollPane_2.setViewportView(listRecipients);

		JLabel lblRecipients = new JLabel("Recipients");
		lblRecipients.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_2.setColumnHeaderView(lblRecipients);

		btnDone = new JButton("Done");
		btnDone.setBounds(845, 587, 119, 39);
		btnDone.addActionListener(new ButtonListener());
		panel.add(btnDone);
		
		btnAdd = new JButton(">");
		btnAdd.setBounds(525, 300, 48, 48);
		btnAdd.addActionListener(new ButtonListener());
		panel.add(btnAdd);
		
		btnRemove = new JButton("<");
		btnRemove.setBounds(525, 350, 48, 48);
		btnRemove.addActionListener(new ButtonListener());
		panel.add(btnRemove);
	}

	private void initLists() {
		List<User> connected = controller.getConnectedUsers();
		List<User> contacts  = controller.getContacts();
		
		DefaultListModel<User> modelConnected = new DefaultListModel<User>();
		DefaultListModel<User> modelContacts  = new DefaultListModel<User>();
		
		Iterator<User> connectedIter = connected.iterator();
		while(connectedIter.hasNext()) {
			modelConnected.addElement(connectedIter.next());
		}
		
		Iterator<User> contactIter = contacts.iterator();
		while(contactIter.hasNext()) {
			modelContacts.addElement(contactIter.next());
		}
		
		listConnectedUsers.setModel(modelConnected);
		listContacts.setModel(modelContacts);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnDone) {
				msgWindow.setRecipients(recipients);
				thisWindow.dispose();
			}
		}
	}
}
