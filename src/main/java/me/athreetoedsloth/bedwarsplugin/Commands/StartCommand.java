package me.athreetoedsloth.bedwarsplugin.Commands;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.States;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    private BedwarsPlugin plugin;

    public StartCommand(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        plugin.stateManager.changeState(States.START);

        return true;
    }
}
