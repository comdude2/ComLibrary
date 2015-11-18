package net.comdude2.plugins.comlibrary.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandHelp {
	
	private String pluginName = null;
	private String pluginVersion = null;
	private List<String> pluginAuthors = new LinkedList <String> ();
	private LinkedList <AbstractCommand> commands = new LinkedList <AbstractCommand> ();
	
	//Class is designed to offer an easy way to display help for commands in your plugin.
	public CommandHelp(Plugin plugin){
		this.pluginName = plugin.getName();
		this.pluginVersion = plugin.getDescription().getVersion();
		this.pluginAuthors = plugin.getDescription().getAuthors();
	}
	
	public boolean registerCommand(AbstractCommand command){
		if (getCommand(command.getName()) == null){
			commands.add(command);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean unregisterCommand(String commandName){
		return false;
	}
	
	public AbstractCommand getCommand(String commandName){
		for (AbstractCommand command : commands){
			if (command.getName().equalsIgnoreCase(commandName)){
				return command;
			}
		}
		return null;
	}
	
	public void displayHelp(CommandSender sender, String command, String[] args){
		
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
			s.replace("%%PLUGIN_NAME%%", this.pluginName);
		}
		if(s.contains("%%NAME%%")){
			s.replace("%%NAME%%", sender.getName());
		}
		if(s.contains("%%VERSION%%")){
			s.replace("%%VERSION%%", this.pluginVersion);
		}
		if(s.contains("%%AUTHORS%%")){
			String authors = "";
			for (String a : this.pluginAuthors){
				if (authors == ""){
					authors = authors + a;
				}else{
					authors = authors + ", " + a;
				}
			}
			s.replace("%%AUTHORS%%", authors);
		}
		return s;
	}
	
}
