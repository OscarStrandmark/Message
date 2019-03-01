package server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DateTimePicker;

public class LoggerUI extends JFrame {

	private JPanel contentPane;
	
	private JTextArea textArea;
	
	private JButton btnUpdate;
	
	private DateTimePicker dtpStart;
	private DateTimePicker dtpEnd;
	
	private Logger logger;
	
	public LoggerUI(Logger logger) {
		this.logger = logger;
		init();
	}
	
	private void init() {
		setTitle("View logs");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 613, 539);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		Font f = new Font("Arial Bold",Font.BOLD,32);
		
		JLabel lblStart = new JLabel("From");
		lblStart.setFont(f);
		lblStart.setBounds(750, 50, 100, 32);
		contentPane.add(lblStart);
		
		dtpStart = new DateTimePicker();
		dtpStart.setBounds(640, 100, 275, 32);
		contentPane.add(dtpStart);
		
		JLabel lblEnd = new JLabel("To");
		lblEnd.setFont(f);
		lblEnd.setBounds(750, 250, 100, 32);
		contentPane.add(lblEnd);
		
		dtpEnd = new DateTimePicker();
		dtpEnd.setBounds(640, 300, 275, 32);
		contentPane.add(dtpEnd);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ButtonListener());
		btnUpdate.setBounds(725, 450, 102, 23);
		contentPane.add(btnUpdate);
	}
	
	public void updateTextArea(String str) {
		textArea.setText(str);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnUpdate) {
				LocalDate LDFrom = dtpStart.datePicker.getDate();
				LocalTime LTFrom = dtpStart.timePicker.getTime();
				
				LocalDate LDTo = dtpEnd.datePicker.getDate();
				LocalTime LTTo = dtpEnd.timePicker.getTime();
				
				if(LDFrom == null || LTFrom == null || LDTo == null || LTTo == null) {
					JOptionPane.showMessageDialog(null, "Invalid date", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					logger.updateView(LDFrom,LTFrom,LDTo,LTTo);
				}
			}
		}	
	}
}
