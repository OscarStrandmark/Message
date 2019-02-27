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

	private String[] data = {"hej", "hallå", "vafan"};
	//JList<String> myList = new JList<String>(data);
	private JFrame frame = new JFrame();
	private JList list = new JList();
	private JButton btnCloseWindow = new JButton("Close Window");




	private MainFrame mainframe;



	public ConnectedUsersWindow(MainFrame mainframe) {
		this.mainframe = mainframe;
		initialize();
	}

	private void initialize() {
		//frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 29, 948, 507);
		frame.getContentPane().add(scrollPane);

		//list = new JList();

		scrollPane.setViewportView(list);

		//JButton btnCloseWindow = new JButton("Close Window");
		btnCloseWindow.setBounds(436, 581, 115, 29);
		frame.getContentPane().add(btnCloseWindow);
		Listener listener = new Listener();
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
