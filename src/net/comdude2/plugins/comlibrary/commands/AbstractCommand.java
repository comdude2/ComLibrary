package net.comdude2.plugins.comlibrary.commands;

import java.util.LinkedList;

public class AbstractCommand {
	
	private String name = null;
	private long id = -1L;
	private LinkedList <String> response = new LinkedList <String> ();
	
	public AbstractCommand(String name, long id){
		this.name = name;
		this.id = id;
	}
	
	public AbstractCommand(String name, long id, LinkedList <String> response){
		this.name = name;
		this.id = id;
		this.response = response;
	}
	
	/**
	 * Returns the primary name of this command.
	 * @return String
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns this command's unique ID.
	 * @return long
	 */
	public long getId(){
		return this.id;
	}
	
	/**
	 * Returns the lines of help allocated to this command pattern.
	 * @return LinkedList <String>
	 */
	public LinkedList <String> getResponse(){
		return this.response;
	}
	
}
