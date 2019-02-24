package shared;

public class LoginMessage extends Message {

	private static final long serialVersionUID = 5843132492035342L;

	public LoginMessage(User from) {
		super(from);
	}

}
