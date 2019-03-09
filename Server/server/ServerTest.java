package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.LoginMessage;
import shared.MediaMessage;
import shared.Message;
import shared.UpdateMessage;
import shared.User;

public class ServerTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private User user2;
	private List<User> recieverList = new ArrayList<User>();
	private Message msg;
	private MediaMessage msgMedia;
	private LoginMessage login; 
	
	public ServerTest() {
		user = new User("Sverker", null);
		user2 = new User("Bertil",null);
		msg = new Message(user);      //Används ej, 
		recieverList.add(user2);
		login = new LoginMessage(user);
		msgMedia = new MediaMessage(user,recieverList,"Kevin luktar fis",null);

		new Connection().start();
	}
	
	private class Connection extends Thread{
		
		public void run() {
			try {
				Socket socket = new Socket("localhost",720);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				//----------------NY ANVÄNDARE
				System.out.println("ServerTest:   /nSkriver till server...");
				oos.writeObject(login);
				System.out.println("Skrivit: "+user.toString()+" till server");
				
				//----------------Meddelande Test
				
				oos.writeObject(msgMedia);
				System.out.print("Meddelandet har skickats till servern");
	
				System.out.println("RUNNING");

					while(true) {
						try {
							System.out.println("waiting for list");
							UpdateMessage msg = (UpdateMessage)ois.readObject();
							System.out.println("read list");
							System.out.println(msg.getList().toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}catch(IOException e) {
				e.printStackTrace();
			}
		}
	
		
	}
	
	public static void main(String[] args) {
		ServerTest test = new ServerTest();
	
		
	}
	
}
