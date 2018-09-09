package me.ryandw11.coralstay;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.ryandw11.coralstay.listeners.CoralListener;

public class CoralStay extends JavaPlugin implements Listener{
	
	public List<Material> blocks = new ArrayList<>();
	
	@Override
	public void onEnable(){
		saveConfig();
		Bukkit.getServer().getPluginManager().registerEvents(new CoralListener(this), this);
		loadAliveBlocks();
	}
	
	private void loadAliveBlocks() {
		for(String s : getConfig().getStringList("stay_alive_coral")) {
			try {
				blocks.add(Material.valueOf(s.toUpperCase()));
			}catch(IllegalArgumentException e) {
				getLogger().info("The block " + s + " does not exist!");
				continue;
			}
		}
	}
	
	 public void saveConfig() {
		saveDefaultConfig();
	}
	
	
}
