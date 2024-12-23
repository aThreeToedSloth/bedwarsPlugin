package me.athreetoedsloth.bedwarsplugin.Commands;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobbySpawn implements CommandExecutor {

    BedwarsPlugin plugin;

    public SetLobbySpawn(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(plugin.stateManager.getState() != GameStates.LOBBY){
            commandSender.sendMessage(ChatColor.RED + "You cannot use this command while a game is in progress.");
            return true;
        }

        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            Location loc = p.getLocation();

            plugin.spawnPointManager.updateLobbySpawnPoint(loc); //Changes the location of the lobby spawn in the config file.
            plugin.spawnPointManager.setLobbySpawn(loc);
            p.sendMessage(ChatColor.DARK_GREEN + (ChatColor.BOLD + "The lobby spawn point has been set to your current location."));
            return true;
        }
        return false;
    }
}
