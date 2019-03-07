package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.MediaMessage;
import shared.Message;
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
	 
	
	public ServerTest() {
		user = new User("Sverker", null);
		user2 = new User("Bertil",null);
		msg = new Message(user);      //Används ej, 
		recieverList.add(user2);
		msgMedia = new MediaMessage(user,recieverList,"Kevin luktar fis",null);

		new Connection().start();
	}
	
	private class Connection extends Thread{
		
		public void run() {
			try( Socket socket = new Socket("localHost",720);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				ObjectOutputStream oos = new ObjectOutputStream(dos))
				{
				
				//----------------NY ANVÄNDARE
				System.out.println("Skriver till server...");
				oos.writeObject(user);
				System.out.println("Skrivit: "+user.toString()+" till server");
				
				//----------------Meddelande Test
				
				oos.writeObject(msgMedia);
				System.out.print("Meddelandet har skickats till servern");
				
				}catch(IOException e) {
				e.printStackTrace();
			}
		}
	
		
	}
	
	public static void main(String[] args) {
		ServerTest test = new ServerTest();
	
		
	}
	
}