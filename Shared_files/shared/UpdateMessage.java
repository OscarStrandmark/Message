package shared;

import java.util.ArrayList;

public class UpdateMessage extends Message {

	private static final long serialVersionUID = 7699186736282794974L;

	private ArrayList<User> connectedUsers;
	
	public UpdateMessage(User from, ArrayList<User> connectedUsers) {
		super(from);
		this.connectedUsers = connectedUsers;
	}
	
	public ArrayList<User> getList(){
		return connectedUsers;
	}

}
