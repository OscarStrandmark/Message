package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import shared.Buffer;
import shared.LoginMessage;
import shared.Message;

public class Client {

	private Controller controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Buffer<Message> messageBuffer;

	private Thread sender;
	private Thread reciever;
	
	private boolean alive = true;
	
	public Client(Controller controller, Socket socket) {
		this.messageBuffer = new Buffer<Message>();
		this.controller = controller;
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			LoginMessage me = (LoginMessage) ois.readObject();

			new ClientSender().start();
			new ClientReceiver().start();

			controller.addnewUser(me.getSender(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendTo(Message msg) {
		messageBuffer.put(msg);
	}

	public void kill() {
		try {
			System.out.println("kill");
			socket.close();
			alive = false;
		} catch (SocketException sE) {
			//SocketException will happen when socket is closed.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class ClientSender extends Thread {
		public void run() {
			Message msg;
			while (alive) {
				try {
					msg = messageBuffer.get();
					oos.writeObject(msg);
					oos.flush();
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}
	}

	private class ClientReceiver extends Thread {
		public void run() {
			while (alive) {
				try {
					Message msg;
					msg = (Message) ois.readObject();
					controller.processMessage(msg);
				} catch (SocketException se) {
					if(se.getMessage().contains("Socket closed")) {
						//Thrown with message "socket closed" when the client is shutting down. No need to print.
					} else {
						se.printStackTrace();
					}
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}
	}
}