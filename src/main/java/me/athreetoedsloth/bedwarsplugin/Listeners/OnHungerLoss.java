package me.athreetoedsloth.bedwarsplugin.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class OnHungerLoss implements Listener {

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent e){
        Player p = (Player) e.getEntity();
        p.setFoodLevel(20);
        p.setSaturation(20);
        e.setCancelled(true);
    }
}
