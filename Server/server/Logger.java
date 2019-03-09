package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import shared.MediaMessage;
import shared.Message;
import shared.User;

public class Logger {

	private static final String FILENAME = "C:\\dev\\ServerLogFile\\log.txt";
	private BufferedWriter bw;
	private BufferedReader br;
	private LoggerUI ui;
	private Logger instance = new Logger();
	
	private Logger() {
		ui = new LoggerUI(this);
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILENAME)));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAME)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Logger getInstance() {
		return instance;
	}
	public void logConnect(String username) {
		try {
			String str = getDateFormatted() + " " + "User: " + username + " " + "connected to the server.";
			bw.write(str);
			bw.flush();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void logDisconnect(String username) {
		try {
			String str = getDateFormatted() + " " + "User: " + username + " " + "disconnected from the server.";
			bw.write(str);
			bw.flush();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void messageRecieved(Message msg) {
		if(msg instanceof MediaMessage) {
			MediaMessage mmsg = (MediaMessage)msg;
			try {
				String str = getDateFormatted() + " " + "Message was recieved from user: " + mmsg.getSender().getUsername().toLowerCase() + ", " + "to: ";
				List<User> recipients = mmsg.getReceivers();
				Iterator<User> iter = recipients.iterator();
				while(iter.hasNext()) {
					str = iter.next().getUsername() + ", ";
				}
				str = str.substring(0, str.length()-2);
				str += ".";
				bw.write(str);
				bw.flush();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
	
	public void messageSent(Message msg) {
		if(msg instanceof MediaMessage) {
			MediaMessage mmsg = (MediaMessage)msg;
			try {
				String str = getDateFormatted() + " " + "Message was sent from server to user: ";
				List<User> recipients = mmsg.getReceivers();
				Iterator<User> iter = recipients.iterator();
				while(iter.hasNext()) {
					str = iter.next().getUsername() + ", ";
				}
				str = str.substring(0, str.length()-2);
				str += ".";
				bw.write(str);
				bw.flush();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
	
	public void updateView(LocalDate Dfrom, LocalTime Tfrom, LocalDate Dto, LocalTime Tto) {
		String displayString = "Displaying traffic from: " + "\n";
		displayString += Dfrom.getYear() + "/" + Dfrom.getMonthValue() + "/" + Dfrom.getDayOfMonth() + " - " + Tfrom.getHour() + ":" + Tfrom.getMinute() + "\n";
		displayString += "to: " + "\n";
		displayString += Dto.getYear() + "/" + Dto.getMonthValue() + "/" + Dto.getDayOfMonth() + " - " + Tto.getHour() + ":" + Tto.getMinute() + "\n";
		
		try {
			while(br.ready()) {
				String line = br.readLine();
				int year  = Integer.parseInt(line.substring(1, 5));
				int month = Integer.parseInt(line.substring(6, 8));
				int date  = Integer.parseInt(line.substring(9, 11));
				int hour  = Integer.parseInt(line.substring(12, 14));
				int min   = Integer.parseInt(line.substring(15, 17));
				
				//Fulaste radena kod jag skrivit hela mitt liv, fick aldrig .isAfter() att fungera. 
				if(year >= Dfrom.getYear() && year <= Dto.getYear()) {
					if(month >= Dfrom.getMonthValue() && month <= Dto.getMonthValue()) {
						if(date >= Dfrom.getDayOfMonth() && date <= Dto.getDayOfMonth()) {
							if(hour >= Tfrom.getHour() && hour <= Tto.getHour()) {
								if(min >= Tfrom.getMinute() && min <= Tto.getMinute()) {
									displayString += line + "\n";
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ui.updateTextArea(displayString);
	}
	
	private String getDateFormatted() {
		//Format: [YYYY/MM/DD/HH:MM]
		Calendar cal = Calendar.getInstance();
		int year  = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date  = cal.get(Calendar.DATE);
		int hour  = cal.get(Calendar.HOUR_OF_DAY);
		int min   = cal.get(Calendar.MINUTE);
		String dateString = "[" + year + "/" + month + "/" + date + "/" + hour + ":" + min + "]";
		return dateString;
	}
}
