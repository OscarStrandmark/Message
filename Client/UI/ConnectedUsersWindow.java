package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ConnectedUsersWindow {
	
	private String[] data = {"hej", "hallå", "vafan"};
	//JList<String> myList = new JList<String>(data);

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectedUsersWindow window = new ConnectedUsersWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectedUsersWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 29, 948, 507);
		frame.getContentPane().add(scrollPane);
		
		JList list = new JList();
		
		scrollPane.setViewportView(list);
		
		JButton btnCloseWindow = new JButton("Close Window");
		btnCloseWindow.setBounds(436, 581, 115, 29);
		frame.getContentPane().add(btnCloseWindow);
	}
}
