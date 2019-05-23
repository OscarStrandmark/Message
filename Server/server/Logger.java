package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import shared.MediaMessage;
import shared.Message;
import shared.User;

public class Logger {

	private static final String FILEPATH = "C:\\dev\\ServerLogFile\\";
		
	private ArrayList<String> thisSession;
	private LoggerUI ui;
	private static Logger instance = new Logger();

	private Logger() {
		thisSession = new ArrayList<String>();
	}

	// Singleton type class
	public static Logger getInstance() {
		return instance;
	}

	public void setUI(LoggerUI ui) {
		this.ui = ui;
	}
	
	//Call on shutdown. Write all data from current session to file.
	public void write() {
		File folder = new File(FILEPATH);
		if(folder.exists()) {
		} else {
			folder.mkdirs();
		}
		try {
			File file = new File("C:\\dev\\ServerLogFile\\log" + (new Date().getTime()) + ".txt");
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
			for(String s : thisSession) {
				bw.write(s);
				bw.newLine();
				bw.flush();
			}
			
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Method for logging a new incoming connection
	public void logConnect(String username) {
		try {
			String str = getDateFormatted() + " " + "User: " + username + " " + "connected to the server.";
			thisSession.add(str);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// Method for logging the loss of a connection
	public void logDisconnect(String username) {
		try {
			String str = getDateFormatted() + " " + "User: " + username + " " + "disconnected from the server.";
			thisSession.add(str);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// Method for logging a message sent through the server.
	public void logMessage(Message msg) {
		if (msg instanceof MediaMessage) {
			MediaMessage mmsg = (MediaMessage) msg;
			try {
				String str = getDateFormatted();
				str += " " + "Message handled: from user: " + mmsg.getSender().getUsername().toLowerCase() + "."
						+ " To: ";
				List<User> recipients = mmsg.getReceivers();
				Iterator<User> iter = recipients.iterator();

				while (iter.hasNext()) {
					str += iter.next().getUsername() + ", ";
				}
				str = str.substring(0, str.length() - 2);
				str += ".";
				thisSession.add(str);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	// Method called when the "update" button is pressed on the logger interface
	public void updateView(LocalDate Dfrom, LocalTime Tfrom, LocalDate Dto, LocalTime Tto) {
		String displayString = "Displaying traffic from: " + "\n";
		displayString += Dfrom.getYear() + "/" + Dfrom.getMonthValue() + "/" + Dfrom.getDayOfMonth() + " - "
				+ Tfrom.getHour() + ":" + Tfrom.getMinute() + "\n";
		displayString += "to: " + "\n";
		displayString += Dto.getYear() + "/" + Dto.getMonthValue() + "/" + Dto.getDayOfMonth() + " - " + Tto.getHour()
				+ ":" + Tto.getMinute() + "\n";
		displayString += "==============================================" + "\n";

		try {
			for (String line : thisSession) {

				int year = Integer.parseInt(line.substring(1, 5));
				int month = Integer.parseInt(line.substring(6, 8));
				int date = Integer.parseInt(line.substring(9, 11));
				int hour = Integer.parseInt(line.substring(12, 14));
				int min = Integer.parseInt(line.substring(15, 17));

				// Monstrosity. Handling dates is terrible. If I had time, would convert to UNIX time. :( /Oscar
				if (year >= Dfrom.getYear() && year <= Dto.getYear()) {
					if (month >= Dfrom.getMonthValue() && month <= Dto.getMonthValue()) {
						if (date >= Dfrom.getDayOfMonth() && date <= Dto.getDayOfMonth()) {
							int time = hour * 60 + min;
							int TimeFrom = Tfrom.getHour() * 60 + Tfrom.getMinute();
							int TimeTo = Tto.getHour() * 60 + Tto.getMinute();
							if (time >= TimeFrom && time <= TimeTo) {
								displayString += line + "\n";
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

	// Get the current time in the specified format.
	private String getDateFormatted() {
		// Format: [YYYY/MM/DD/HH:MM]
		Calendar cal = Calendar.getInstance();
		String fillMonth = "";
		String fillDate = "";
		String fillHour = "";
		String fillMinute = "";

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);

		if (month < 10) {
			fillMonth = "0";
		}

		if (date < 10) {
			fillDate = "0";
		}

		if (hour < 10) {
			fillHour = "0";
		}

		if (min < 10) {
			fillMinute = "0";
		}
		String dateString = "[" + year + "/" + fillMonth + month + "/" + fillDate + date + "/" + fillHour + hour + ":"
				+ fillMinute + min + "]";
		return dateString;
	}
}