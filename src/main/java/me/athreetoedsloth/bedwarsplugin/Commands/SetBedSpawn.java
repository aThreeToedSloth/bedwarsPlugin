package me.athreetoedsloth.bedwarsplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBedSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            if(commandSender instanceof Player){

            }
            else {
                commandSender.sendMessage(ChatColor.RED + "Only a player may use this command.");
            }
            return true;
        }
        return false;
    }
}
