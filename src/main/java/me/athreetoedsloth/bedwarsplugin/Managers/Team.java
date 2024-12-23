package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Team {

    BedwarsPlugin plugin;

    public Team(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    //Data about the team
    private final ArrayList<UUID> players = new ArrayList<>();
    private Color teamColor;
    private String teamName;
    private ChatColor teamChatColor;
    private Location spawnPoint;
    private boolean bedAlive;
    private boolean teamAlive;
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
        players.add(player.getUniqueId());
    }

    public ArrayList<UUID> getPlayers(){
        return players;
    }

    public void setSpawnPoint(Location location){
        spawnPoint = location;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setBedAlive(boolean bedAlive) {
        this.bedAlive = bedAlive;
    }

    public boolean getBedAlive(){
        return this.bedAlive;
    }

    public void setTeamAlive(boolean teamAlive){
        this.teamAlive = teamAlive;
    }

    public boolean getTeamAlive(){
        return this.teamAlive;
    }
}
