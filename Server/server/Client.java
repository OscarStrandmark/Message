package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Buffer;
import shared.Message;

public class Client {
	
	private Controller controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Buffer<Message> messageBuffer;
	
	public Client() {

	}
	
	
	private class ClientSender extends Thread {
		
	}
	
	private class ClientReciever extends Thread {
		
	}

}
