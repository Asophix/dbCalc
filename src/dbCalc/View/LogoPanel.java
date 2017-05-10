package dbCalc.View;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3130230435546556132L;
	
	JLabel lWelcome;

	public LogoPanel() throws IOException {
		this.setLayout(null);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		//--- instantiate components
		lWelcome = new JLabel();
		lWelcome.setIcon(new ImageIcon("logo.png"));
		lWelcome.setBounds(0, 0, 150, 96);
		this.add(lWelcome);
		//---
	}

}
