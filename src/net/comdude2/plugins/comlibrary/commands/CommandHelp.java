package net.comdude2.plugins.comlibrary.commands;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandHelp {
	
	private Plugin plugin = null;
	private LinkedList <AbstractCommand> commands = new LinkedList <AbstractCommand> ();
	
	//Class is designed to offer an easy way to display help for commands in your plugin.
	public CommandHelp(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean registerCommand(AbstractCommand command){
		if (getCommand(command.getName()) == null && getCommand(command.getId()) == null){
			commands.add(command);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean unregisterCommand(String commandName){
		AbstractCommand cmd = getCommand(commandName);
		if (cmd != null){
			commands.remove(cmd);
			return true;
		}else{
			return false;
		}
	}
	
	public AbstractCommand getCommand(String commandName){
		for (AbstractCommand command : commands){
			if (command.getName().equalsIgnoreCase(commandName)){
				return command;
			}
		}
		return null;
	}
	
	public AbstractCommand getCommand(long id){
		for (AbstractCommand command : commands){
			if (command.getId() == id){
				return command;
			}
		}
		return null;
	}
	
	public void displayHelp(CommandSender sender, String command, String[] args){
		AbstractCommand cmd = getCommand(command);
		if (cmd != null){
			printHelp(sender, cmd);
		}
	}
	
	public void displayHelp(CommandSender sender, long id, String[] args){
		AbstractCommand cmd = getCommand(id);
		if (cmd != null){
			printHelp(sender, cmd);
		}
	}
	
	private void printHelp(CommandSender sender, AbstractCommand cmd){
		if (cmd != null){
			if (cmd.getResponse() != null){
				for (String s : cmd.getResponse()){
					sender.sendMessage(formatResponse(s, sender));
				}
			}
		}
	}
	
	private String formatResponse(String s, CommandSender sender){
		if(s.contains("%%PLUGIN_NAME%%")){
			s.replace("%%PLUGIN_NAME%%", this.plugin.getName());
		}
		if(s.contains("%%NAME%%")){
			s.replace("%%NAME%%", sender.getName());
		}
		if(s.contains("%%VERSION%%")){
			s.replace("%%VERSION%%", this.plugin.getDescription().getVersion());
		}
		if(s.contains("%%AUTHORS%%")) {
			s.replace("%%AUTHORS%%", String.join(",", this.plugin.getDescription().getAuthors()));
		}
		if (s.contains("%%DESCRIPTION%%")){
			s.replace("%%DESCRIPTION%%", this.plugin.getDescription().getDescription());
		}
		return s;
	}
	
	public long generateNewId(){
		Random r = new Random();
		long chosen = 0L;
		boolean used = false;
		while(chosen == 0L || used == true){
			chosen = r.nextLong();
			if (getCommand(chosen) == null){
				used = false;
			}else{
				used = true;
			}
		}
		return chosen;
	}
	
}
