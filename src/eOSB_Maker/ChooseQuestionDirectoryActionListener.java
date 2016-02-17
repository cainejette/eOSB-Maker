package eOSB_Maker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ChooseQuestionDirectoryActionListener implements ActionListener {

	private Builder builder;
	private JFrame frame;
	
	public ChooseQuestionDirectoryActionListener(Builder builder, JFrame frame) {
		this.builder = builder;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose question directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	
		chooser.setAcceptAllFileFilterUsed(false);
	
		if (chooser.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			this.builder.setQuestionDirectory(chooser.getSelectedFile());
		}
	}
}
