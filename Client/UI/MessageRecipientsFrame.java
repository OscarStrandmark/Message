package UI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import shared.User;

public class MessageRecipientsFrame extends JFrame {

	private JPanel contentPane;

	private JList<User> listConnectedUsers;
	private JList<User> listContacts;
	private JList<User> listRecipients;

	private MessageFrame parent;

	public MessageRecipientsFrame(MessageFrame parent) {
		this.parent = parent;
		init();
		
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

		JButton btnNewButton = new JButton("Done");
		btnNewButton.setBounds(845, 587, 119, 39);
		panel.add(btnNewButton);
	}

}
