/*
ComLibrary - A library plugin for Minecraft
Copyright (C) 2015  comdude2 (Matt Armer)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact: admin@mcviral.net
*/

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
	
	/**
	 * Register a command.
	 * @param AbstractCommand command
	 * @return boolean
	 */
	public boolean registerCommand(AbstractCommand command){
		if (getCommand(command.getName()) == null && getCommand(command.getId()) == null){
			commands.add(command);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Unregister a command.
	 * @param String commandName
	 * @return boolean
	 */
	public boolean unregisterCommand(String commandName){
		AbstractCommand cmd = getCommand(commandName);
		if (cmd != null){
			commands.remove(cmd);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Get a command with the specified command name.
	 * @param String commandName
	 * @return AbstractCommand
	 */
	public AbstractCommand getCommand(String commandName){
		for (AbstractCommand command : commands){
			if (command.getName().equalsIgnoreCase(commandName)){
				return command;
			}
		}
		return null;
	}
	
	/**
	 * Get a command with the specified id.
	 * @param long id
	 * @return AbstractCommand
	 */
	public AbstractCommand getCommand(long id){
		for (AbstractCommand command : commands){
			if (command.getId() == id){
				return command;
			}
		}
		return null;
	}
	
	/**
	 * Display help based on the command and arguments.
	 * @param CommandSender sender
	 * @param String command
	 * @param String[] args
	 */
	
	//TODO THIS IS NOT FINISHED, IT DOESN'T TAKE INTO ACCOUNT THE ARGS.
	public void displayHelp(CommandSender sender, String command, String[] args){
		AbstractCommand cmd = getCommand(command);
		if (cmd != null){
			printHelp(sender, cmd);
		}
	}
	
	/**
	 * Display help based on the command and arguments.
	 * @param CommandSender sender
	 * @param String id
	 * @param String[] args
	 */
	
	//TODO THIS IS NOT FINISHED, IT DOESN'T TAKE INTO ACCOUNT THE ARGS.
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
			s.replace("%%AUTHORS%%", String.join(", ", this.plugin.getDescription().getAuthors()));
		}
		if (s.contains("%%DESCRIPTION%%")){
			s.replace("%%DESCRIPTION%%", this.plugin.getDescription().getDescription());
		}
		return s;
	}
	
	/**
	 * Generate a new Id for use in an AbstractCommand.
	 * @return long
	 */
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
