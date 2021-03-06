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

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class IconMenu implements Listener{
	
	private String name = null;
    private int size = 54;
    private OptionClickEventHandler handler = null;
    private Plugin plugin = null;
   
    private String[] optionNames = null;
    private ItemStack[] optionIcons = null;
    
    
    //To implement this:
    //IconMenu menu = new IconMenu("My Fancy Menu", 9, new IconMenu.OptionClickEventHandler() {
    //@Override
    //public void onOptionClick(IconMenu.OptionClickEvent event) {
        //event.getPlayer().sendMessage("You have chosen " + event.getName());
        //event.setWillClose(true);
        //event.setWillDestroy(true);
    //}
    //}, plugin)
    //.setOption(3, new ItemStack(Material.APPLE, 1), "Food", "The food is delicious")
    //.setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Weapon", "Weapons are for awesome people")
    //.setOption(5, new ItemStack(Material.EMERALD, 1), "Money", "Money brings happiness");
    
   
    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * Unregister OptionClickEvent from handler list.
     */
    public void unregisterEvents(){
    	HandlerList.unregisterAll(this);
    }
   
    /**
     * Set / Add an option to the menu.
     * @param int position
     * @param ItemStack icon
     * @param String name
     * @param String... info
     * @return IconMenu
     */
    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
    
    /**
     * Clear all options.
     */
    public void clear(){
    	this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
    }
    
    /**
     * Get the options in the menu.
     * @return String[]
     */
    public String[] getOptionNames(){
    	return optionNames;
    }
    
    /**
     * Get the icons for the options in the menu.
     * @return ItemStack[]
     */
    public ItemStack[] getOptionIcons(){
    	return optionIcons;
    }
    
    /**
     * Get the size of the menu.
     * @return int
     */
    public int getMenuSize(){
    	return size;
    }
   
    /**
     * Open the menu in a players inventory.
     * @param Player player
     */
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
   
    /**
     * Destroy the inventory.
     */
    public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }
   
    /**
     * Listen for inventory clicks
     * @param InventoryClickEvent event
     */
    @EventHandler(priority=EventPriority.MONITOR)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                Plugin plugin = this.plugin;
                OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);
                if (e.willClose()) {
                    final Player p = (Player)event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if (e.willDestroy()) {
                    destroy();
                }
            }
        }
    }
   
    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);      
    }
   
    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
       
        public OptionClickEvent(Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }
       
        public Player getPlayer() {
            return player;
        }
       
        public int getPosition() {
            return position;
        }
       
        public String getName() {
            return name;
        }
       
        public boolean willClose() {
            return close;
        }
       
        public boolean willDestroy() {
            return destroy;
        }
       
        public void setWillClose(boolean close) {
            this.close = close;
        }
       
        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
   
    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
            im.setDisplayName(name);
            im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }
}
