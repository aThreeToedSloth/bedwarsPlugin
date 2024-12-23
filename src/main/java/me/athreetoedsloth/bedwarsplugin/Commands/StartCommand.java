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
            if(strings.length == 1){
                if(isValidNumber(strings[0])){
                    int num = Integer.parseInt(strings[0]);
                    plugin.stateManager.changeState(GameStates.START);
                    plugin.numberOfTeams = num;
                    return true;
                }
            }
            return false;
        }
        else {
            commandSender.sendMessage("The match has already started!");
        }
        return true;
    }

    //Checks if the user inputted a number between 0-7
    private boolean isValidNumber(String string){
        if(string.length() == 1){
            char[] digit = string.toCharArray();
            return digit[0] <= '7' && digit[0] >= '2';
        }
        return false;
    }
}
