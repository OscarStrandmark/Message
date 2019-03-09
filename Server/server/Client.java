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
		messageBuffer = new Buffer<Message>();
		this.controller = controller;
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			User me = (User)ois.readObject();
			System.out.println("Client: Servern har läst in Userobjekt: "+me.toString()); //TEST

			
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
					System.out.print("Client: ClientSender: Meddelande hämtat från messageBuffer...");

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
			while (true) { 
				try {
				Message msg;
				System.out.println("Client: ClientReciever: Väntar på att läsa in objekt....");

				msg = (Message) ois.readObject();
				System.out.println("Client: ClientReciever: Meddelande: "+msg.toString()+" inläst från inputstream");

				controller.processMessage(msg);
			} catch ( Exception e) {
				e.printStackTrace();
				}
			}
		}
	}
}