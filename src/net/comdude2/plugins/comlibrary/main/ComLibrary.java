package net.comdude2.plugins.comlibrary.main;

import org.bukkit.plugin.java.JavaPlugin;

public class ComLibrary extends JavaPlugin{
	
	public ComLibrary(){
		
	}
	
	public void onEnable(){
		this.getLogger().info(this.getDescription().getName() + " V" + this.getDescription().getVersion() + " is now Enabled!");
	}
	
	public void onDisable(){
		this.getLogger().info(this.getDescription().getName() + " V" + this.getDescription().getVersion() + " is now Disabled!");
	}
	
}
