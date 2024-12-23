package me.athreetoedsloth.bedwarsplugin.Listeners;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerDisconnect implements Listener {
    BedwarsPlugin plugin;

    public OnPlayerDisconnect(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e){
        plugin.deathManager.onDeath(e.getPlayer());
    }
}
