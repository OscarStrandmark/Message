package server;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class KillSwitchUI extends JFrame {

	private JPanel contentPane;
	private JButton btnExit;
	
	private Controller controller;
	public KillSwitchUI(Controller controller) {
		this.controller = controller;
		
		contentPane = new JPanel();
		btnExit = new JButton("Close server and write to log");

		setContentPane(contentPane);
		contentPane.add(btnExit);
		btnExit.addActionListener(e -> kill());
		contentPane.setBackground(Color.BLACK);
		new DangerColor().start();
		setTitle("KILLSWITCH");
		setSize(250, 75);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void kill() {
		controller.kill();
	}
	
	private class DangerColor extends Thread {
		
		//true=red,false=black
		private boolean color = true;

		public void run() {
			while(true) {
				try {
					Thread.sleep(750);
					
					if(color) {
						contentPane.setBackground(Color.RED);
						color = !color;
					} else {
						contentPane.setBackground(Color.BLACK);
						color = !color;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
