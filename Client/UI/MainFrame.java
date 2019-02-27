package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import shared.Message;
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
		Message[] msg = {new Message(new User("txt",new ImageIcon()))};
		messagesRecieved = new JList<Message>(msg);
		messagesRecievedPane.add(messagesRecieved);
		contentPane.add(messagesRecievedPane,BorderLayout.WEST);
		
		//CENTER
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		centerPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblImage = new JLabel("Image");
		northPanel.add(lblImage);
		
		JLabel lblName = new JLabel("Name");
		northPanel.add(lblName);
		
		JLabel lblTimestamp = new JLabel("Timestamp");
		northPanel.add(lblTimestamp);
		centerPanel.add(northPanel,BorderLayout.NORTH);
		
		JTextPane jtp = new JTextPane();
		centerPanel.add(jtp,BorderLayout.CENTER);
	}

	public void sendMessage() {
		
	}
	
	public void addRecipients(List<User> recipients) {
		
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnContacts) {
				new ContactWindow(ui);
			}

			if (e.getSource() == btnNewMessage) {
				new MessageFrame(ui);
			}

			if (e.getSource() == btnConnectedUsers) {
				new ConnectedUsersWindow(ui);
			}
		}
	}
}
