package me.athreetoedsloth.bedwarsplugin.Commands;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeamSpawn implements CommandExecutor {

    BedwarsPlugin plugin;

    public SetTeamSpawn(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    //Set the spawn point of a specific team to the current location of the command sender
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(plugin.stateManager.getState() != GameStates.LOBBY){
            commandSender.sendMessage(ChatColor.RED + "You cannot use this command while a game is in progress.");
            return true;
        }

        if(strings.length == 1){
            if(commandSender instanceof Player){
                Player p = (Player) commandSender;
                if(isValidNumber(strings[0])){
                    int num = Integer.parseInt(strings[0]);
                    Location loc = p.getLocation();

                    plugin.spawnPointManager.updateTeamSpawnPoint(loc, num);
                    plugin.spawnPointManager.setTeamSpawn(loc, num);
                    p.sendMessage(ChatColor.DARK_GREEN + (ChatColor.BOLD + "The team spawn point has been set to your current location."));
                    return true;
                }
                return false;
            }
            else {
                commandSender.sendMessage(ChatColor.RED + "Only a player may use this command.");
            }
            return true;
        }
        return false;
    }

    //Checks if the user inputted a number between 0-7
    private boolean isValidNumber(String string){
        if(string.length() == 1){
            char[] digit = string.toCharArray();
            return digit[0] <= '7' && digit[0] >= '0';
        }
        return false;
    }
}
