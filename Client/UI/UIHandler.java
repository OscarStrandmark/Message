package UI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.Controller;

public class UIHandler {

	private Controller controller;
	
	public UIHandler(Controller controller) {
		this.controller = controller;
		
		JFrame login = new LoginFrame(this);
		login.setVisible(true);
	}
	
	public void logIn(String username, ImageIcon avatar) {
		controller.login(username, avatar);
	}
}
