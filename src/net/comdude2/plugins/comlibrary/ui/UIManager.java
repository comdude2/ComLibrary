package net.comdude2.plugins.comlibrary.ui;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.plugin.Plugin;

public class UIManager {
	
	//This class and UI class are provided as an off the shelf management system for IconMenu, they are not required.
	
	private Plugin plugin = null;
	private LinkedList <UI> userInterfaces = new LinkedList <UI> ();
	private ConcurrentLinkedQueue <Long> registeredInterfaceIds = new ConcurrentLinkedQueue <Long> ();
	
	public UIManager(Plugin plugin){
		this.plugin = plugin;
	}
	
	public UI createUI(String title, int size, IconMenu.OptionClickEventHandler handler){
		long id = -1L;
		id = requestId();
		UI ui = new UI(id, title, size, handler, plugin);
		return ui;
	}
	
	public UI getUI(long id){
		for (UI ui : this.userInterfaces){
			if (ui.getId() == id){
				return ui;
			}
		}
		return null;
	}
	
	public boolean isRegistered(long id){
		return this.registeredInterfaceIds.contains(id);
	}
	
	public boolean destroyUI(long id){
		UI ui = getUI(id);
		if (ui != null){
			ui.destroy();
			this.registeredInterfaceIds.remove(ui.getId());
			this.userInterfaces.remove(ui);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean destroyUI(UI ui){
		if (ui != null){
			ui.destroy();
			this.registeredInterfaceIds.remove(ui.getId());
			this.userInterfaces.remove(ui);
			return true;
		}else{
			return false;
		}
	}
	
	public LinkedList <UI> getInterfaces(){
		return this.userInterfaces;
	}
	
	private long requestId(){
		Random r = new Random();
		long selected = -1L;
		while (selected == -1L){
			selected = r.nextLong();
			if (registeredInterfaceIds.contains(selected)){
				selected = -1L;
			}
		}
		this.registeredInterfaceIds.add(selected);
		return selected;
	}
	
}
