package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import eOSB_Maker.Builder;
import eOSB_Maker.Password;

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
				
				try {
					String saltHashPass = Password.getSaltedHash(password);
					this.builder.setPassword(saltHashPass);
				} 
				catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
