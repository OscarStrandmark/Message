package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MessageFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	private JButton btnAddReciever;
	private JButton btnSend;
	
	private MessageFrame thisWindow = this;
	private MainFrame mainframe;
	
	
	public MessageFrame(MainFrame mainframe) {
		this.mainframe = mainframe;
		init();
	}

	private void init() {
		setAlwaysOnTop(true);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 11, 803, 36);
		contentPane.add(textField);
		textField.setColumns(10);

		btnAddReciever = new JButton("Add reciever");
		btnAddReciever.setBounds(823, 11, 151, 36);
		btnAddReciever.addActionListener(new ButtonListener());
		contentPane.add(btnAddReciever);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 58, 964, 487);
		contentPane.add(textArea);

		btnSend = new JButton("Send");
		btnSend.setBounds(856, 593, 118, 57);
		btnSend.addActionListener(new ButtonListener());
		contentPane.add(btnSend);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 603, 836, 36);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton_1 = new JButton("Add image");
		btnNewButton_1.setBounds(10, 556, 118, 36);
		contentPane.add(btnNewButton_1);
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == btnAddReciever) {
				new MessageRecipientsFrame(thisWindow);
			}
			
			if(e.getSource() == btnSend) {
				mainframe.sendMessage();
				setVisible(false);
			}
		}
	}
}
