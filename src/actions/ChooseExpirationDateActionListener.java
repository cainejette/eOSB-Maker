package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import eOSB_Maker.Builder;

public class ChooseExpirationDateActionListener implements ActionListener {

	private Builder builder;
	private JFrame frame;
	
	public ChooseExpirationDateActionListener(Builder builder, JFrame frame) {
		this.builder = builder;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		final UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

		final JDialog dialog = new JDialog(this.frame, "Expiration date", true);
		JPanel panel = new JPanel();
		panel.add(datePicker);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ExpirationDateChosenActionListener(this.builder, dialog, model));

		panel.add(okButton);
		dialog.setContentPane(panel);
		dialog.pack();
		dialog.setVisible(true);
	}
}
