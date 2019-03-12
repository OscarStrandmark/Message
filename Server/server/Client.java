package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Buffer;
import shared.LoginMessage;
import shared.Message;
import shared.User;

public class Client {

	private Controller controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Buffer<Message> messageBuffer;

	public Client(Controller controller, Socket socket) {
		this.messageBuffer = new Buffer<Message>();
		this.controller = controller;
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Str�mmar skapade");
			LoginMessage me = (LoginMessage) ois.readObject();
			System.out.println("Client: Servern har läst in Userobjekt: " + me.toString()); // TEST

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

	private class ClientSender extends Thread {
		public void run() {
			Message msg;
			while (true) {
				try {
					msg = messageBuffer.get();
					System.out.print("Client: ClientSender: Meddelande hämtat från messageBuffer...");

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
			while (true) {
				try {
					Message msg;
					System.out.println("Client: ClientReciever: Väntar på att läsa in objekt....");

					msg = (Message) ois.readObject();
					System.out.println(
							"Client: ClientReciever: Meddelande: " + msg.toString() + " inläst från inputstream");

					controller.processMessage(msg);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}
	}
}