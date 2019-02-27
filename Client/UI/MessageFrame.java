package UI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MessageFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	public MessageFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		setVisible(true);
		init();
	}

	private void init() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 11, 803, 36);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnAddReciever = new JButton("Add reciever");
		btnAddReciever.setBounds(823, 11, 151, 36);
		contentPane.add(btnAddReciever);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 58, 964, 487);
		contentPane.add(textArea);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBounds(856, 593, 118, 57);
		contentPane.add(btnNewButton);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 603, 836, 36);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton_1 = new JButton("Add image");
		btnNewButton_1.setBounds(10, 556, 118, 36);
		contentPane.add(btnNewButton_1);
	}
}
