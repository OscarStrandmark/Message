package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

import client.Controller;
import shared.MediaMessage;
import shared.Message;
import shared.User;

public class MainFrame extends JFrame {

	private Controller controller;
	
	private JPanel contentPane;
	private JTextPane jtp;
	
	private JButton btnContacts;
	private JButton btnNewMessage;
	private JButton btnConnectedUsers;

	private JLabel lblName;
	private JLabel lblTimestamp;
	
	private MainFrame thisWindow = this;
	
	private JList<String> messagesRecievedList;
	private ArrayList<String> messageStrings;
	private HashMap<String,MediaMessage> messageMap;
	
	public MainFrame(Controller controller) {
		this.messageMap = new HashMap<String,MediaMessage>();
		this.messageStrings = new ArrayList<String>();
		this.controller = controller;
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
		addWindowListener(new wListener());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// NORTH
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 3, 0, 0));

		ButtonListener listener = new ButtonListener();

		btnContacts = new JButton("Contacts");
		btnContacts.addActionListener(listener);
		panel.add(btnContacts);

		btnNewMessage = new JButton("New Message");
		btnNewMessage.addActionListener(listener);
		panel.add(btnNewMessage);

		btnConnectedUsers = new JButton("Connected Users");
		btnConnectedUsers.addActionListener(listener);
		panel.add(btnConnectedUsers);
		
		// WEST
		JScrollPane messagesRecievedPane = new JScrollPane();
		messagesRecievedPane.setPreferredSize(new Dimension(200,650));
		messagesRecievedList = new JList<String>();
		messagesRecievedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		messagesRecievedPane.setViewportView(messagesRecievedList);
		messagesRecievedList.addListSelectionListener(new ListListener());
		contentPane.add(messagesRecievedPane,BorderLayout.WEST);
		
		//CENTER
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		centerPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblFrom = new JLabel("Message from user: ");
		northPanel.add(lblFrom);
		
		lblName = new JLabel("Name");
		northPanel.add(lblName);
		
		lblTimestamp = new JLabel("Timestamp");
		northPanel.add(lblTimestamp);
		centerPanel.add(northPanel,BorderLayout.NORTH);
		
		jtp = new JTextPane();
		jtp.setFont(new Font("Arial bold",Font.PLAIN,24));
		jtp.setEditable(false);
		centerPanel.add(jtp,BorderLayout.CENTER);
		
		contentPane.add(centerPanel,BorderLayout.CENTER);
	}
	
	public void updateMessageList(ArrayList<MediaMessage> messages) {
		messageMap.clear();
		messageStrings.clear();
		
		for(MediaMessage m : messages) {
			String s = m.getSender().getUsername() + " - " + m.getSent().getHours() + ":" + m.getSent().getMinutes() + ":" + m.getSent().getSeconds();
			messageMap.put(s, m);
			messageStrings.add(s);
		}
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(String s : messageStrings) {
			model.addElement(s);
		}
		
		messagesRecievedList.setModel(model);
	}
	
	private class ListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			jtp.setText(null);
			
			int index = messagesRecievedList.getSelectedIndex();
			
			ListModel<String> model = messagesRecievedList.getModel();
			
			MediaMessage msg = messageMap.get(model.getElementAt(index));
			
			lblName.setText(msg.getSender().getUsername());
			String timestamp = msg.getSent().toString();
			lblTimestamp.setText(timestamp);
			
			ImageIcon img = msg.getImage();

			if(img != null) {
				jtp.insertIcon(img);

			}
			
			try {
				jtp.getDocument().insertString(0, msg.getText() + "\n", null);
			} catch (BadLocationException BLE) {
				BLE.printStackTrace();
			}
			
		}
	}
	private class wListener implements WindowListener {

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowClosing(WindowEvent arg0) {
			controller.disconnect();
		}

		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}
		
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnContacts) {
				new ContactWindow(controller);
			}

			if (e.getSource() == btnNewMessage) {
				new MessageFrame(controller);
			}

			if (e.getSource() == btnConnectedUsers) {
				new ConnectedUsersWindow(controller);
			}
		}
	}
}
