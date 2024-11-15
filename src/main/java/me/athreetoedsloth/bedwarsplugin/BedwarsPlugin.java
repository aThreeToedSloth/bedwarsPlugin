package me.athreetoedsloth.bedwarsplugin;

import me.athreetoedsloth.bedwarsplugin.Commands.SetLobbySpawn;
import me.athreetoedsloth.bedwarsplugin.Commands.StartCommand;
import me.athreetoedsloth.bedwarsplugin.Commands.EndCommand;
import me.athreetoedsloth.bedwarsplugin.Listeners.PlayerTakeDamage;
import me.athreetoedsloth.bedwarsplugin.Managers.SpawnPointManager;
import me.athreetoedsloth.bedwarsplugin.Managers.StateManager;
import me.athreetoedsloth.bedwarsplugin.Managers.Team;
import me.athreetoedsloth.bedwarsplugin.Managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BedwarsPlugin extends JavaPlugin {

    public Team team = new Team(this);
    public TeamManager teamManager = new TeamManager(this);
    public ArrayList<Team> teams = new ArrayList<>();
    public StateManager stateManager = new StateManager(this);
    public SpawnPointManager spawnPointManager = new SpawnPointManager(this);

    public Location lobbySpawn;

    @Override
    public void onEnable() {
        //Set up the spawn point manager
        spawnPointManager.setup();

        //Register commands
        this.getCommand("start").setExecutor(new StartCommand(this));
        this.getCommand("end").setExecutor(new EndCommand(this));
        this.getCommand("setlobbyspawn").setExecutor(new SetLobbySpawn(this));

        //Register events
        getServer().getPluginManager().registerEvents(new PlayerTakeDamage(this), this);

    }

    @Override
    public void onDisable() {
        saveConfig();
    }



    /* ---CHECKLIST---
        -ALLOW PLAYER TO SET TEAM AND BED SPAWN POINTS
        -SET UP RESTRAINTS FOR PLAYERS
        -SET UP SYSTEM TO GIVE AND TAKE AWAY ITEMS FROM PLAYERS
        -BLOCK MANAGER
        -CREATE GAME LOOP
    */
}
