package me.athreetoedsloth.bedwarsplugin;

import me.athreetoedsloth.bedwarsplugin.Commands.StartCommand;
import me.athreetoedsloth.bedwarsplugin.Commands.EndCommand;
import me.athreetoedsloth.bedwarsplugin.Listeners.PlayerTakeDamage;
import me.athreetoedsloth.bedwarsplugin.Managers.GameStates;
import me.athreetoedsloth.bedwarsplugin.Managers.StateManager;
import me.athreetoedsloth.bedwarsplugin.Managers.Team;
import me.athreetoedsloth.bedwarsplugin.Managers.TeamManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BedwarsPlugin extends JavaPlugin {

    public Team team = new Team(this);
    public TeamManager teamManager = new TeamManager(this);
    public ArrayList<Team> teams = new ArrayList<>();
    public StateManager stateManager = new StateManager(this);

    public Location LOBBY_SPAWN;

    @Override
    public void onEnable() {
        LOBBY_SPAWN = new Location(getServer().getWorld("world"), 3.5, 96, -8.5, 90.0f, 0.0f);

        this.getCommand("start").setExecutor(new StartCommand(this));
        this.getCommand("end").setExecutor(new EndCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerTakeDamage(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //If the player is on a team then teleport them to the team spawn, otherwise teleport them to the lobby spawn.
    public void teleportToSpawn(Player p){
        for(Team team : teams){
            for(Player _p: team.getPlayers()){
                if(_p == p){
                    p.teleport(team.getSpawnPoint());
                    return;
                }
            }
        }

        p.teleport(LOBBY_SPAWN);
    }

    /* ---CHECKLIST---
        -ALLOW PLAYER TO SET TEAM AND BED SPAWN POINTS
        -SET UP RESTRAINTS FOR PLAYERS
        -SET UP SYSTEM TO GIVE AND TAKE AWAY ITEMS FROM PLAYERS
        -BLOCK MANAGER
        -CREATE GAME LOOP
    */
}
