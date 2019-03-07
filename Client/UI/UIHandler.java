package UI;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import client.Controller;
import shared.User;

public class UIHandler {

	private Controller controller;
	private List<User> userList;
	
	public UIHandler(Controller controller) {
		this.controller = controller;
		this.userList = controller.getConnectedUsers();
		
		JFrame login = new LoginFrame(this);
		login.setVisible(true);
	}
	
	public void logIn(String username, ImageIcon avatar) {
		controller.login(username, avatar);
		
	}
	
	public void showMainWindow() {
		JFrame mainWindow = new MainFrame(controller);
	}

	public List<User> getConnected() {
		return controller.getConnectedUsers();
	}

	public List<User> getContacts() {
		return controller.getContacts();
	}
}
