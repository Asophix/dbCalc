package dbCalc.View;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FDPanel extends JPanel {
	
	JLabel textFDBox;
	JScrollPane scrollArea;
	JTextArea textArea;
	JButton btnDeleteAll;

	private static final long serialVersionUID = 361288910343324539L;
	
	public FDPanel() {
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		//---
		textFDBox = new JLabel("<html><u>Dependency set</u></html>");
		textFDBox.setBounds(78, 2, 160, 20);
		this.add(textFDBox);
		//---
		textArea = new JTextArea();
		textArea.setBounds(2, 22, 236, 198);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		this.add(textArea);
		//---
		scrollArea = new JScrollPane(textArea);
		scrollArea.setBounds(2, 22, 236, 198);
		this.add(scrollArea);
		//---
		btnDeleteAll = new JButton("Delete all");
		btnDeleteAll.setBounds(2, 220, 236, 18);
		btnDeleteAll.addMouseListener(new DeleteAllListener());
		this.add(btnDeleteAll);
	}
	
	public class DeleteAllListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				Window.f.removeAll();
				Window.statusPanel.status.setText("Cleared dependency set.");
				Window.fdPanel.textArea.setText("");
				Window.iPanel.attrs.setText("");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Window.statusPanel.status.setText("Removes all dependencies from the set.");			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}