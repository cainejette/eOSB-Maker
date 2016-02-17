package eOSB_Maker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChoosePasswordActionListener implements ActionListener {
	
	private Builder builder;
	private JFrame frame;
	
	public ChoosePasswordActionListener(Builder builder, JFrame frame) {
		this.builder = builder;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String firstPasswordAttempt = (String) JOptionPane.showInputDialog(
													frame, 
													"Enter password:", 
													"Password input",
													JOptionPane.PLAIN_MESSAGE, 
													null, 
													null, 
													null);

		if (firstPasswordAttempt == null || firstPasswordAttempt.isEmpty()) {
			JOptionPane.showMessageDialog(
								frame, 
								"Password cannot be null or empty", 
								"Password input",
								JOptionPane.ERROR_MESSAGE);
		} 
		else {
			String secondPasswordAttempt = (String) JOptionPane.showInputDialog(
															frame, 
															"Confirm password:", 
															"Password input",
															JOptionPane.PLAIN_MESSAGE, 
															null, 
															null, 
															null);
			
			String password;
			if (secondPasswordAttempt == null || secondPasswordAttempt.isEmpty() || !secondPasswordAttempt.equals(firstPasswordAttempt)) {
				JOptionPane.showMessageDialog(
									frame, 
									"Passwords did not match", 
									"Password input",
									JOptionPane.ERROR_MESSAGE);
				password = null;
			} 
			else {
				password = secondPasswordAttempt;
				
				String saltHashPass;
				try {
					saltHashPass = Password.getSaltedHash(password);
					
					File passwordFile = new File("password.txt");
					this.builder.setPasswordFile(passwordFile);
					
					BufferedWriter writer = null;
					try {
						writer = new BufferedWriter(new FileWriter(passwordFile));
			            writer.write(saltHashPass);
					} 
					catch (IOException e1) {
						e1.printStackTrace();
					} 
					finally {
						try {
							writer.close();
						} 
						catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				} 
				catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
	}
}
