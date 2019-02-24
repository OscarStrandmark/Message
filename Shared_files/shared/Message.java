package shared;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = -2815116531203592121L;

	private User from;
	
	public Message(User from) {
		this.from = from;
	}
}
