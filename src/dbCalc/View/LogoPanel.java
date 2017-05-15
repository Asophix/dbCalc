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
	
	JLabel lLogo, lCredits;

	public LogoPanel() throws IOException {
		this.setLayout(null);
		//--- instantiate components
		lLogo = new JLabel();
		lLogo.setIcon(new ImageIcon("logo.png"));
		lLogo.setBounds(0, 0, 142, 43);
		this.add(lLogo);
		//---
		lCredits = new JLabel("(C) Dóka Zsolt - 2017");
		lCredits.setBounds(13, 43, 132, 20);
		this.add(lCredits);
	}

}
