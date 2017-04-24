package dbCalc;

public class Attribute {

	private char name; //attribute name, preferably a single letter
	
	//constructor with name and primary key toggle
	public Attribute(char name) {
		this.name = name;
	}
	
	public Attribute(Attribute attr) {
		this.name = attr.getName();
	}

	//general getter-setter functions
	public char getName() {
		return name;
	}
	
	public void setName(char val) {
		name = val;
	}

}
