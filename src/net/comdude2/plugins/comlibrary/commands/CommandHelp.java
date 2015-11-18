package net.comdude2.plugins.comlibrary.commands;

import java.util.LinkedList;

public class CommandHelp {
	
	private LinkedList <AbstractCommand> commands = new LinkedList <AbstractCommand> ();
	
	//Class is designed to offer an easy way to display help for commands in your plugin.
	public CommandHelp(){
		
	}
	
	public void registerCommand(AbstractCommand command){
		
	}
	
	public boolean unregisterCommand(String commandName){
		return false;
	}
	
	
	
}
