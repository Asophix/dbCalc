package dbCalc.View;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import dbCalc.Model.Database;

public class Window extends JFrame {

	private static final long serialVersionUID = 1485565230491815565L;
	final double versionNumber = 0.61;
	final String title = "dbCalc - Functional Dependency Calculator v" + versionNumber;
	public static InputPanel iPanel; //generic input container
	public static FDPanel fdPanel; //FD-container
	public static KeyPanel keyPanel; //LRBN and Key evaluator panel
	public static LogoPanel logoPanel; //welcome text/image pane
	public static StatusPanel statusPanel; //status bar
	MenuBar menuBar; //menu bar
	
	JPanel bigPanel;
	
	int width = 640;
	int height = 480;
	
	public static Database f;
	
	public Window(Database f) throws IOException {
		Window.f = f;
		//--- set window-specific attributes 
		this.setLayout(null);
		this.setTitle(title);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(640, 480));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//--- instantiate container panel
		bigPanel = new JPanel();
		this.add(bigPanel);
		bigPanel.setLayout(null);
		bigPanel.setBounds(0, 0, 640, 480);
		//--- instantiate menu bar
		menuBar = new MenuBar();
		menuBar.setBounds(0, 0, 640, 20);
		bigPanel.add(menuBar);
		//--- instantiate subpanels
		statusPanel = new StatusPanel();
		statusPanel.setBounds(0, 20, 640, 20);
		bigPanel.add(statusPanel);
		//---
		iPanel = new InputPanel();
		iPanel.setBounds(0, 40, 160, 240);
		bigPanel.add(iPanel);
		//---
		fdPanel = new FDPanel();
		fdPanel.setBounds(160, 40, 240, 240);
		bigPanel.add(fdPanel);
		//---
		keyPanel = new KeyPanel();
		keyPanel.setBounds(400, 40, 240, 240);
		bigPanel.add(keyPanel);
		//---
		logoPanel = new LogoPanel();
		logoPanel.setBounds(495, 390, 142, 63);
		bigPanel.add(logoPanel);
		//---
		this.setVisible(true);
		this.pack();
	}
	
	
}