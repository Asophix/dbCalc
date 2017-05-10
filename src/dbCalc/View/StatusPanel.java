package dbCalc.View;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1834610338388902755L;
	JLabel status;

	public StatusPanel() {
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		//---
		status = new JLabel("Welcome to DBCalc calculator!");
		status.setBounds(0, 0, 640, 20);
		status.setEnabled(false);
		this.add(status);
	}

}
