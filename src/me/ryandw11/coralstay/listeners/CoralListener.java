package me.ryandw11.coralstay.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

import me.ryandw11.coralstay.CoralStay;

public class CoralListener implements Listener {
	
	private CoralStay plugin;
	public CoralListener(CoralStay plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void blockChange(BlockFadeEvent e){
		if(plugin.getConfig().getBoolean("World_Whitelist_Enabled")) {
			if(plugin.getConfig().getStringList("World_Whitelist").contains(e.getBlock().getWorld().getName())) {
				if(plugin.blocks.contains(e.getBlock().getType())) {
					e.setCancelled(true);
				}
			}
		}else {
			if(plugin.blocks.contains(e.getBlock().getType())) {
				e.setCancelled(true);
			}
		}
		
	}
}
