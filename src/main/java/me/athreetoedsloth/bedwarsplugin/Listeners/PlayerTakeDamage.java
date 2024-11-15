package me.athreetoedsloth.bedwarsplugin.Listeners;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerTakeDamage implements Listener {

    BedwarsPlugin plugin;

    public PlayerTakeDamage(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamageTake(EntityDamageEvent e){

        Entity entity = e.getEntity();
        if(entity instanceof Player){
            Player p = (Player) entity;
            if(p.getHealth() - e.getDamage() <= 0){
                plugin.spawnPointManager.teleportToSpawn(p);
                p.setHealth(20.0);
                e.setCancelled(true);
            }
        }
    }
}
