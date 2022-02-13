package com.ryandw11.coralstay.listeners;

import com.ryandw11.coralstay.CoralStay;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {
    private final CoralStay plugin;

    public OnBlockBreak(CoralStay plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        String permission = plugin.getConfig().getString("Restricted_Access.Permission");
        Player p = e.getPlayer();
        if (plugin.blocks.contains(e.getBlock().getType())) {
            if (plugin.data.contains(plugin.getLocationCypher().getStringFromLocation(e.getBlock().getLocation()))) {
                if (!e.getPlayer().hasPermission(permission)) {
                    if (plugin.getConfig().getBoolean("Restricted_Access.BlockPlacement.Enabled")) {
                        p.sendMessage(ChatColor.RED + "You do not have permission to break this block!");
                        e.setCancelled(true);
                        return;
                    }
                }

                plugin.data.set(plugin.getLocationCypher().getStringFromLocation(e.getBlock().getLocation()), null);
                plugin.saveFile();


            } //End of if data contains block check
        }
    }
}
