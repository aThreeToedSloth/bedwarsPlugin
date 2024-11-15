package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnPointManager {
    BedwarsPlugin plugin;

    FileConfiguration config;
    private Location lobbySpawn;

    public SpawnPointManager (BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    public void setup(){
        config = plugin.getConfig();
        config.options().copyDefaults(true);

        if(config.getString("Lobby Spawn") != null){
            //If a lobby spawn can be found in the config file then set the lobbySpawn variable to it.
            setLobbySpawn(stringToLoc(config.getString("Lobby Spawn")));
        }
        else {
            //If a lobby spawn cannot be found in the config then set it to the default value and add the default value to the config.
            updateLobbySpawnPoint(new Location(plugin.getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f));
        }
        plugin.getServer().broadcastMessage("Woo!");
    }

    //If the player is on a team then teleport them to the team spawn, otherwise teleport them to the lobby spawn.
    public void teleportToSpawn(Player p){
        for(Team team : plugin.teams){
            for(Player _p: team.getPlayers()){
                if(_p == p){
                    p.teleport(team.getSpawnPoint());
                    return;
                }
            }
        }
        p.teleport(getLobbySpawn());
    }

    private String locToString(Location loc){
        String world = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();

        return String.format("%s,%f,%f,%f,%f", world, x, y, z, yaw);
    }

    private Location stringToLoc(String string){
        String[] locInfo = string.split(",");
        return new Location(
                Bukkit.getWorld(locInfo[0]), //World
                Double.parseDouble(locInfo[1]), Double.parseDouble(locInfo[2]), Double.parseDouble(locInfo[3]), //Coordinates
                Float.parseFloat(locInfo[4]), 0.0f); //Yaw and pitch
    }

    //Round to the nearest multiple of 45
    private float roundTo45(float num){
        float remainder = num % 45;
        return remainder < 22.5 ? num - remainder : num + 45 - remainder;
    }

    //Method to call when the lobby spawn point needs to be updated.
    public void updateLobbySpawnPoint(Location loc){
        loc.setYaw(roundTo45(loc.getYaw()));
        loc.setPitch(0.0f);
        config.addDefault("Lobby Spawn", locToString(loc));        //Adds the lobby spawn to the config
        setLobbySpawn(loc);                                               //Changes the location of the lobby spawn.
    }

    public Location getLobbySpawn(){
        return lobbySpawn;
    }
    public void setLobbySpawn(Location loc){
        lobbySpawn = loc;
    }
}
