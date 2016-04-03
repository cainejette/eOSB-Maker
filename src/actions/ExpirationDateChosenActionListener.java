package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import org.jdatepicker.impl.UtilDateModel;

import eOSB_Maker.Builder;

public class ExpirationDateChosenActionListener implements ActionListener {
	
	private Builder builder;
	private JDialog dialog;
	private UtilDateModel model;
	
	public ExpirationDateChosenActionListener(Builder builder, JDialog dialog, UtilDateModel model) {
		this.builder = builder;
		this.dialog = dialog;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String expiration = model.getYear() + "/" + (model.getMonth() + 1) + "/" + model.getDay();
		this.builder.setExpiration(expiration);

		dialog.setVisible(false);
		dialog.dispose();
	}
}
