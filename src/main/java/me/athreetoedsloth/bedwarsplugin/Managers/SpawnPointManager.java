package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnPointManager {
    BedwarsPlugin plugin;

    int NUM_OF_TEAMS = 8;

    FileConfiguration config;
    private Location lobbySpawn;
    private Location[] teamSpawn;
    private Location[] bedSpawn;

    public SpawnPointManager (BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    public void setup(){
        teamSpawn = new Location[NUM_OF_TEAMS];

        config = plugin.getConfig();
        config.options().copyDefaults(true);

        if(config.getString("Lobby Spawn") == null){
            //If a lobby spawn cannot be found in the config then set it to the default value and add the default value to the config.
            updateLobbySpawnPoint(new Location(plugin.getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f));
        }
        setLobbySpawn(stringToLoc(config.getString("Lobby Spawn")));

        for(int i = 0; i < NUM_OF_TEAMS; i++){
            if(config.getString("Team Spawn." + i) == null){
                updateTeamSpawnPoint(new Location(plugin.getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f), i);
            }
            setTeamSpawn(stringToLoc(config.getString("Team Spawn." + i)), i);
        }
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
        setLobbySpawn(loc);
    }

    public void updateTeamSpawnPoint(Location loc, int index){
        loc.setYaw(roundTo45(loc.getYaw()));
        loc.setPitch(0.0f);
        config.addDefault("Team Spawn." + index, locToString(loc));
    }

    public Location getLobbySpawn(){
        return lobbySpawn;
    }
    public void setLobbySpawn(Location loc){
        lobbySpawn = loc;
    }

    public Location getTeamSpawn(int index){
        return teamSpawn[index];
    }
    public void setTeamSpawn(Location loc, int index){
        teamSpawn[index] = loc;
    }
}
