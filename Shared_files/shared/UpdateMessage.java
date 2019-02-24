package shared;

import java.util.List;

public class UpdateMessage extends Message {

	private static final long serialVersionUID = 7699186736282794974L;

	private List<User> connectedUsers;
	
	public UpdateMessage(User from, List<User> connectedUsers) {
		super(from);
		this.connectedUsers = connectedUsers;
	}
	
	public List<User> getList(){
		return connectedUsers;
	}

}
