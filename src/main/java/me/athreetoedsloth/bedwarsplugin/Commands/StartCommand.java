package me.athreetoedsloth.bedwarsplugin.Commands;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    BedwarsPlugin plugin;

    public StartCommand(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(plugin.stateManager.getState() == GameStates.LOBBY){
            plugin.stateManager.changeState(GameStates.START);
        }
        else {
            commandSender.sendMessage("The match has already started!");
        }
        return true;
    }
}
