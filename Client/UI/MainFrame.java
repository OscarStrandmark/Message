package UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import shared.User;

public class MainFrame extends JFrame {

	private UIHandler ui;
	
	private JPanel contentPane;

	private JButton btnContacts;
	private JButton btnNewMessage;
	private JButton btnConnectedUsers;

	private MainFrame thisWindow = this;
	
	private JList messagesRecieved;
	public MainFrame(UIHandler ui) {
		this.ui = ui;
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// NORTH
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 3, 0, 0));

		ButtonListener listener = new ButtonListener();

		btnContacts = new JButton("Kontakter");
		btnContacts.addActionListener(listener);
		panel.add(btnContacts);

		btnNewMessage = new JButton("Nytt meddelande");
		btnNewMessage.addActionListener(listener);
		panel.add(btnNewMessage);

		btnConnectedUsers = new JButton("Anslutna användare");
		btnConnectedUsers.addActionListener(listener);
		panel.add(btnConnectedUsers);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);

		JList<User> list = new JList<User>();
		panel_1.add(list);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);

		// WEST
		
		JScrollPane messagesRecievedPane = new JScrollPane();
		messagesRecieved = new JList();
		messagesRecievedPane.add(messagesRecieved);
		contentPane.add(messagesRecievedPane);
	}

	public void sendMessage() {
		
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnContacts) {
				new ContactFrame(thisWindow);
			}

			if (e.getSource() == btnNewMessage) {
				new MessageFrame(thisWindow);
			}

			if (e.getSource() == btnConnectedUsers) {
				new ConnectedUsersWindow(thisWindow);
			}
		}
	}
}
