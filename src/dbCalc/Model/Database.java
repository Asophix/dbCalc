package dbCalc.Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An Object class that resembles a database. It contains two fields:
 *  - a set of attributes, known as a "relation"  
 *  - a set of functional dependencies between the relation's attributes.
 */

public class Database {

	//TODO Relation
	//Object négytagú deklarációval. Bal, mindegyik, jobb, egyik sem.
	//Amikor a függés frissül, akkor a relation is fog DataBase-ben. 
	//ArrayList<Attribute> ki lesz vezérelve Relation fieldjeibe.
	//Alapesetben mindenki mindkettőbe kerül.
	//Inkrementális implementáció:
	//f1: ui-n táblázat - BMEJ => done
	//f2: Helper - kulcs keresése
	//f3: Helper - normálformák!!!
	
	private ArrayList<FuncDep> set;
	
	//simple constructor
	public Database() {
		this.set = new ArrayList<>(0);
	}
	
	//parameterized constructors
	public Database(Database f) {
		this.set = new ArrayList<>(f.getSet());
	}
	
	public Database(ArrayList<FuncDep> set) {
		this.set = set;
	}
	
	//getter for a specific FuncDep index
	public FuncDep getFuncDep(int i) {
		return set.get(i);
	}
	
	//getter for the entire set
	public ArrayList<FuncDep> getSet() {
		return this.set;
	}
		
	//getter for size
	public int getSize() {
		return set.size();
	}
	
	public FuncDep getFuncDep(String master, char slave) throws Exception {
		for (FuncDep item : set) {
			System.out.println(item.getMasterName() + "->" + item.getSlaveName());
			if ((item.getMasterName().equals(master)) 
				&& (item.getSlaveName() == slave))
				return item;
		}
		throw new Exception("FuncDepSet.getFuncDep() found no items.");
	}

	//set configure
	public void setSet(ArrayList<FuncDep> newset) {
		this.set = newset;
	}
	
	//getter for a specific FuncDep index, stringified
	public String getFuncDepName(int i) {
		FuncDep f = getFuncDep(i);
		print("FuncDepSet.getFuncDepName(): FuncDep at index " + i + ": ");
		String result = f.stringify();
		println(result);
		return result;
	}
	
	public void printFuncDepName(int i) {
		FuncDep f = getFuncDep(i);
		print("FuncDepSet.getFuncDepName(): FuncDep at index " + i + ": ");
		String master = f.getMasterName();
		char slave = f.getSlaveName();
		println(master + "->" + slave);
	}
	
	//adds a FuncDep
	public void addFuncDep(String masterName, String slaveName) {
		println("FuncDepSet.addFuncDep():");
		masterName = Helper.simplify(masterName);
		slaveName = Helper.simplify(slaveName);
		for (int i = 0; i < slaveName.length(); i++) {
			boolean contains = false;
			for (int j = 0; j < masterName.length(); j++)
				if (masterName.charAt(j) == slaveName.charAt(i))
					contains = true;
			if (!contains) {
				set.add(new FuncDep(masterName, slaveName.charAt(i)));
				println("FuncDep " + masterName + "->" + slaveName.charAt(i) + " added to set.");
			}
		}
		set = Helper.omitDuplicates(this);
	}
	
	//removes a FuncDep at index
	public void removeFuncDep(int i) {
		set.remove(i);
		println("FuncDepSet.removeFuncDep(): FuncDep removed.");
		//System.out.println("Size:" + set.size());
		//writeSet();
	}
	
	//removes FuncDeps based on a string query
	public void removeFuncDep(String master, String slave) {
		master = master.toUpperCase();
		slave = slave.toUpperCase();
		ArrayList<Integer> deleteable = new ArrayList<>(0);
		for (int j = 0; j < slave.length(); j++) {
			for (int i = 0; i < set.size(); i++) {
				if ((set.get(i).getMasterName().equals(master))
					&& (set.get(i).getSlaveName() == slave.charAt(j))) {
					deleteable.add(i);
				}
			}
		}
		Collections.sort(deleteable, Collections.reverseOrder());
		for (int i = 0; i < deleteable.size(); i++) {
			removeFuncDep(deleteable.get(i));
		}
	}
	
	public void removeAll() {
		set.clear();
	}
	
	public void print(String s) {
		System.out.print(s);
	}
	
	public void println(String s) {
		System.out.println(s);
	}
	
	public void writeSet() {
		println("FuncDepSet.writeSet():");
		for (FuncDep item : set) {
			println(item.getMasterName() + "->" + item.getSlaveName());
		}
	}

}
