package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

public class KitManager {
    BedwarsPlugin plugin;

    public KitManager(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    public void giveKit(Player player, Color color){
        PlayerInventory inventory = player.getInventory();

        ItemStack itemStack;

        itemStack = new ItemStack(Material.WOOD_SWORD);
        itemStack = makeUnbreakable(itemStack);
        inventory.addItem(itemStack);

        itemStack = new ItemStack(Material.STONE_PICKAXE);
        itemStack = makeUnbreakable(itemStack);
        inventory.addItem(itemStack);
        byte _color = (byte) 7;

        if(color.equals(Color.RED)){
            _color = (byte) 14;
        } else if (color.equals(Color.BLUE)) {
            _color = (byte) 11;
        } else if (color.equals(Color.PURPLE)) {
            _color = (byte) 10;
        } else if (color.equals(Color.ORANGE)) {
            _color = (byte) 1;
        }
        itemStack = new ItemStack(Material.STAINED_CLAY,64, _color);
        inventory.addItem(itemStack);

        itemStack = new ItemStack(Material.LEATHER_HELMET);
        itemStack = makeUnbreakable(itemStack);
        itemStack = makeColor(itemStack, color);
        inventory.setHelmet(itemStack);

        itemStack = new ItemStack(Material.LEATHER_CHESTPLATE);
        itemStack = makeUnbreakable(itemStack);
        itemStack = makeColor(itemStack, color);
        inventory.setChestplate(itemStack);

        itemStack = new ItemStack(Material.LEATHER_LEGGINGS);
        itemStack = makeUnbreakable(itemStack);
        itemStack = makeColor(itemStack, color);
        inventory.setLeggings(itemStack);

        itemStack = new ItemStack(Material.LEATHER_BOOTS);
        itemStack = makeUnbreakable(itemStack);
        itemStack = makeColor(itemStack, color);
        inventory.setBoots(itemStack);
    }

    private ItemStack makeUnbreakable(ItemStack itemStack){
        ItemMeta itemMeta;
        itemMeta = itemStack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack makeColor(ItemStack itemStack, Color color){
        ItemMeta itemMeta;
        itemMeta = itemStack.getItemMeta();
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return itemStack;
    }

    public void giveKits(){
        for(Team team : plugin.teams){
            for(UUID player: team.getPlayers()){
                if(plugin.getServer().getOfflinePlayer(player).isOnline()){
                    Player p = (Player) plugin.getServer().getOfflinePlayer(player);
                    giveKit(p, team.getTeamColor());
                }
            }
        }
    }

    public void clearKit(Player p){
        PlayerInventory inventory = p.getInventory();

        inventory.clear();
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);
    }

    public void clearKits(){
        for(Player p: plugin.getServer().getOnlinePlayers()){
            clearKit(p);
        }
    }
}
