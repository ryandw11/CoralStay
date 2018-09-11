package me.ryandw11.coralstay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.ryandw11.coralstay.commands.CoralInspect;
import me.ryandw11.coralstay.listeners.CoralListener;
import me.ryandw11.coralstay.listeners.OnBlockBreak;
import me.ryandw11.coralstay.listeners.OnBlockPlace;
import me.ryandw11.coralstay.listeners.RestrictedCoralListener;
import me.ryandw11.coralstay.util.LocationCypher;

public class CoralStay extends JavaPlugin implements Listener{
	
	public List<Material> blocks = new ArrayList<>();
	public File datafile = new File(getDataFolder() + "/data/coraldata.yml");
	public FileConfiguration data = YamlConfiguration.loadConfiguration(datafile);
	private static CoralStay plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
		saveConfig();
		registerEvents();
		loadAliveBlocks();
		loadRecipe();
		loadFile();
	}
	
	@Override
	public void onDisable() {
		saveFile();
	}
	
	private void registerEvents() {
		if(!getConfig().getBoolean("Restricted_Access.Enabled")) {
			Bukkit.getServer().getPluginManager().registerEvents(new CoralListener(this), this);
		}else {
			Bukkit.getServer().getPluginManager().registerEvents(new RestrictedCoralListener(this), this);
			Bukkit.getServer().getPluginManager().registerEvents(new OnBlockPlace(this), this);
			Bukkit.getServer().getPluginManager().registerEvents(new OnBlockBreak(this), this);
		}
		
		getCommand("coralinspect").setExecutor(new CoralInspect(this));
		
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
	
	private void loadRecipe() {
		if(getConfig().getBoolean("Survival_Furnace_Recipe")) {
			for(Material m : blocks) {
				ItemStack result = new ItemStack(Material.valueOf("DEAD_" + m.toString()), getConfig().getInt("Dead_Coral_Amount"));
			    FurnaceRecipe fr = new FurnaceRecipe(NamespacedKey.minecraft("coralstay_" + m.toString().toLowerCase()), result, m, 1, getConfig().getInt("Cook_Time"));
			    Bukkit.addRecipe(fr);
			}
		}
	}
	
	/**
	 * Get the Location Cypher
	 * @return LocationCypher
	 */
	public LocationCypher getLocationCypher() {
		return new LocationCypher();
	}
	
	 public void saveConfig() {
		saveDefaultConfig();
	}
	 
	 /**
	     * Save the data file.
	     */
	    public void saveFile(){
			
			try{
				data.save(datafile);
			}catch(IOException e){
				e.printStackTrace();
				
			}
			
		}
		/**
		 * load the data file
		 */
		public void loadFile(){
			if(datafile.exists()){
				try {
					data.load(datafile);
					
				} catch (IOException | InvalidConfigurationException e) {

					e.printStackTrace();
				}
			}
			else{
				try {
					data.save(datafile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static CoralStay getInstance() {
			return plugin;
		}
	
}
