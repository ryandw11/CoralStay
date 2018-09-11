package me.ryandw11.coralstay.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ryandw11.coralstay.CoralStay;
import me.ryandw11.coralstay.api.CoralBlock;

public class CoralInspect implements CommandExecutor {
	
	private CoralStay plugin;
	public CoralInspect(CoralStay plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command is for players only!");
			return true;
		}
		Player p = (Player) sender;
		if(!p.hasPermission("coralstay.coralinspect")) {
			p.sendMessage(ChatColor.RED + "You do not have permission for this command!");
			return true;
		}
		
		Block b = p.getTargetBlock(null, 200);
		
		if(!plugin.blocks.contains(b.getType())) {
			p.sendMessage(ChatColor.RED + "You must be looking at a coral block!");
			return true;
		}
		
		if(plugin.data.contains(plugin.getLocationCypher().getStringFromLocation(b.getLocation()))) {
			CoralBlock cb = new CoralBlock(plugin, b.getLocation());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b=======[&aCoral Information&b]======="));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bPlaced By:&a " + cb.getOwnerOfflinePlayer().getName()));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMaterial:&a " + cb.getMaterial().toString()));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bPath:&a " + cb.getPath()));
		}else {
			p.sendMessage(ChatColor.RED + "No data exists for that block!");
		}
		
		return false;
	}

}
