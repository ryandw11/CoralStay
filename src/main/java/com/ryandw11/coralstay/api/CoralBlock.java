package com.ryandw11.coralstay.api;

import com.ryandw11.coralstay.CoralStay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import java.util.Objects;
import java.util.UUID;

public class CoralBlock {

    private Location loc;
    private UUID owner;
    private Material material;
    private boolean exists;
    private String path;
    private final CoralStay plugin;

    public CoralBlock(CoralStay plugin, Location loc) {
        this.plugin = plugin;
        this.loc = loc;
        if (plugin.data.contains(plugin.getLocationCypher().getStringFromLocation(loc))) {
            path = plugin.getLocationCypher().getStringFromLocation(loc);
            owner = UUID.fromString(Objects.requireNonNull(plugin.data.getString(path + ".Owner")));
            material = Material.valueOf(plugin.data.getString(path + ".Material"));
            exists = true;
        } else {
            exists = false;
        }
    }

    /**
     * Construct a coral block from a normal block.
     *
     * @param block The block to create the coral block from.
     * @throws IllegalArgumentException If the provided block is not a coral block.
     */
    public CoralBlock(Block block) {
        this.plugin = CoralStay.getInstance();
        if (!this.plugin.blocks.contains(block.getType()))
            throw new IllegalArgumentException("The provided block is not a coral block!");

        this.loc = block.getLocation();
        if (plugin.data.contains(plugin.getLocationCypher().getStringFromLocation(loc))) {
            path = plugin.getLocationCypher().getStringFromLocation(loc);
            owner = UUID.fromString(Objects.requireNonNull(plugin.data.getString(path + ".Owner")));
            material = Material.valueOf(plugin.data.getString(path + ".Material"));
            exists = true;
        } else {
            exists = false;
        }
    }

    /**
     * If the block exists.
     *
     * @return True if it does. False if not.
     */
    public boolean exists() {
        return exists;
    }

    /**
     * Get the owner's uuid.
     *
     * @return uuid.
     */
    public UUID getOwnerUUID() {
        return owner;
    }

    /**
     * Get the owner's offline player.
     *
     * @return OfflinePlayer
     */
    public OfflinePlayer getOwnerOfflinePlayer() {
        return Bukkit.getOfflinePlayer(owner);
    }

    /**
     * Get the location of the block.
     *
     * @return Location.
     */
    public Location getLocation() {
        return loc;
    }

    /**
     * Get the path of the block.
     *
     * @return path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the material of the coral.
     *
     * @return material.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Deletes the Coral Block data.
     * Notice: Everything after delete will be null.
     */
    public void delete() {
        plugin.data.set(path, null);
        path = null;
        owner = null;
        material = null;
        exists = false;
        loc = null;
    }

}
