package me.athreetoedsloth.bedwarsplugin.Listeners;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {
    BedwarsPlugin plugin;

    public OnBlockBreak(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(plugin.blockManager.getProtectionEnabled()){
            if(plugin.stateManager.getState() == GameStates.LOBBY){
                e.setCancelled(true);
            }
            else if (plugin.stateManager.getState() == GameStates.IN_PROGRESS) {
                if(plugin.blockManager.checkIfProtected(e.getBlock().getLocation())){
                    e.setCancelled(true);
                }
            }
        }
    }
}
