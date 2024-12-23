package me.athreetoedsloth.bedwarsplugin.Listeners;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import me.athreetoedsloth.bedwarsplugin.Managers.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Iterator;
import java.util.UUID;

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

            //If player dies, reset their health.
            if(p.getHealth() - e.getDamage() <= 0){
                plugin.deathManager.onDeath(p);
                e.setCancelled(true);
            }
        }
    }


}
