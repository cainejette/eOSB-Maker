package eOSB_Maker;

import java.awt.Container;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Builder {

	JFrame frame;
	private File questionDirectory;
	JLabel questionDirectoryLabel = new JLabel();
	
	JLabel passwordLabel = new JLabel();
	String password;
	
	JLabel expirationLabel = new JLabel();
	String expiration;
	
	private File baseFile;
	JLabel baseLabel = new JLabel();
	
	JButton goButton = new JButton("Generate new eOSB");
	JLabel goLabel = new JLabel();

	public Builder() {
		this.frame = new JFrame();
		
		this.populateContentPane();
		this.initFrame();
	}
	
	private void populateContentPane() {
		Container contentPane = frame.getContentPane();

		JPanel panel = new JPanel();
		
		panel.setLayout(new MigLayout("wrap 2"));
		
		panel.add(ButtonMaker.MakeButton(ButtonTypes.QUESTION, this, this.frame), "sizegroupx 1");
		panel.add(questionDirectoryLabel);

		panel.add(ButtonMaker.MakeButton(ButtonTypes.EXPIRATION, this, this.frame), "sizegroupx 1, gapy 30px");
		panel.add(expirationLabel);		
		
		panel.add(ButtonMaker.MakeButton(ButtonTypes.PASSWORD, this, this.frame), "sizegroupx 1, gapy 30px");
		panel.add(passwordLabel);
		
		panel.add(ButtonMaker.MakeButton(ButtonTypes.BASE, this, this.frame), "sizegroupx 1, gapy 30px");
		panel.add(baseLabel);
		
		panel.add(goButton = ButtonMaker.MakeButton(ButtonTypes.GO, this, this.frame), "sizegroupx 1, gapy 30px");
		panel.add(goLabel);
		
		panel.add(ButtonMaker.MakeButton(ButtonTypes.CLEAR, this, this.frame), "sizegroupx 1, wrap");
		panel.add(ButtonMaker.MakeButton(ButtonTypes.EXIT, this, this.frame), "sizegroupx 1");

		contentPane.add(panel);
	}
	
	private void initFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("eOSB Maker");
		frame.pack();
		frame.setMinimumSize(new Dimension(200, 200));
		frame.setVisible(true);
	}
	
	public void setQuestionDirectory(File questionDirectory) {
		this.questionDirectory = questionDirectory;
		if (questionDirectory == null) {
			questionDirectoryLabel.setText("");
		}
		else {
			questionDirectoryLabel.setText(questionDirectory.getAbsolutePath());	
		}
		
		updateGoButtonStatus();
		frame.pack();
	}
	
	public File getQuestionDirectory() {
		return this.questionDirectory;
	}
	
	public void setBaseFile(File baseFile) {
		this.baseFile = baseFile;
		
		if (baseFile == null) {
			baseLabel.setText("");
		}
		else {
			baseLabel.setText(baseFile.getAbsolutePath());	
		}
		
		updateGoButtonStatus();
		frame.pack();
	}
	
	public File getBaseFile() {
		return this.baseFile;
	}
	
	public void setExpiration(String expiration) {
		this.expiration = expiration;
		
		if (this.expiration != null) {
			expirationLabel.setText(expiration);			
		}
		else {
			expirationLabel.setText("");
		}
		
		updateGoButtonStatus();
		frame.pack();
	}
	
	public String getExpiration() {
		return this.expiration;
	}
		
	public void setPassword(String password) {
		this.password = password;
		
		if (this.password != null) {
			passwordLabel.setText("************");
		}
		else {
			passwordLabel.setText("");
		}
		
		updateGoButtonStatus();
		frame.pack();
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void clearGoLabel() {
		this.goLabel.setText("");
	}

	private void updateGoButtonStatus() {
		boolean shouldEnable = baseFile != null && expiration != null && password != null && questionDirectory != null;
		goButton.setEnabled(shouldEnable);
	}
	
	public void showDone(String output) {
		goLabel.setText("Created: " + output);
		goLabel.setVisible(true);
		frame.pack();
	}
}
