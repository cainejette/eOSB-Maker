package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eOSB_Maker.Builder;

public class GoMakeEOSBActionListener implements ActionListener {

	private Builder builder;
	
	public GoMakeEOSBActionListener(Builder builder) {
		this.builder = builder;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.builder.generateEOSB();
	}
}
