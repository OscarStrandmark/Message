package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import client.Controller;
import shared.User;

public class ConnectedUsersWindow {

	private JFrame frame = new JFrame();
	private JButton btnCloseWindow = new JButton("Close Window");
	private JTextArea JTA;
	private Controller controller;

	public ConnectedUsersWindow(Controller controller) {
		this.controller = controller;
		initialize();
		initList();
	}

	private void initList() {
		List<User> connectedUsers = controller.getConnectedUsers();
		
		String content = "";
		for(User u : connectedUsers) {
			content += u.getUsername() + "\n";
		}
		JTA.setText(content);
		JTA.updateUI();
	}
	private void initialize() {

		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JTA = new JTextArea();
		JTA.setBounds(15, 29, 948, 507);
		frame.getContentPane().add(JTA);
		JTA.setEditable(false);

		Listener listener = new Listener();

		btnCloseWindow.setBounds(436, 581, 115, 29);
		frame.getContentPane().add(btnCloseWindow);
		btnCloseWindow.addActionListener(listener);
	}

	private class Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnCloseWindow) {
				frame.dispose();
			}
		}
	}
}
