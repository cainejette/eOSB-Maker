package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eOSB_Maker.Builder;
import eOSB_Maker.eOSB_Maker;

public class GoMakeEOSBActionListener implements ActionListener {

	private Builder builder;
	
	public GoMakeEOSBActionListener(Builder builder) {
		this.builder = builder;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			String path = eOSB_Maker.updateJarFile( 
					this.builder.getBaseFile().getAbsolutePath(),
					this.builder.getQuestionDirectory(), 
					this.builder.getPassword(), 
					this.builder.getExpiration());
			
			this.builder.showDone(path);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
