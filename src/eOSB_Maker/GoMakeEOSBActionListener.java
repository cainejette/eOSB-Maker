package eOSB_Maker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoMakeEOSBActionListener implements ActionListener {

	private Builder builder;
	
	public GoMakeEOSBActionListener(Builder builder) {
		this.builder = builder;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		List<File> files = new ArrayList<File>();
		for (File file : this.builder.getQuestionDirectory().listFiles()) {
			files.add(file);
		}
		try {
			String path = eOSB_Maker.updateJarFile( 
					this.builder.getBaseFile().getAbsolutePath(),
					files, 
					this.builder.getPasswordFile(), 
					this.builder.getExpirationFile());
			
			this.builder.showDone(path);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
