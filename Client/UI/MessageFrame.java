package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import shared.User;

public class MessageFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textRecipients;
	private JTextField textImgPath;
	
	private JTextArea textArea;
	
	private JButton btnAddReciever;
	private JButton btnAddImage;
	private JButton btnSend;
	
	private MessageFrame thisWindow = this;
	private UIHandler ui;
	
	private List<User> recipients;
	private String MessageText;
	private ImageIcon MessageImage;
	
	
	
	public MessageFrame(UIHandler ui) {
		this.ui = ui;
		init();
	}

	private void init() {
		requestFocus();
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textRecipients = new JTextField();
		textRecipients.setEditable(false);
		textRecipients.setBounds(10, 11, 803, 36);
		contentPane.add(textRecipients);
		textRecipients.setColumns(10);

		btnAddReciever = new JButton("Add reciever");
		btnAddReciever.setBounds(823, 11, 151, 36);
		btnAddReciever.addActionListener(new ButtonListener());
		contentPane.add(btnAddReciever);

		textArea = new JTextArea();
		textArea.setBounds(10, 58, 964, 487);
		contentPane.add(textArea);

		btnSend = new JButton("Send");
		btnSend.setBounds(856, 593, 118, 57);
		btnSend.addActionListener(new ButtonListener());
		contentPane.add(btnSend);

		textImgPath = new JTextField();
		textImgPath.setBounds(10, 603, 836, 36);
		contentPane.add(textImgPath);
		textImgPath.setColumns(10);

		btnAddImage = new JButton("Add image");
		btnAddImage.setBounds(10, 556, 118, 36);
		btnAddImage.addActionListener(new ButtonListener());
		contentPane.add(btnAddImage);
	}
	
	public void setRecipients(List<User> recipients) {
		this.recipients = recipients;
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == btnAddReciever) {
				new MessageRecipientsFrame(ui,thisWindow);
			}
			
			if(e.getSource() == btnSend) {
				setVisible(false);
			}
			
			if(e.getSource() == btnAddImage) {
				JFileChooser JFC = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
				JFC.setFileFilter(filter);
				int optionpicked = JFC.showOpenDialog(null);
				if (optionpicked == JFileChooser.APPROVE_OPTION) {
					File file = JFC.getSelectedFile();
					MessageImage = new ImageIcon(file.getPath());
					textImgPath.setText(file.getPath());
				}
			}
		}
	}
}
