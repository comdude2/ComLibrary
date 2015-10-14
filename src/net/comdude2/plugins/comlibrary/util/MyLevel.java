package net.comdude2.plugins.comlibrary.util;

import java.util.logging.Level;

public class MyLevel extends Level{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4469906525478261643L;
	public static final Level Error = new MyLevel("ERROR", Level.SEVERE.intValue() + 1);
	public static final Level Debug = new MyLevel("DEBUG", Level.INFO.intValue() + 1);
	
	public MyLevel(String name, int value){
		super(name, value);
	}
	
}
