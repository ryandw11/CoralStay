package me.ryandw11.coralstay.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationCypher {
	public String getStringFromLocation(Location loc) {
		return loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ();
	}
	
	public Location getLocationFromString(String s) {
		String[] sp = s.split(";");
		return new Location(Bukkit.getWorld(sp[0]), Integer.parseInt(sp[1]), Integer.parseInt(sp[2]), Integer.parseInt(sp[3]));
	}
}
