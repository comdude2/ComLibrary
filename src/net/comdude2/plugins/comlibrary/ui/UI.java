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

package net.comdude2.plugins.comlibrary.ui;

import org.bukkit.plugin.Plugin;

public class UI {
	
	private long id = -1L;
	private IconMenu menu = null;
	
	public UI(long id, String title, int size, IconMenu.OptionClickEventHandler handler, Plugin plugin){
		this.id = id;
		menu = new IconMenu(title, size, handler, plugin);
	}
	
	public long getId(){
		return this.id;
	}
	
	public IconMenu getMenu(){
		return this.menu;
	}
	
	public void create(String title, int size, IconMenu.OptionClickEventHandler handler, Plugin plugin){
		menu = new IconMenu(title, size, handler, plugin);
	}
	
	public void destroy(){
		menu.destroy();
		menu.unregisterEvents();
	}
	
}
