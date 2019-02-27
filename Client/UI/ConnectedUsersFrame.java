package UI;

import javax.swing.JFrame;

public class ConnectedUsersFrame extends JFrame {

	private MainFrame mainframe;
	
	public ConnectedUsersFrame(MainFrame mainframe) {
		this.mainframe = mainframe;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
	}
}
