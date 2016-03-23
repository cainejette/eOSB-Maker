package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eOSB_Maker.Builder;

public class ClearButtonActionListener implements ActionListener {

	private Builder builder;
	
	public ClearButtonActionListener(Builder builder) {
		this.builder = builder;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.builder.setBaseFile(null);
		this.builder.setExpiration(null);
		this.builder.setPassword(null);
		this.builder.setQuestionDirectory(null);
		this.builder.clearGoLabel();
	}
}
