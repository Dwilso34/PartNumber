package binaparts.main;

import binaparts.gui.*;
//version 1.0.0.0
public class RunProgram{
	
	static final String configFilePath = "config.properties";

	public static void main(String[] args){		
		
		MainFrames m = new MainFrames();
		m.displayGUI();
	}
}		