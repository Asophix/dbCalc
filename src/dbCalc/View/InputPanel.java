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
	JTextField master, slave, closure, attrs, extra;
	JLabel textFD, textArrow, textAction, textClosure, textTitle, textEvaluate;

	JButton btnAdd, btnDelete, btnClosure, btnMinCover, btnKey;
	
	public InputPanel() {
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		//--- instantiate components
		textTitle = new JLabel("<html><u>Input data</u></html>");
		textTitle.setBounds(50, 0, 60, 20);
		this.add(textTitle);
		//--- FD input
		textFD = new JLabel("Functional dependency:");
		textFD.setBounds(2, 20, 160, 20);
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
		textClosure = new JLabel("Relation to close:");
		textClosure.setBounds(2, 80, 130, 20);
		this.add(textClosure);
		//---
		closure = new JTextField();
		closure.setBounds(2, 100, 156, 20);
		this.add(closure);
		//--- attribute set evaluation input
		btnClosure = new JButton("Closure");
		btnClosure.setBounds(30, 120, 100, 20);
		btnClosure.addMouseListener(new ClosureListener());
		this.add(btnClosure);
		//---
		textEvaluate = new JLabel("Relation to evaluate:");
		textEvaluate.setBounds(2, 140, 156, 20);
		this.add(textEvaluate);
		//---
		attrs = new JTextField("");
		attrs.setBounds(2, 160, 118, 20);
		attrs.setEnabled(false);
		this.add(attrs);
		//---
		extra = new JTextField("");
		extra.setBounds(120, 160, 38, 20);
		this.add(extra);
		//---
		btnKey = new JButton("Evaluate");
		btnKey.setBounds(30, 180, 100, 20);
		btnKey.addMouseListener(new KeyListener());
		this.add(btnKey);
		//---
		btnMinCover = new JButton("Min.cover");
		btnMinCover.setBounds(30, 210, 100, 20);
		btnMinCover.addMouseListener(new MinCoverListener());
		this.add(btnMinCover);
	}
	
	public class AddListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			String mt = master.getText().toUpperCase();
			String st = slave.getText().toUpperCase();
			if (!mt.isEmpty() && !st.isEmpty() && (arg0.getButton() == MouseEvent.BUTTON1)) {
				Window.f.addFuncDep(mt, st);
				Window.statusPanel.status.setText(mt + "->"	+ st + " added to the set.");
			}
			Window.fdPanel.textArea.setText("");
			for (FuncDep fd : Window.f.getSet()) {
				Window.fdPanel.textArea.append(fd.stringify() + "\n");
			}
			attrs.setText(Helper.getAttributes(Window.f.getSet()));
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			String mt = master.getText().toUpperCase();
			String st = slave.getText().toUpperCase();
			if (!mt.isEmpty() && !st.isEmpty())
				Window.statusPanel.status.setText("Adds " + mt + "->" + st + " to the set. "
						+ "Duplicate dependencies will not be added.");
			else
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
				Window.statusPanel.status.setText("Removed " + mt + "->" + st + " from the set.");
				attrs.setText(Helper.getAttributes(Window.f.getSet()));
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
				Window.statusPanel.status.setText("Removes " + mt + "->" + st + " from the set.");
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
			String at = closure.getText().toUpperCase();
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
			String at = attrs.getText().toUpperCase();
			String et = extra.getText().toUpperCase();
			if (!at.isEmpty())
				Window.statusPanel.status.setText("Computes attribute closure of {" + (at + et) + "} on the set.");
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
				attrs.setText(Helper.getAttributes(Window.f.getSet()));
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
			String la, ra, ba;
			ArrayList<String> keys;
			String at = Helper.simplify(attrs.getText().toUpperCase() + extra.getText().toUpperCase());
			String et = Helper.removeString(extra.getText().toUpperCase(), attrs.getText().toUpperCase());
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				la = Helper.createSide(Window.f.getSet(), at, State.LEFT);
				ra = Helper.createSide(Window.f.getSet(), at, State.RIGHT);
				Window.keyPanel.left.setText(la);
				Window.keyPanel.right.setText(ra);
				Window.keyPanel.none.setText(et);
				ba = at;
				ba = Helper.removeString(ba, la);
				ba = Helper.removeString(ba, ra);
				ba = Helper.removeString(ba, et);
				Window.keyPanel.both.setText(ba);
				Window.keyPanel.textArea.setText("");
				keys = Helper.findKeys(Window.f.getSet(), la, ba, et);
				for (int i = 0; i < keys.size(); i++) {
					Window.keyPanel.textArea.append(keys.get(i));
					if (i < keys.size() - 1)
						Window.keyPanel.textArea.append(", ");
				}
				int nf = Helper.getNormalForm(Window.f.getSet(), keys);
				switch (nf) {
					case 4: 
						Window.keyPanel.nf.setText("BCNF");
						break;
					default:
						Window.keyPanel.nf.setText(nf +"NF");
						break;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			String at = attrs.getText().toUpperCase();
			String et = extra.getText().toUpperCase();
			if (!at.isEmpty())
				Window.statusPanel.status.setText("Evaluates attributes, keys and highest normal form on {" + (Helper.simplify(at+et)) + "} and the set.");
			else
				Window.statusPanel.status.setText("Evaluates attributes, keys and highest normal form on a given relation and the set.");
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
