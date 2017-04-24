package dbCalc;

import java.util.ArrayList;
import java.util.Collections;

public class FuncDepSet {

	private ArrayList<FuncDep> set;
	
	//simple constructor
	public FuncDepSet() {
		set = new ArrayList<>(0);
	}
	
	//copy constructor
	
	public FuncDepSet(ArrayList<FuncDep> set) {
		this.set = new ArrayList<>(set);
	}
	
	public FuncDepSet(FuncDepSet f) {
		this.set = new ArrayList<>(f.getSet());
	}
	
	public void setSet(ArrayList<FuncDep> newset) {
		this.set = newset;
	}
	
	//getter for a specific FuncDep index
	public FuncDep getFuncDep(int i) {
		return set.get(i);
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
	
	//getter for size
	public int getSize() {
		return set.size();
	}
	
	
	public void printFuncDepName(int i) {
		FuncDep f = getFuncDep(i);
		print("FuncDepSet.getFuncDepName(): FuncDep at index " + i + ": ");
		String master = f.getMasterName();
		char slave = f.getSlaveName();
		println(master + "->" + slave);
	}
	
	//getter for a specific FuncDep index, stringified
	public String getFuncDepName(int i) {
		FuncDep f = getFuncDep(i);
		print("FuncDepSet.getFuncDepName(): FuncDep at index " + i + ": ");
		String result = f.stringify();
		println(result);
		return result;
	}
	
	//getter for the entire set
	public ArrayList<FuncDep> getSet() {
		return set;
	}
	
	//adds a FuncDep
	public void addFuncDep(String masterName, String slaveName) {
		println("FuncDepSet.addFuncDep():");
		masterName = simplify(masterName);
		slaveName = simplify(slaveName);
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
		set = omitDuplicates(this);
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
	
	public ArrayList<FuncDep> minCover() {
		FuncDepSet f = new FuncDepSet(set);
		//1. only single attributes on the right - dealt with
		//2. left-side args cannot be omitted
		//we can omit an argument, if:
		// - it can be implied by other left arguments thru FDs
		// - there is a simpler FD with the same right side
		int i = 0;
		while (i < f.getSize()) {
			FuncDep fd = f.getFuncDep(i);
			//if there is only a single attribute, we do nothing
			if (fd.getMasterName().length() > 1) {
				FuncDepSet shrinked = new FuncDepSet(f);
				//else we check for omission possibilities.
				//first we need to omit the actual funcdep.
				ArrayList<Integer> deleteable = new ArrayList<>(0);
				StringBuilder removeable = new StringBuilder("");
				for (int j = 0; j < shrinked.getSize(); j++) {
					if (fd.same(shrinked.getFuncDep(j)))
						deleteable.add(j);
				}
				Collections.sort(deleteable, Collections.reverseOrder());
				for (int index : deleteable)
					shrinked.removeFuncDep(index);
				//then we need to see if leftside attributes imply each other.
				//for this, we need to see all power sets of the leftside.
				ArrayList<String> powersets = formAll(fd.getMasterName());
				for (int j = 0; j < powersets.size(); j++) {
					String powerset = powersets.get(j);
					//compute closure of power set
					StringBuilder powersetclosure = new StringBuilder(closure(powerset));
					//remove powerset from closure
					//we compare each powerset letter with each closure letter
					for (int index = 0; index < powerset.length(); index++) {
						for (int k = 0; k < powersetclosure.toString().length(); k++) {
							if (powersetclosure.charAt(k) == powerset.charAt(index))
								powersetclosure.deleteCharAt(k);
						}
					}
					//for further polishing, we exclude the power set itself.
					//let's remove the powerset from the mastername
					StringBuilder chunk = new StringBuilder(fd.getMasterName());
					for (int x = 0; x < powerset.length(); x++) {
						for (int y = 0; y < chunk.length(); y++) {
							if (chunk.charAt(y) == powerset.charAt(x))
								chunk.deleteCharAt(y);
						}
					}
					System.out.print("powerset:" + powerset);
					System.out.print(", powersetclosure: " + powersetclosure);
					System.out.print(", chunk: " + chunk + "\n");
					//for powersetclosure:
					//if it contains chunk, then chunk is removeable.
					for (int x = 0; x < chunk.length(); x++) {
						char ch = chunk.charAt(x);
							int y = powersetclosure.toString().indexOf(ch);
							System.out.println(y);
							if (y > -1)
								removeable.append(ch);
					}
					//after we got this, we simplify and remove all masterelements
					String removeables = simplify(removeable.toString());
					System.out.println("removeables:" + removeables);
					for (int x = 0; x < removeables.length(); x++) {
						fd.removeMasterElement(removeables.charAt(x));
					}
					//now we see if there is any fd with the rightside
					//and if yes, actual contains its leftside
					for (int x = 0; x < shrinked.getSize(); x++) {
						FuncDep victim = shrinked.getFuncDep(x);
						if (fd.getSlaveName() == victim.getSlaveName()) {
							StringBuilder contains = new StringBuilder("");
							String leftmaster = fd.getMasterName();
							String rightmaster = victim.getMasterName();
							for (int li = 0; li < leftmaster.length(); li++) {
								for (int ri = 0; ri < rightmaster.length(); ri++)
									if (leftmaster.charAt(li) == rightmaster.charAt(ri))
										contains.append(leftmaster.charAt(li));
							}
							for (int index = 0; index < contains.toString().length(); index++)
								fd.removeMasterElement(contains.charAt(index));
						}
					}
				}
			}
			i++;
		}
		removeEmpty();
		i = 0;
		//3. deps cannot be omitted
		//FD can be omitted if its rightside is obtained thru other FDs
		ArrayList<Integer> removeable = new ArrayList<>(0);
		while (i < f.getSize()) {
			FuncDep fd = f.getFuncDep(i);
			char fdright = fd.getSlaveName();
			boolean toDelete = false;
			//we shrink
			FuncDepSet shrinked = new FuncDepSet(f.shrink(fd));
			//for shrinked's FD's, we compute the closure and if it contains,
			//we remove this FD
			for (int j = 0; j < shrinked.getSize(); j++) {
				String closure = shrinked.closure(fd.getMasterName());
				if (contains(closure, fdright+""))
					toDelete = true;					
			}
			if (toDelete)
				removeable.add(i);
			i++;
		}
		Collections.sort(removeable, Collections.reverseOrder());
		for (int index : removeable) {
			f.removeFuncDep(index);
		}
		f.writeSet();
		return f.getSet();
	}

	public boolean contains(String container, String query) {
		StringBuilder contains = new StringBuilder("");
		for (int li = 0; li < container.length(); li++) {
			for (int ri = 0; ri < query.length(); ri++)
				if (container.charAt(li) == query.charAt(ri))
					contains.append(container.charAt(li));
		}
		if (contains.toString().equals(query))
			return true;
		return false;
		
	}
	
	//omits the current FD from funcdepset and returns the shrinked set
	public FuncDepSet shrink(FuncDep fd) {
		FuncDepSet shrinked = new FuncDepSet(this);
		ArrayList<Integer> deleteable = new ArrayList<>(0);
		for (int j = 0; j < shrinked.getSize(); j++) {
			if (fd.same(shrinked.getFuncDep(j)))
				deleteable.add(j);
		}
		Collections.sort(deleteable, Collections.reverseOrder());
		for (int index : deleteable)
			shrinked.removeFuncDep(index);
		return shrinked;
	}
	
	//removes dependencies with empty attributes
	public void removeEmpty() {
		ArrayList<Integer> deleteable = new ArrayList<>(0);
		for (int i = 0; i < set.size(); i++) {
			System.out.println("remove");
			if (set.get(i).getMasterName().isEmpty()) {
				deleteable.add(i);
			}
		}
		Collections.sort(deleteable, Collections.reverseOrder());
		for (int index : deleteable) {
			set.remove(index);
		}
		writeSet();
	}

	public ArrayList<FuncDep> omitDuplicates(FuncDepSet f) {
		ArrayList<FuncDep> set = f.getSet();
		for (int i = 0; i < set.size(); i++) {
			FuncDep actual = set.get(i);
			ArrayList<Integer> deleteable = new ArrayList<>(0);
			for (int j = 0; j < set.size(); j++) {
				FuncDep victim = set.get(j);
				if ((i != j) 
					&& (actual.same(victim))) {
					deleteable.add(j);
				}
			}
			Collections.sort(deleteable, Collections.reverseOrder());
			for (Integer item : deleteable) {
				f.removeFuncDep(item);
			}
		}
		return f.getSet();
	}

	public String closure(String attrs) {
		//copy all funcdeps
		ArrayList<FuncDep> remaining = new ArrayList<FuncDep>(this.set);
		String query = "";
		if (attrs.isEmpty())
			query = remaining.get(0).getMasterName();
		else
			query = attrs;
		String closure;			
		while (!remaining.isEmpty()) {
			//first we simplify closure
			query = simplify(query.toString()).toUpperCase();
			ArrayList<String> candidates = formAll(query);
			//System.out.println(candidates.toString());
			String newAttrs = "";
			ArrayList<Integer> deleteable = new ArrayList<>(0);
			
			//now we check all dependencies if their leftside equals to anything in candidate
			//if it does, we append it to newattrs, then exclude from remaining;
			
			for (int i = 0; i < remaining.size(); i++) {
				StringBuilder na = new StringBuilder("");
				String left = simplify(remaining.get(i).getMasterName());
				char right = remaining.get(i).getSlaveName();
				for (int j = 0; j < candidates.size(); j++) {
					//System.out.println("left: " + left + ", right: " + right);
					String candidate = candidates.get(j);
					if (candidate.equals(left)) {
						//System.out.println("candidate: " + candidate + ", left: " + left);
						na.append(right);
						//System.out.println("new attributes: " + na);
					}
				}
				if (!na.toString().equals(""))
					deleteable.add(i);
				//System.out.println(simplify(na.toString()));
				newAttrs = newAttrs + simplify(na.toString());
			}
			if (newAttrs.equals(""))
				break;
			//we remove all dependencies that fit query
			Collections.sort(deleteable, Collections.reverseOrder());
			for (Integer item : deleteable) {
				remaining.remove(remaining.get(item));
			}
			//finally we update
			query = simplify(query + newAttrs);
		}
		closure = simplify(query).toUpperCase();
		return closure;
	}
	
	
	
	private ArrayList<String> formAll(String query) {
		ArrayList<String> candidates = new ArrayList<>(0);
		ArrayList<String> bitsample = new ArrayList<>(0);
		for (int i = 0; i < Math.pow(2, query.length()); i++) {
			String s = Integer.toBinaryString(i);
			String leaderzeros = "";
			for (int j = 0; j < (query.length() - s.length()); j++) {
				leaderzeros = leaderzeros + "0";
			}
			s = leaderzeros + s;
			bitsample.add(s);
		}
		
		for (String item : bitsample) {
			StringBuilder s = new StringBuilder("");
			for (int i = 0; i < query.length(); i++) {
				if (item.charAt(i) == '1')
					s.append(query.charAt(i));
			}
			if (!s.toString().equals(""))
				candidates.add(s.toString());
		}
		return candidates;
	}

	//function to remove duplicates and ascend String
	private String simplify(String query) {
		ArrayList<Character> c = new ArrayList<Character>(0);
		for (int i = 0; i < query.length(); i++) {
			boolean isFound = false;
			for (int j = 0; j < c.size(); j++) {
				if (c.get(j) == query.charAt(i))
					isFound = true;
			}
			if (!isFound)
				c.add(query.charAt(i));
		}
		//Next we sort the characters in ascending order.
		Collections.sort(c);
		StringBuilder s = new StringBuilder();
		for (Character item : c)
			s.append(item);
		return s.toString();
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
