package UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ConnectedUsersWindow {

	private String[] data = { "hej", "hallå", "vafan" };
	// JList<String> myList = new JList<String>(data);
	private JFrame frame = new JFrame();
	private JList list = new JList();
	private JButton btnCloseWindow = new JButton("Close Window");

	private UIHandler ui;

	public ConnectedUsersWindow(UIHandler ui) {
		this.ui = ui;
		initialize();
	}

	private void initialize() {

		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 29, 948, 507);
		frame.getContentPane().add(scrollPane);

		Listener listener = new Listener();

		scrollPane.setViewportView(list);
		
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
