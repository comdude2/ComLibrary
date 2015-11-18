package net.comdude2.plugins.comlibrary.commands;

import java.util.HashMap;
import java.util.LinkedList;

public class AbstractCommand {
	
	private String name = null;
	private String root = null;
	private HashMap <String, LinkedList <String>> patterns = new HashMap <String, LinkedList <String>> ();
	
	public AbstractCommand(String name, String root){
		this.name = name;
		this.root = root;
	}
	
	public AbstractCommand(String name, String root, HashMap <String, LinkedList <String>> patterns){
		this.name = name;
		this.root = root;
		this.patterns = patterns;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getRoot(){
		return this.root;
	}
	
	public void registerPattern(String pattern, LinkedList <String> helpLines){
		patterns.put(pattern, helpLines);
	}
	
	public void unregisterPattern(){
		
	}
	
}
