package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import shared.Buffer;
import shared.LoginMessage;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

//Class that represents a users client on the server.
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

	//Send a message to this client
	public void sendTo(Message msg) {
		messageBuffer.put(msg);
	}

	//Method for killing the Client
	public void kill() {
		try {
			socket.close();
			alive = false;
		} catch (SocketException sE) {
			//SocketException will happen when socket is closed.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Class that handles sending Message-type objects to the userclient.
	private class ClientSender extends Thread {
		public void run() {
			Message msg;
			while (alive) {
				try {
					msg = messageBuffer.get();

					if(msg instanceof UpdateMessage) {
						UpdateMessage uMsg = (UpdateMessage)msg;
						ArrayList<User> ul = uMsg.getList();

						for(User u : ul) {
							System.out.println(u.getUsername());
						}
					}
					oos.writeObject(msg);
					oos.flush();
					oos.reset();
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}
	}

	//Class that handles recieving all Message-type objects from the userclient to the server.
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
					} else if(se.getMessage().contains("Connection reset")) {
						//No need to print.
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
