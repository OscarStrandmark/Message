package shared;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class User implements Serializable {

	private static final long serialVersionUID = -8292271837872554180L;

	private String username;
	
	private ImageIcon avatar;

	public User(String username, ImageIcon avatar) {
		this.username = username;
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}
}
