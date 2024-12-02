package me.athreetoedsloth.bedwarsplugin.Commands;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBlockProtection implements CommandExecutor {

    BedwarsPlugin plugin;

    public SetBlockProtection(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(plugin.stateManager.getState() == GameStates.LOBBY){
            if(plugin.blockManager.getProtectionEnabled()){
                plugin.blockManager.setProtectionEnabled(false);
                commandSender.sendMessage("Block protections have been disabled.");
            }
            else{
                plugin.blockManager.setProtectionEnabled(true);
                commandSender.sendMessage("Block protections have been enabled");
            }
        }
        else{
            commandSender.sendMessage("You cannot disable block protections while a game is in progress.");
        }
        return  true;
    }
}
