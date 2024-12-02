package me.athreetoedsloth.bedwarsplugin.Listeners;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {

    BedwarsPlugin plugin;

    public OnBlockPlace(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(plugin.blockManager.getProtectionEnabled()){
            if(plugin.stateManager.getState() == GameStates.LOBBY){
                e.setCancelled(true);
            }
        }
    }
}
