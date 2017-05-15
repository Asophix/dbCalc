package dbCalc.View;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class KeyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3653157922140956328L;
	JLabel textAttrs, textLeft, textRight, textBoth, textNone;
	JTextField left, right, both, none;
	JLabel textKeys, textNF, textNFValue, textReso;
	JTextArea textArea, resoArea;
	JScrollPane scrollArea, scrollReso;
	
	public KeyPanel() {
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		//--- Title component
		textAttrs = new JLabel("<html><u>Evaluation results</u></html>");
		textAttrs.setBounds(64, 2, 176, 20);
		this.add(textAttrs);
		//--- Left/Right components
		textLeft = new JLabel("Left (primary)");
		textLeft.setBounds(2, 22, 116, 20);
		this.add(textLeft);
		//---
		textRight = new JLabel("Right (secondary)");
		textRight.setBounds(122, 22, 118, 20);
		this.add(textRight);
		//---
		left = new JTextField("");
		left.setBounds(2, 42, 116, 20);
		left.setEnabled(false);
		this.add(left);
		//---
		right = new JTextField("");
		right.setBounds(120, 42, 116, 20);
		right.setEnabled(false);
		this.add(right);
		//--- Both/None components
		textBoth = new JLabel("Both");
		textBoth.setBounds(2, 62, 116, 20);
		this.add(textBoth);
		//---
		textNone = new JLabel("None (primary)");
		textNone.setBounds(122, 62, 118, 20);
		this.add(textNone);
		//---
		both = new JTextField("");
		both.setBounds(2, 82, 116, 20);
		both.setEnabled(false);
		this.add(both);
		//---
		none = new JTextField("");
		none.setBounds(120, 82, 116, 20);
		none.setEnabled(false);
		this.add(none);
		//--- Key lister components
		textKeys = new JLabel("<html><u>Keys:</u></html>");
		textKeys.setBounds(2, 102, 116, 20);
		this.add(textKeys);
		//---
		textArea = new JTextArea("");
		textArea.setBounds(2, 122, 236, 40);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		this.add(textArea);
		//---
		scrollArea = new JScrollPane(textArea);
		scrollArea.setBounds(2, 122, 236, 40);
		this.add(scrollArea);
		//---
		textNF = new JLabel("Highest normal form:");
		textNF.setBounds(2, 162, 178, 20);
		this.add(textNF);
		//---
		textNFValue = new JLabel("n/a");
		textNFValue.setBounds(200, 162, 58, 20);
		this.add(textNFValue);
		//---
		textReso = new JLabel("<html><u>Lossless 3NF resolution:</u></html>");
		textReso.setBounds(2, 182, 178, 20);
		this.add(textReso);
		//---
		resoArea = new JTextArea();
		resoArea.setBounds(2, 200, 236, 40);
		resoArea.setEditable(false);
		resoArea.setLineWrap(true);
		resoArea.setWrapStyleWord(true);
		this.add(resoArea);
		//---
		scrollReso = new JScrollPane(resoArea);
		scrollReso.setBounds(2, 200, 236, 40);
		this.add(scrollReso);
	}

}

