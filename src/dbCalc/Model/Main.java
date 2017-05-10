package dbCalc.Model;

import dbCalc.View.Window;

//import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Database f = new Database();
		Window w = new Window(f);
		System.out.println(w.getName());
	}

}
