package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {

    BedwarsPlugin plugin;

    public Team(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    //Data about the team
    private final ArrayList<Player> players = new ArrayList<>();
    private Color teamColor;
    private String teamName;
    private ChatColor teamChatColor;
    private Location spawnPoint;
    //-------------------

    //Getter and setter methods
    public void setTeamColor(Color color){
        teamColor = color;
    }

    public Color getTeamColor(){
        return this.teamColor;
    }

    public void setTeamName(String name){
        teamName = name;
    }

    public String getTeamName(){
        return this.teamName;
    }

    public void setTeamChatColor(ChatColor chatColor){
        teamChatColor = chatColor;
    }

    public ChatColor getTeamChatColor(){
        return this.teamChatColor;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setSpawnPoint(Location location){
        spawnPoint = location;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }
}
