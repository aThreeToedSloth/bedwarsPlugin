package me.athreetoedsloth.bedwarsplugin.Listeners;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import me.athreetoedsloth.bedwarsplugin.Managers.Team;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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
                else {
                    for(int i = 0; i < plugin.numberOfTeams; i++){
                        if(e.getBlock().getLocation().equals(plugin.spawnPointManager.getBedSpawn(i).getBlock().getLocation())){
                            Team team = plugin.teams.get(i);

                            if(team.getBedAlive()){
                                team.setBedAlive(false);
                                Bukkit.broadcastMessage(team.getTeamChatColor() + team.getTeamName() + " bed has been destroyed.");
                                plugin.deathManager.checkIfTeamIsAlive(team);

                                for(Player p : plugin.getServer().getOnlinePlayers()){
                                    p.playSound(p.getLocation(), Sound.CAT_MEOW, 1, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
