package dbCalc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Window extends JFrame {

	private static final long serialVersionUID = 1485565230491815565L;
	JPanel bigPanel, panel3, panel4; 
	FDPanel panel1, panel2;
	JTextField master, slave, attrs;
	JLabel textArrow, textFD, textAction, textClosure;
	JButton btnAdd, btnDelete, btnClosure, btnDeleteAll;
	FuncDepSet f, g;
	final float versionNumber = 0.4f;
	final String title = "dbCalc - Functional Dependency Calculator v" + versionNumber;
	
	
	public Window(FuncDepSet f, FuncDepSet g) {
		this.f = f;
		this.g = g;
		//--- instantiate panels
		bigPanel = new JPanel();
		this.add(bigPanel);
		bigPanel.setLayout(null);
		bigPanel.setBounds(0, 0, 638, 480);
		panel1 = new FDPanel(f, 1, 100);
		panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2 = new FDPanel(g, 321, 100);
		panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel3 = new JPanel();
		panel4 = new JPanel();
		bigPanel.add(panel1);
		bigPanel.add(panel2);
		bigPanel.add(panel3);
		bigPanel.add(panel4);
		panel3.setLayout(null);
		panel3.setBounds(1, 0, 640, 120);
		panel4.setLayout(null);
		panel4.setBounds(1, 340, 640, 120);
		//--- instantiate panel3 components
		textFD = new JLabel("Functional dependency");
		textFD.setBounds(2, 0, 140, 20);
		//---
		master = new JTextField("");
		master.setBounds(2, 20, 50, 20);
		//---
		textArrow = new JLabel("=>");
		textArrow.setBounds(58, 20, 20, 20);
		//---
		slave = new JTextField("");
		slave.setBounds(80, 20, 50, 20);
		//---
		textClosure = new JLabel("Attributes set");
		textClosure.setBounds(2, 40, 100, 20);
		//---
		attrs = new JTextField("");
		attrs.setBounds(2, 60, 100, 20);
		//--- add resources to panel3
		panel3.add(master);
		panel3.add(slave);
		panel3.add(attrs);
		panel3.add(textArrow);
		panel3.add(textFD);
		panel3.add(textClosure);
		//--- set window-specific attributes 
		this.setLayout(null);
		this.setTitle(title);
		this.setSize(640, 480);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(640,480));		
		this.pack();
	}
	
	public class FDPanel extends JPanel {

		private static final long serialVersionUID = 361288910343324539L;
		
		JTextArea textArea;
		JScrollPane textPane;
		JButton btnAdd, btnDelete, btnClosure, btnDeleteAll, btnMinCover;
		FuncDepSet set;
		JLabel textAction;
		
		public FDPanel (FuncDepSet f, int x, int y) {
			this.set = f;
			this.setLayout(null);
			this.setBounds(x, y, 320, 240);
			//---
			textArea = new JTextArea();
			btnAdd = new JButton("Add FD");
			btnDelete = new JButton("Delete FD");
			btnDeleteAll = new JButton("Delete all");
			btnClosure = new JButton("Closure");
			btnMinCover = new JButton("Min. cover");
			textPane = new JScrollPane(textArea);
			textAction = new JLabel("Actions");
			//---
			this.add(textPane);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			textPane.setBounds(1, 1, 200, 240);
			//---
			this.add(textAction);
			textAction.setBounds(201, 0, 100, 20);
			//---
			this.add(btnAdd);
			btnAdd.setBounds(211, 30, 100, 20);
			btnAdd.addMouseListener(new AddButtonEventer());
			//---
			this.add(btnDelete);
			btnDelete.setBounds(211, 50, 100, 20);
			btnDelete.addMouseListener(new DeleteButtonEventer());
			//---
			this.add(btnDeleteAll);
			btnDeleteAll.setBounds(211, 70, 100, 20);
			btnDeleteAll.addMouseListener(new DeleteAllButtonEventer());
			//---
			this.add(btnClosure);
			btnClosure.setBounds(211, 90, 100, 20);
			btnClosure.addMouseListener(new ClosureButtonEventer());
			//
			this.add(btnMinCover);
			btnMinCover.setBounds(211, 110, 100, 20);
			btnMinCover.addMouseListener(new MinCoverButtonEventer());
			//---
			
			
		}
		
		public class AddButtonEventer implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String mt = master.getText();
				String st = slave.getText();
				if ((arg0.getButton() == MouseEvent.BUTTON1) 
					&& (!mt.isEmpty()) 
					&& (!st.isEmpty())) {
						textArea.setText("");
						set.addFuncDep(mt, st);
						set.writeSet();
						ArrayList<FuncDep> fdset = set.getSet();
						for (FuncDep item : fdset) {
							String textToAppend = item.stringify();
							textArea.append(textToAppend + "\n");
					}
					//szovegdoboz.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}
		
		public class DeleteButtonEventer implements MouseListener {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String mt = master.getText();
				String st = slave.getText();
				if ((arg0.getButton() == MouseEvent.BUTTON1) 
					&& (!mt.isEmpty()) 
					&& (!st.isEmpty())) {
						textArea.setText("");
						set.removeFuncDep(mt, st);
						for (FuncDep item : set.getSet()) {
							String textToAppend = item.stringify();
							textArea.append(textToAppend + "\n");
					}
					//szovegdoboz.repaint();
				}
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					for (int i = 0; i < 10; i++) {	
						textArea.append("ZERG RUSH zZzZzZzZzZzZ\n");
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}

		public class ClosureButtonEventer implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String at = attrs.getText();
				if ((arg0.getButton() == MouseEvent.BUTTON1) 
					//&& (!at.isEmpty())
					) {
					textArea.setText("");
					String closure = set.closure(at);
					set.writeSet();
					ArrayList<FuncDep> fdset = set.getSet();
					for (FuncDep item : fdset) {
						String textToAppend = item.stringify();
						textArea.append(textToAppend + "\n");
					}
					textArea.append("Closure:\n");
					textArea.append(closure + "\n");
					//szovegdoboz.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}
		
	public class DeleteAllButtonEventer implements MouseListener {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1) {
					set.removeAll();
					textArea.setText("");
					//szovegdoboz.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
	}
	
	public class MinCoverButtonEventer implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				textArea.setText("");
				ArrayList<FuncDep> fdset = set.getSet();
				for (FuncDep item : fdset) {
					textArea.append(item.stringify() + "\n");
				}
				textArea.append("---\nMinimal cover:\n");
				fdset = set.minCover();
				for (FuncDep item : fdset) {
					textArea.append(item.stringify() + "\n");
				}
				//szovegdoboz.repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	}
}