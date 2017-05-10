package dbCalc.Model;

import java.util.ArrayList;
import java.util.Collections;

import dbCalc.Model.Content.State;

public class Helper {
	
	//computes the minimal cover of given FuncDepSet
	public static ArrayList<FuncDep> minCover(ArrayList<FuncDep> s) {
		ArrayList<FuncDep> f = new ArrayList<>(s);
		//1. only single attributes on the right - dealt with
		//2. left-side args cannot be omitted
		//we can omit an argument, if:
		// - it can be implied by other left arguments thru FDs
		// - there is a simpler FD with the same right side
		int i = 0;
		while (i < f.size()) {
			FuncDep fd = f.get(i);
			//if there is only a single attribute, we do nothing
			if (fd.getMasterName().length() > 1) {
				ArrayList<FuncDep> shrinked = new ArrayList<>(f);
				//else we check for omission possibilities.
				//first we need to omit the actual funcdep.
				ArrayList<Integer> deleteable = new ArrayList<>(0);
				StringBuilder removeable = new StringBuilder("");
				for (int j = 0; j < shrinked.size(); j++) {
					if (fd.same(shrinked.get(j)))
						deleteable.add(j);
				}
				Collections.sort(deleteable, Collections.reverseOrder());
				for (int index : deleteable)
					shrinked.remove(index);
				//then we need to see if leftside attributes imply each other.
				//for this, we need to see all power sets of the leftside.
				ArrayList<String> powersets = formAll(fd.getMasterName());
				for (int j = 0; j < powersets.size(); j++) {
					String powerset = powersets.get(j);
					//compute closure of power set
					StringBuilder powersetclosure = new StringBuilder(closure(shrinked, powerset));
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
					for (int x = 0; x < shrinked.size(); x++) {
						FuncDep victim = shrinked.get(x);
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
		f = removeEmpty(f);
		i = 0;
		//3. deps cannot be omitted
		//FD can be omitted if its rightside is obtained thru other FDs
		ArrayList<Integer> removeable = new ArrayList<>(0);
		while (i < f.size()) {
			FuncDep fd = f.get(i);
			char fdright = fd.getSlaveName();
			boolean toDelete = false;
			//we shrink
			ArrayList<FuncDep> shrinked = new ArrayList<>(shrink(f, fd));
			//for shrinked's FD's, we compute the closure and if it contains,
			//we remove this FD
			for (int j = 0; j < shrinked.size(); j++) {
				String closure = closure(shrinked, fd.getMasterName());
				if (contains(closure, fdright+""))
					toDelete = true;					
			}
			if (toDelete)
				removeable.add(i);
			i++;
		}
		Collections.sort(removeable, Collections.reverseOrder());
		for (int index : removeable) {
			f.remove(index);
		}
		f = removeEmpty(f);
		return f;
	}
	
	//omits the current FD from funcdepset and returns the shrinked set
	public static ArrayList<FuncDep> shrink(ArrayList<FuncDep> set, FuncDep fd) {
		ArrayList<FuncDep> shrinked = new ArrayList<>(set);
		ArrayList<Integer> deleteable = new ArrayList<>(0);
		for (int j = 0; j < shrinked.size(); j++) {
			if (fd.same(shrinked.get(j)))
				deleteable.add(j);
		}
		Collections.sort(deleteable, Collections.reverseOrder());
		for (int index : deleteable)
			shrinked.remove(index);
		return shrinked;
	}
		
	//computes an attribute set's closure
	public static String closure(ArrayList<FuncDep> set, String attrs) {
		//copy all funcdeps
		ArrayList<FuncDep> remaining = new ArrayList<>(set);
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
		
	//helper function that creates all power sets for a set
	public static ArrayList<String> formAll(String query) {
		ArrayList<String> candidates = new ArrayList<>(0);
		ArrayList<String> bitsample = new ArrayList<>(0);
		//we form all bitcodes (2 ^ <query.length> pieces) 
		for (int i = 0; i < Math.pow(2, query.length()); i++) {
			String s = Integer.toBinaryString(i);
			String leaderzeros = "";
			for (int j = 0; j < (query.length() - s.length()); j++) {
				leaderzeros = leaderzeros + "0";
			}
			s = leaderzeros + s;
			bitsample.add(s);
		}
		//then we mask each bitsample to create attribute combinations
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
	
	//function to remove duplicates and ascend attributes in a new String
	public static String simplify(String query) {
		ArrayList<Character> c = new ArrayList<Character>(0);
		//we collect all occurring characters' indices
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
	
	//removes dependencies with empty attributes
	public static ArrayList<FuncDep> removeEmpty(ArrayList<FuncDep> s) {
		ArrayList<FuncDep> set = new ArrayList<>(s);
		ArrayList<Integer> deleteable = new ArrayList<>(0);
		for (int i = 0; i < s.size(); i++) {
			System.out.println("remove");
			if (set.get(i).getMasterName().isEmpty()) {
				deleteable.add(i);
			}
		}
		Collections.sort(deleteable, Collections.reverseOrder());
		for (int index : deleteable) {
			set.remove(index);
		}
		return set;
	}
	
	public static boolean contains(String container, String query) {
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
	
	//removes duplicate FD's from parameter set
	public static ArrayList<FuncDep> omitDuplicates(Database f) {
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
	
	//finds all attributes in a funcdepset
	public static String getAttributes(ArrayList<FuncDep> f) {
		StringBuilder result = new StringBuilder("");
		for (FuncDep fd : f) {
			result.append(fd.getMasterName() + fd.getSlaveName());
		}
		return simplify(result.toString());
	}
	
	public static String removeString(String container, String query) {
		StringBuilder cont = new StringBuilder(container);
		ArrayList<Integer> deleteable = new ArrayList<>(0); 
		for (int i = 0; i < cont.toString().length(); i++) {
			boolean contains = false;
			for (int j = 0; j < query.length(); j++) {
				if ((cont.charAt(i) == query.charAt(j)) && (!contains)) {
					deleteable.add(i);
					contains = true;
				}
			}
		}
		Collections.sort(deleteable, Collections.reverseOrder());
		for (int index : deleteable)
			cont.deleteCharAt(index);
		return cont.toString();
	}
	
	//creates a particular side of attributes in funcdepset
	public static String createSide(ArrayList<FuncDep> f, String attrs, State s) {
		String existing = getAttributes(f);
		if (s == State.NONE) {
			existing = removeString(attrs, existing);
			return existing;
		}
		else {
			String left = "";
			String right = "";
			for (FuncDep fd : f) {
				left = left + fd.getMasterName();
				right = right + fd.getSlaveName();
			}
			left = simplify(left);
			right = simplify(right);
			if (s == State.LEFT)
				return removeString(left, right);
			else if (s == State.RIGHT)
				return removeString(right, left);
		}
		return "Something went wrong in Helper.createSide().";
	}

}
