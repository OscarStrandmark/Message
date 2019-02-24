package server;

import java.util.concurrent.ConcurrentHashMap;

import shared.User;

public class Connection {

	private ConcurrentHashMap<User, Client> connections;
	
	
	private class ClientAccepter extends Thread {
		
	}
	
	private class MessageHandler extends Thread {
		
	}
}
