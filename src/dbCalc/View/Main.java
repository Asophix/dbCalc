package dbCalc.View;

import dbCalc.Model.Database;

//import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Database f = new Database();
		Window w = new Window(f);
		System.out.println(w.getName());
	}

}
