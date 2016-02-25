package eOSB_Maker;

import javax.swing.JButton;
import javax.swing.JFrame;

import actions.ChooseBaseFileActionListener;
import actions.ChooseExpirationDateActionListener;
import actions.ChoosePasswordActionListener;
import actions.ChooseQuestionDirectoryActionListener;
import actions.ClearButtonActionListener;
import actions.ExitButtonActionListener;
import actions.GoMakeEOSBActionListener;

public final class ButtonMaker {
	public static JButton MakeButton(ButtonTypes type, Builder builder, JFrame frame) {
		JButton button = new JButton();
		
		switch (type) {
		case QUESTION:
			button = new JButton("Choose question directory");
			button.addActionListener(new ChooseQuestionDirectoryActionListener(builder, frame));;
			break;
			
		case EXPIRATION:
			button = new JButton("Choose expiration date");
			button.addActionListener(new ChooseExpirationDateActionListener(builder, frame));;
			break;
		
		case PASSWORD:
			button = new JButton("Choose password");
			button.addActionListener(new ChoosePasswordActionListener(builder, frame));;
			break;
		
		case BASE:
			button = new JButton("Choose base eOSB");
			button.addActionListener(new ChooseBaseFileActionListener(builder, frame));;
			break;
		
		case GO:
			button = new JButton("Generate new eOSB");
			button.addActionListener(new GoMakeEOSBActionListener(builder));
			break;
		
		case CLEAR:
			button = new JButton("Clear");
			button.addActionListener(new ClearButtonActionListener(builder));
			break;
			
		case EXIT:
			button = new JButton("Exit");
			button.addActionListener(new ExitButtonActionListener(frame));
			break;
		}
		
		return button;
	}
}
