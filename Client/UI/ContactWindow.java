package UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;

public class ContactWindow {

	private JFrame frame = new JFrame("Kontakter");
	private JScrollPane scrollPane = new JScrollPane();
	private JList listContacts = new JList();
	private JLabel lblKontakter = new JLabel("Kontakter");
	private JButton btnClose = new JButton("Close");
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JLabel lblAnslutnaAnvndare = new JLabel("Anslutna Användare");
	private final JList listUsers = new JList();
	private final JButton btnFromContacts = new JButton("----->");
	private final JButton btnFromUsers = new JButton("<-----");



	public static void main(String[] args) {
		ContactWindow ctw = new ContactWindow();
	}

	public ContactWindow() {
		initialize();
	}

	private void initialize() {
		frame.setVisible(true);
		frame.setBounds(100, 100, 830, 683);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnClose) {
				frame.dispose();
			}

			if (e.getSource() == btnFromContacts) {

			}
			if(e.getSource() == btnFromUsers) {

			}
		}
	}
}
