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
	
	public String getName(){
		return this.name;
	}
	
	public long getId(){
		return this.id;
	}
	
	public LinkedList <String> getResponse(){
		return this.response;
	}
	
}
