package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

public class DeathManager {

    BedwarsPlugin plugin;

    public DeathManager(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    public void onDeath(Player p){
        plugin.kitManager.clearKit(p);
        p.setHealth(20.0);
        displayDeathMessage(p);
        ifGameInProgress(p.getUniqueId());
        plugin.spawnPointManager.teleportToSpawn(p);
    }

    private void ifGameInProgress(UUID p){
        if(plugin.stateManager.getState() == GameStates.IN_PROGRESS){
            for(Team team : plugin.teams){
                if(!team.getBedAlive()){
                    //Iterate through the arraylist of players for the team
                    Iterator<UUID> iter = team.getPlayers().iterator();

                    while(iter.hasNext()){
                        if(iter.next() == p){
                            if(plugin.getServer().getOfflinePlayer(p).isOnline()){
                                Player _p = (Player) plugin.getServer().getOfflinePlayer(p);
                                _p.sendMessage( ChatColor.BLACK + "You have been eliminated.");
                            }

                            iter.remove();

                            boolean isEnd = checkIfTeamIsAlive(team);
                            if(isEnd){
                                return;
                            }
                        }
                    }
                }
                else{
                    if(plugin.getServer().getOfflinePlayer(p).isOnline()){
                        for(UUID _p : team.getPlayers()) {
                            if (_p == p) {
                                Player __p = (Player) plugin.getServer().getOfflinePlayer(p);
                                plugin.kitManager.giveKit(__p, team.getTeamColor());
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkIfTeamIsAlive(Team team) {
        int teamsAlive = 0;
        if(team.getPlayers().isEmpty()){
            team.setTeamAlive(false);
            plugin.getServer().broadcastMessage(team.getTeamChatColor() + team.getTeamName() + " has been eliminated from the game.");

            for(Team _team : plugin.teams){
                if(_team.getTeamAlive()){
                    teamsAlive++;
                }
            }
            if(teamsAlive > 1){
                return false;
            }
            else{
                plugin.stateManager.changeState(GameStates.END);
                return true;
            }
        }
        return false;
    }

    public void displayDeathMessage(Player player){
        Random rand = new Random();
        int randInt = rand.nextInt(5);

        String name = ChatColor.BOLD + player.getDisplayName();

        for(Team team : plugin.teams){
            for(UUID uuid: team.getPlayers()){
                if(uuid == player.getUniqueId()){
                    name = team.getTeamChatColor() + name;
                    break;
                }
            }
        }

        name = name + ChatColor.RESET;

        switch(randInt){
            case 0:
                plugin.getServer().broadcastMessage(name + " was killed.");
                break;
            case 1:
                plugin.getServer().broadcastMessage(name + " is no longer with us.");
                break;
            case 2:
                plugin.getServer().broadcastMessage(name + " has died.");
                break;
            case 3:
                plugin.getServer().broadcastMessage(name + " was slain.");
                break;
            case 4:
                plugin.getServer().broadcastMessage(name + " has been killed.");
                break;
        }
    }
}
