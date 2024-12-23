package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

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
        bedSpawn = new Location[NUM_OF_TEAMS];

        config = plugin.getConfig();

        //Set up the lobby spawn point
        if(config.getString("Lobby Spawn") == null){
            //If a lobby spawn cannot be found in the config then set it to the default value and add the default value to the config.
            updateLobbySpawnPoint(new Location(plugin.getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f));
        }
        setLobbySpawn(stringToLoc(config.getString("Lobby Spawn")));

        //set up the team spawn points
        for(int i = 0; i < NUM_OF_TEAMS; i++){
            if(config.getString("Team Spawn." + i) == null){
                updateTeamSpawnPoint(new Location(plugin.getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f), i);
            }
            setTeamSpawn(stringToLoc(config.getString("Team Spawn." + i)), i);
        }

        //set up the bed spawn points
        for(int i = 0; i < NUM_OF_TEAMS; i++){
            if(config.getString("Bed Spawn." + i) == null){
                updateBedSpawnPoint(new Location(plugin.getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f), i);
            }
            setBedSpawn(stringToLoc(config.getString("Bed Spawn." + i)), i);
        }
    }

    //If the player is on a team then teleport them to the team spawn, otherwise teleport them to the lobby spawn.
    public void teleportToSpawn(Player player){
        for(Team team : plugin.teams){
            for(UUID p: team.getPlayers()){
                if(plugin.getServer().getOfflinePlayer(p).isOnline()){
                    Player _p = (Player) plugin.getServer().getOfflinePlayer(p);

                    if(_p == player){
                        player.teleport(team.getSpawnPoint());
                        return;
                    }
                }


            }
        }
        player.teleport(getLobbySpawn());
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
        plugin.saveConfig();
    }

    public void updateTeamSpawnPoint(Location loc, int index){
        loc.setYaw(roundTo45(loc.getYaw()));
        loc.setPitch(0.0f);
        config.addDefault("Team Spawn." + index, locToString(loc));
        plugin.saveConfig();
    }

    public void updateBedSpawnPoint(Location loc, int index){
        config.addDefault("Bed Spawn." + index, locToString(loc));
        plugin.saveConfig();
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

    public Location getBedSpawn(int index){
        return bedSpawn[index];
    }

    public void setBedSpawn(Location loc, int index){
        bedSpawn[index] = loc;
    }
}
