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
