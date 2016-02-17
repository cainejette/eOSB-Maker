package eOSB_Maker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButtonActionListener implements ActionListener {

	private Builder builder;
	
	public ClearButtonActionListener(Builder builder) {
		this.builder = builder;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		this.builder.setBaseFile(null);
		this.builder.setExpirationFile(null, "");
		this.builder.setPasswordFile(null);
		this.builder.setQuestionDirectory(null);

	}

}
