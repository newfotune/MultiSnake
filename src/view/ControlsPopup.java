package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControlsPopup extends JDialog {
	private JPanel myPanel;
	
	public ControlsPopup() {
		myPanel = new JPanel();
		myPanel.setSize(300, 300);
		//this.setUndecorated(true);
		myPanel.add(createCloseButton());
		
		add(myPanel);
		pack();
		setVisible(true);
	}
	
	private JButton createCloseButton() {
		JButton button = new JButton("close");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				firePropertyChange("close",null,null);
				dispose();
			}
		});
		return button;
	}
}
