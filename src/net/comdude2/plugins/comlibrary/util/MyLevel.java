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
