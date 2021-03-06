package shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

public class MediaMessage extends Message {
	
	private static final long serialVersionUID = 6318594750448202389L;
	
	private List<User> to;
	private String text;
	private ImageIcon image;
	
	private Date received;
	private Date delivered;
	private Date sent;

	public MediaMessage(User from, List<User> to, String text, ImageIcon image) {
		super(from);
		this.to = to;
		this.text = text;
		this.image = image;
		this.sent = new Date();
	}

	public List<User> getReceivers() {
		return to;
	}
	
	public User getSender() {
		return super.getSender();
	}

	public String getText() {
		return text;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setReceived() {
		received = new Date();
	}
	
	public void setDelivered() {
		delivered = new Date();
	}

	public void setReceivers(ArrayList<User> uList) {
		to = uList;
	}
	
	public Date getReceived() {
		return received;
	}

	public Date getDelivered() {
		return delivered;
	}
	
	public Date getSent() {
		return sent;
	}
}
