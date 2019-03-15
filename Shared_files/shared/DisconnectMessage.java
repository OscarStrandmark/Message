package shared;

public class DisconnectMessage extends Message {

	private static final long serialVersionUID = -500021589811263835L;

	public DisconnectMessage(User from) {
		super(from);
	}

}
