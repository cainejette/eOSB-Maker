package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import eOSB_Maker.Builder;
import eOSB_Maker.JarFileFilter;

public class ChooseBaseFileActionListener implements ActionListener {

	private Builder builder;
	private JFrame frame;

	public ChooseBaseFileActionListener(Builder builder, JFrame frame) {
		this.builder = builder;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose base eOSB");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new JarFileFilter());
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File baseFile = chooser.getSelectedFile();
			this.builder.setBaseFile(baseFile);
		}		
	}
}
