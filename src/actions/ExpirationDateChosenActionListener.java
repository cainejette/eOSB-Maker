package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

		String expirationP = model.getYear() + "/" + (model.getMonth() + 1) + "/" + model.getDay();
		
		File expirationFile = new File("expiration.txt");
		this.builder.setExpirationFile(expirationFile, expirationP);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(expirationFile));
            writer.write(expirationP);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		dialog.setVisible(false);
		dialog.dispose();
	}
}
