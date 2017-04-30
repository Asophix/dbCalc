package dbCalc;

import java.util.ArrayList;

public class FuncDep {
	
	private ArrayList<Attribute> master; //left-side of FuncDep
	private Attribute slave;  //right-side of FuncDep
	
	public FuncDep(ArrayList<Attribute> master, Attribute slave) {
		for (Attribute item : master) {
			item.setName(Character.toUpperCase(item.getName()));
		}
		slave.setName(Character.toUpperCase(slave.getName()));
		this.master = master;
		this.slave = slave;
		System.out.println("FuncDep(): " + getMasterName() + "->" + getSlaveName() + " created.");
	}
	
	public FuncDep(Attribute master, Attribute slave) {
		this.master = new ArrayList<>(0);
		master.setName(Character.toUpperCase(master.getName()));
		slave.setName(Character.toUpperCase(slave.getName()));
		this.master.add(master);
		this.slave = slave;
	}
	
	public FuncDep(String master, char slave) {
		this.master = new ArrayList<Attribute>(0);
		for (int i = 0; i < master.length(); i++) {
			this.master.add(new Attribute(Character.toUpperCase(master.charAt(i))));
		}
		this.slave = new Attribute(Character.toUpperCase(slave));
	}
	
	public FuncDep(FuncDep funcDep) {
		this.master = new ArrayList<Attribute>(funcDep.getMaster());
		this.slave = new Attribute(funcDep.getSlave());
	}

	//removes an array of attributes from the left side 
	public void removeMasterElement(ArrayList<Attribute> attrlist) {
		for (Attribute item : attrlist) {
			Character.toUpperCase(item.getName());
			System.out.println("FuncDep.removeMasterElement():");
			char queryLetter = item.getName(); 
			for (int i = 0; i < master.size(); i++) {
				if (master.get(i).getName() == queryLetter) {
					master.remove(i);
					System.out.println("Attribute " + queryLetter + " removed.");
				}
			}
		}
	}
	
	public void removeMasterElement(char attr) {
		attr = Character.toUpperCase(attr);
		System.out.println("FuncDep.removeMasterElement():");
		for (int i = 0; i < master.size(); i++) {
			if (master.get(i).getName() == attr) {
				master.remove(i);
				System.out.println("Attribute " + attr + " removed.");
			}
		}
	}
	
	//getters that return attributes on a particular side in a string
	public String getMasterName() {
		StringBuilder result = new StringBuilder("");
		for (Attribute item : master) {
			result.append(item.getName());
		}
		return result.toString();
	}
	
	public boolean same(FuncDep fd) {
		if ((this.getMasterName().equals(fd.getMasterName()))
			&& (this.getSlaveName() == fd.getSlaveName())) {
			return true;
		}
		return false;
	}
	
	public char getSlaveName() {
		return slave.getName();
	}
	
	public ArrayList<Attribute> getMaster() {
		return master;
	}
	
	public Attribute getSlave() {
		return slave;
	}
	
	//secondary function for providing better output
	public String stringify() {
		String s;
		s = getMasterName().toString() + "->" + getSlaveName(); 
		return s;
	}
	
}
