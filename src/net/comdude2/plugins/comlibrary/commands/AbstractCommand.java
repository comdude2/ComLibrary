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
