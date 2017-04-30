package dbCalc;

import java.awt.Color;
import java.awt.Component;
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

import dbCalc.Content.State;

public class Window extends JFrame {

	private static final long serialVersionUID = 1485565230491815565L;
	JPanel bigPanel, panel3, panel31, panel32, 
		   panel4, panel41, panel42, panel5; 
	FDPanel panel1, panel2;
	JTextField master, slave, attrs,
			   left, right, both, none;
	JLabel textArrow, textFD, textAction, textClosure, 
		   textLeft, textRight, textBoth, textNone,
		   textStatus;
	JButton btnAdd, btnDelete, btnClosure, btnDeleteAll;
	Database f, g;
	final double versionNumber = 0.5;
	final String title = "dbCalc - Functional Dependency Calculator v" + versionNumber;
	
	public Window(Database f, Database g) {
		this.f = f;
		this.g = g;
		//--- instantiate panels
		bigPanel = new JPanel();
		this.add(bigPanel);
		bigPanel.setLayout(null);
		bigPanel.setBounds(0, 0, 640, 480);
		panel1 = new FDPanel(f, 0, 100);
		panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2 = new FDPanel(g, 320, 100);
		panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel3 = new JPanel();
		panel31 = new JPanel();
		panel32 = new JPanel();
		panel4 = new JPanel();
		panel41 = new JPanel();
		panel41.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel42 = new JPanel();
		panel42.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel5 = new JPanel();
		bigPanel.add(panel1);
		bigPanel.add(panel2);
		bigPanel.add(panel3);
		bigPanel.add(panel4);
		bigPanel.add(panel5);
		panel3.setLayout(null);
		panel3.setBounds(0, 0, 640, 100);
		panel4.setLayout(null);
		panel4.setBounds(0, 340, 640, 90);
		panel31.setLayout(null);
		panel31.setBounds(0, 0, 320, 100);
		panel3.add(panel31);
		panel32.setLayout(null);
		panel32.setBounds(320, 0, 320, 100);
		panel3.add(panel32);
		panel41.setLayout(null);
		panel41.setBounds(0, 0, 320, 90);
		panel4.add(panel41);
		panel42.setLayout(null);
		panel42.setBounds(320, 0, 320, 90);
		panel4.add(panel42);
		panel5.setLayout(null);
		panel5.setBounds(0, 430, 640, 50);
		for (Component c : panel4.getComponents()) {
			System.out.println(c.getLocation() + "," + c.getHeight());
		}
		//--- instantiate panel31 components
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
		textClosure = new JLabel("Attribute set (relation)");
		textClosure.setBounds(2, 40, 160, 20);
		//---
		attrs = new JTextField("");
		attrs.setBounds(2, 60, 100, 20);
		//--- add resources to panel31
		panel31.add(master);
		panel31.add(slave);
		panel31.add(attrs);
		panel31.add(textArrow);
		panel31.add(textFD);
		panel31.add(textClosure);
		//--- instantiate panel41 components
		textLeft = new JLabel("Left (primary)");
		textLeft.setBounds(2, 0, 100, 20);
		//---
		left = new JTextField("");
		left.setBounds(2, 20, 100, 20);
		left.setEnabled(false);
		//---
		textRight = new JLabel("Right (secondary)");
		textRight.setBounds(102, 0, 100, 20);
		//---
		right = new JTextField("");
		right.setBounds(102, 20, 100, 20);
		right.setEnabled(false);
		//---
		textBoth = new JLabel("Both (uncertain)");
		textBoth.setBounds(2, 40, 100, 20);
		//---
		both = new JTextField("");
		both.setBounds(2, 60, 100, 20);
		both.setEnabled(false);
		//---
		textNone = new JLabel("None (primary)");
		textNone.setBounds(102, 40, 100, 20);
		//---
		none = new JTextField("");
		none.setBounds(102, 60, 100, 20);
		none.setEnabled(false);
		//--- add resources to panel41
		panel41.add(textLeft);
		panel41.add(left);
		panel41.add(textRight);
		panel41.add(right);
		panel41.add(textBoth);
		panel41.add(both);
		panel41.add(textNone);
		panel41.add(none);
		//---
		//instantiate status bar
		textStatus = new JLabel("dbCalc> calculator by Dóka Zsolt - 2017");
		textStatus.setEnabled(false);
		textStatus.setBounds(0, 0, 640, 20);
		panel5.add(textStatus);
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
		JButton btnAdd, btnDelete, btnClosure, btnDeleteAll, btnMinCover, btnKey;
		Database set;
		JLabel textAction;
		
		public FDPanel (Database f, int x, int y) {
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
			btnKey = new JButton("Evaluate");
			textPane = new JScrollPane(textArea);
			textAction = new JLabel("Actions");
			//---
			this.add(textPane);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			textPane.setBounds(0, 0, 200, 239);
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
			//---
			this.add(btnMinCover);
			btnMinCover.setBounds(211, 110, 100, 20);
			btnMinCover.addMouseListener(new MinCoverButtonEventer());
			//---
			this.add(btnKey);
			btnKey.setBounds(211, 130, 100, 20);
			btnKey.addMouseListener(new KeyButtonEventer());	
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
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				textStatus.setText("Adds a functional dependency to the container.");
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				textStatus.setText("Ready");
			}

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
						textArea.append("Created for GajdOS (C)\n");
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				String m = Helper.simplify(master.getText()).toUpperCase();
				String s = Helper.simplify(slave.getText()).toUpperCase();
				if (!(m.isEmpty() || (s.isEmpty())))
					textStatus.setText("Removes " + m + "=>" + s + " from the container.");
				else {
					textStatus.setText("Removes a given functional dependency from the container.");
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				textStatus.setText("Ready");
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}

		public class ClosureButtonEventer implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String at = attrs.getText();
				ArrayList<FuncDep> fdset = set.getSet();
				if ((arg0.getButton() == MouseEvent.BUTTON1) 
					&& (!fdset.isEmpty()
					&& (!at.isEmpty()))
					) {
					textArea.setText("");
					for (FuncDep item : fdset) {
						String textToAppend = item.stringify();
						textArea.append(textToAppend + "\n");
					}
					String closure = Helper.closure(fdset, at);
					textArea.append("---\nClosure:\n");
					textArea.append(closure + "\n");
					//szovegdoboz.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				String at = attrs.getText();
				at = Helper.simplify(at).toUpperCase();
				if (!(set.getSet().isEmpty()) && (!at.isEmpty()))
					textStatus.setText("Computes closure of {" + at + "} in the container.");
				else {
					textStatus.setText("Computes closure of an attribute set.");
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				textStatus.setText("Ready");
			}

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
			public void mouseEntered(MouseEvent arg0) {
				textStatus.setText("Deletes all dependencies in the container.");
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				textStatus.setText("Ready");
			}

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
				fdset = Helper.minCover(fdset);
				for (FuncDep item : fdset) {
					textArea.append(item.stringify() + "\n");
				}
				//szovegdoboz.repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			textStatus.setText("Computes minimal cover of dependency set in the container.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			textStatus.setText("Ready");
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	public class KeyButtonEventer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			String at = attrs.getText().toUpperCase();
			if (arg0.getButton() == MouseEvent.BUTTON1
					&& !at.isEmpty()) {
					String sideNone = Helper.createSide(set.getSet(), at, State.NONE);
					String sideLeft = Helper.createSide(set.getSet(), at, State.LEFT);
					String sideRight = Helper.createSide(set.getSet(), at, State.RIGHT);
					String sideBoth = Helper.removeString(at, sideLeft);
					sideBoth = Helper.removeString(sideBoth, sideRight);
					sideBoth = Helper.removeString(sideBoth, sideNone);
					none.setText(sideNone);
					left.setText(sideLeft);
					right.setText(sideRight);
					both.setText(sideBoth);
				}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			textStatus.setText("Evaluates primary/secondary attributes.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			textStatus.setText("Ready");
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}

	}
}