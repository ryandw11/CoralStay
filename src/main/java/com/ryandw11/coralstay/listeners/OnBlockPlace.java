package com.ryandw11.coralstay.listeners;

import com.ryandw11.coralstay.CoralStay;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {

    private final CoralStay plugin;

    public OnBlockPlace(CoralStay plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        String permission = plugin.getConfig().getString("Restricted_Access.Permission");
        Player p = e.getPlayer();
        if (plugin.blocks.contains(e.getBlock().getType())) {
            if (!p.hasPermission(permission)) {
                if (plugin.getConfig().getBoolean("Restricted_Access.BlockPlacement.Enabled")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Restricted_Access.BlockPlacement.Deny_Message")));
                    e.setCancelled(true);
                }
            } else {
                String path = plugin.getLocationCypher().getStringFromLocation(e.getBlock().getLocation());
                plugin.data.set(path + ".Material", e.getBlock().getType().toString());
                plugin.data.set(path + ".Location", path);
                plugin.data.set(path + ".Owner", "" + e.getPlayer().getUniqueId());
                plugin.saveFile();
            }

        }
    }

}
