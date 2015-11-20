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

package net.comdude2.plugins.comlibrary.util;

import java.io.Serializable;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messenger implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8652739024601225294L;
	private String prefix = null;
	private String suffix = null;
	
	public Messenger(String prefix, String suffix){
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	public String getPrefix(){
		return this.prefix;
	}
	
	public String getSuffix(){
		return this.suffix;
	}
	
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
	
	public void setSuffix(String suffix){
		this.suffix = suffix;
	}
	
	//Main methods
	
	/**
	 * Message a CommandSender a message, includes prefix and suffix.
	 * @param CommandSender sender
	 * @param String message
	 */
	public void message(CommandSender sender, String message){
		if (sender != null){
			String pre = stripNull(prefix);
			String suf = stripNull(suffix);
			sender.sendMessage(pre + message + suf);
		}
	}
	
	/**
	 * Message a Player a message, includes prefix and suffix.
	 * @param Player player
	 * @param String message
	 */
	public void message(Player player, String message){
		if (player != null){
			String pre = stripNull(prefix);
			String suf = stripNull(suffix);
			player.sendMessage(pre + message + suf);
		}
	}
	
	/**
	 * Message a CommandSender a message, without prefix and suffix.
	 * @param CommandSender sender
	 * @param String message
	 */
	public void messageWithoutExtras(CommandSender sender, String message){
		if (sender != null){
			sender.sendMessage(message);
		}
	}
	
	/**
	 * Message a Player a message, without prefix and suffix.
	 * @param Player player
	 * @param String message
	 */
	public void messageWithoutExtras(Player player, String message){
		if (player != null){
			player.sendMessage(message);
		}
	}
	
	/**
	 * If a string is null it returns an empty string to ensure the string doesn't contain "NULL".
	 * @param String s
	 * @return String
	 */
	private String stripNull(String s){
		if (s == null){
			return "";
		}else{
			return s;
		}
	}
	
}
