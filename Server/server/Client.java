package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Buffer;
import shared.Message;
import shared.User;

public class Client {
	
	private Controller controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Buffer<Message> messageBuffer;
	
	public Client(Controller controller,Socket socket) {
		this.controller = controller;
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			User me = (User)ois.readObject();
			
			controller.addnewUser(me,this);
			
			new ClientSender().start();
			new ClientReceiver().start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void sendTo(Message msg) {
		messageBuffer.put(msg);
	}
	
	private class ClientSender extends Thread {
		public void run() {
			Message msg;
			while(true) {
				try {
					msg = messageBuffer.get();
					oos.writeObject(msg);
					oos.flush();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class ClientReceiver extends Thread {
		public void run() {
			while (true) try {
				Message msg;
				msg = (Message) ois.readObject();
				controller.processMessage(msg);
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
	}
}
