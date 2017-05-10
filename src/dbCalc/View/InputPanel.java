package dbCalc.View;


import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dbCalc.Model.Content.State;
import dbCalc.Model.FuncDep;
import dbCalc.Model.Helper;

public class InputPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2800783750060069235L;
	JTextField master, slave, attrs;
	JLabel textFD, textArrow, textAction, textRelation, textTitle;

	JButton btnAdd, btnDelete, btnClosure, btnMinCover, btnKey;
	
	public InputPanel() {
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		//--- instantiate components
		textTitle = new JLabel("<html><u>Input data</u></html>");
		textTitle.setBounds(50, 0, 60, 20);
		this.add(textTitle);
		//--- FD input
		textFD = new JLabel("Functional dependency");
		textFD.setBounds(15, 20, 130, 20);
		this.add(textFD);
		//---
		master = new JTextField();
		master.setBounds(2, 40, 65, 20);
		this.add(master);
		//---
		textArrow = new JLabel(" =>");
		textArrow.setBounds(67, 40, 20, 20);
		this.add(textArrow);
		//---
		slave = new JTextField();
		slave.setBounds(90, 40, 68, 20);
		this.add(slave);
		//---
		btnAdd = new JButton("Add");
		btnAdd.setBounds(2, 60, 78, 20);
		btnAdd.addMouseListener(new AddListener());
		this.add(btnAdd);
		//---
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(80, 60, 78, 20);
		btnDelete.addMouseListener(new DeleteListener());
		this.add(btnDelete);
		//--- relation input
		textRelation = new JLabel("Relation (attribute set)");
		textRelation.setBounds(15, 80, 130, 20);
		this.add(textRelation);
		//---
		attrs = new JTextField();
		attrs.setBounds(2, 100, 156, 20);
		this.add(attrs);
		//--- attribute set evaluation input
		btnClosure = new JButton("Closure");
		btnClosure.setBounds(30, 120, 100, 20);
		btnClosure.addMouseListener(new ClosureListener());
		this.add(btnClosure);
		//---
		btnMinCover = new JButton("Min.cover");
		btnMinCover.setBounds(30, 165, 100, 20);
		btnMinCover.addMouseListener(new MinCoverListener());
		this.add(btnMinCover);
		//---
		btnKey = new JButton("Keys");
		btnKey.setBounds(30, 185, 100, 20);
		btnKey.addMouseListener(new KeyListener());
		this.add(btnKey);
	}
	
	public class AddListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			String mt = master.getText().toUpperCase();
			String st = slave.getText().toUpperCase();
			if (!mt.isEmpty() && !st.isEmpty() && (arg0.getButton() == MouseEvent.BUTTON1)) {
				Window.f.addFuncDep(mt, st);
				Window.statusPanel.status.setText(mt + "->"	+ st + "added to the set.");
			}
			Window.fdPanel.textArea.setText("");
			for (FuncDep fd : Window.f.getSet()) {
				Window.fdPanel.textArea.append(fd.stringify() + "\n");
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Window.statusPanel.status.setText("Adds a dependency to the set. "
					+ "Duplicate dependencies will not be added.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class DeleteListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			String mt = master.getText().toUpperCase();
			String st = slave.getText().toUpperCase();
			if (!mt.isEmpty() && !st.isEmpty() && (arg0.getButton() == MouseEvent.BUTTON1)) {
				Window.f.removeFuncDep(mt, st);
				Window.statusPanel.status.setText("Removed" + mt + "->"	+ st + "from the set.");
			}
			Window.fdPanel.textArea.setText("");
			for (FuncDep fd : Window.f.getSet()) {
				Window.fdPanel.textArea.append(fd.stringify() + "\n");
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			String mt = master.getText().toUpperCase();
			String st = slave.getText().toUpperCase();
			if (!mt.isEmpty() && !st.isEmpty())
				Window.statusPanel.status.setText("Removes" + mt + "->"	+ st + "from the set.");
			else
				Window.statusPanel.status.setText("Removes a dependency with specific attributes from the set.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class ClosureListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			String at = attrs.getText().toUpperCase();
			String closure;
			if (!at.isEmpty() && (arg0.getButton() == MouseEvent.BUTTON1)) {
				Window.fdPanel.textArea.setText("");
				for (FuncDep fd : Window.f.getSet()) {
					Window.fdPanel.textArea.append(fd.stringify() + "\n");
				}
				Window.fdPanel.textArea.append("Closure:\n------\n");
				closure = Helper.closure(Window.f.getSet(), at);
				Window.fdPanel.textArea.append(closure);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			String at = master.getText().toUpperCase();
			if (!at.isEmpty())
				Window.statusPanel.status.setText("Computes attribute closure of {" + at + "} on the set.");
			else
				Window.statusPanel.status.setText("Computes attribute closure of given attributes on the set.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class MinCoverListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			ArrayList<FuncDep> mincover;
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				Window.fdPanel.textArea.setText("");
				for (FuncDep fd : Window.f.getSet()) {
					Window.fdPanel.textArea.append(fd.stringify() + "\n");
				}
				Window.fdPanel.textArea.append("Minimal cover:\n------\n");
				mincover = Helper.minCover(Window.f.getSet());
				for (FuncDep fd : mincover)
					Window.fdPanel.textArea.append(fd.stringify() + "\n");
			}			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Window.statusPanel.status.setText("Computes minimal cover and shrinks current attribute set.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class KeyListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			String la, ra, ba, na;
			String at = attrs.getText().toUpperCase();
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				la = Helper.createSide(Window.f.getSet(), at, State.LEFT);
				ra = Helper.createSide(Window.f.getSet(), at, State.RIGHT);
				na = Helper.createSide(Window.f.getSet(), at, State.NONE);
				Window.keyPanel.left.setText(la);
				Window.keyPanel.right.setText(ra);
				Window.keyPanel.none.setText(na);
				ba = at;
				ba = Helper.removeString(ba, la);
				ba = Helper.removeString(ba, ra);
				ba = Helper.removeString(ba, na);
				Window.keyPanel.both.setText(ba);
			}	
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			String at = master.getText().toUpperCase();
			if (!at.isEmpty())
				Window.statusPanel.status.setText("Evaluates primary and secondary attributes of {" + at + "} on the set.");
			else
				Window.statusPanel.status.setText("Evaluates primary and secondary attributes of given attributes on the set.");
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

}
