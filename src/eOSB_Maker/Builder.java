package eOSB_Maker;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	File passwordFile;
	
	JLabel expirationLabel = new JLabel();
	File expirationFile;
	
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
		
		panel.add(createQuestionButton(), "sizegroupx 1");
		panel.add(questionDirectoryLabel);

		panel.add(createExpirationButton(), "sizegroupx 1, gapy 30px");
		panel.add(expirationLabel);		
		
		panel.add(createPasswordButton(), "sizegroupx 1, gapy 30px");
		panel.add(passwordLabel);
		
		panel.add(createBaseButton(), "sizegroupx 1, gapy 30px");
		panel.add(baseLabel);
		
		panel.add(createGoButton(), "sizegroupx 1, gapy 30px");
		panel.add(goLabel);
		
		panel.add(createClearButton(), "sizegroupx 1, wrap");
		panel.add(createExitButton(), "sizegroupx 1");

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
	
	public void setExpirationFile(File expirationFile, String expiration) {
		this.expirationFile = expirationFile;
		
		if (expirationFile == null) {
			expirationLabel.setText("");
		}
		else {
			expirationLabel.setText(expiration);	
		}
		
		updateGoButtonStatus();
		frame.pack();
	}
	
	public File getExpirationFile() {
		return this.expirationFile;
	}
	
	public void setPasswordFile(File passwordFile) {
		this.passwordFile = passwordFile;
		
		if (passwordFile == null) {
			passwordLabel.setText("");
		}
		else {
			passwordLabel.setText("************");
		}
		
		updateGoButtonStatus();
		frame.pack();
	}
	
	public File getPasswordFile() {
		return this.passwordFile;
	}

	public JButton createQuestionButton() {
		JButton button = new JButton("Choose question directory");
		button.addActionListener(new ChooseQuestionDirectoryActionListener(this, this.frame));

		return button;
	}

	public JButton createExpirationButton() {
		JButton button = new JButton("Choose expiration date");
		button.addActionListener(new ChooseExpirationDateActionListener(this, this.frame));

		return button;
	}
	
	public JButton createPasswordButton() {
		JButton button = new JButton("Set password");
		button.addActionListener(new ChoosePasswordActionListener(this, this.frame));

		return button;
	}

	public JButton createBaseButton() {
		JButton button = new JButton("Choose base eOSB");
		button.addActionListener(new ChooseBaseFileActionListener(this, this.frame));
		
		return button;
	}
	
	private void updateGoButtonStatus() {
		boolean shouldEnable = baseFile != null && passwordFile != null && expirationFile != null && questionDirectory != null;
		goButton.setEnabled(shouldEnable);
	}
	
	public JButton createGoButton() {
		goButton = new JButton("Generate new eOSB");
		this.updateGoButtonStatus();
		goButton.addActionListener(new GoMakeEOSBActionListener(this));
		
		return goButton;
	}
	
	private JButton createClearButton() {
		JButton button = new JButton("Clear");
		button.addActionListener(new ClearButtonActionListener(this));
		
		return button;
	}
	
	private JButton createExitButton() {
		JButton button = new JButton("Exit");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
		
		return button;
	}
	
	public void showDone(String output) {
		goLabel.setText("Wrote: " + output);
		goLabel.setVisible(true);
		frame.pack();
	}
}
