package me.athreetoedsloth.bedwarsplugin.Commands;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndCommand implements CommandExecutor {

    private BedwarsPlugin plugin;

    public EndCommand(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(plugin.stateManager.getState() == GameStates.IN_PROGRESS){
            plugin.stateManager.changeState(GameStates.END);
        }
        else {
            commandSender.sendMessage("The match hasn't begun!");
        }
        return true;
    }
}
