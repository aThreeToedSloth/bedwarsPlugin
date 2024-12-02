package me.athreetoedsloth.bedwarsplugin;

import me.athreetoedsloth.bedwarsplugin.Commands.*;
import me.athreetoedsloth.bedwarsplugin.Listeners.OnBlockBreak;
import me.athreetoedsloth.bedwarsplugin.Listeners.OnBlockPlace;
import me.athreetoedsloth.bedwarsplugin.Listeners.OnHungerLoss;
import me.athreetoedsloth.bedwarsplugin.Listeners.PlayerTakeDamage;
import me.athreetoedsloth.bedwarsplugin.Managers.*;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BedwarsPlugin extends JavaPlugin {

    public Team team = new Team(this);
    public TeamManager teamManager = new TeamManager(this);
    public ArrayList<Team> teams = new ArrayList<>();
    public StateManager stateManager = new StateManager(this);
    public SpawnPointManager spawnPointManager = new SpawnPointManager(this);
    public BlockManager blockManager = new BlockManager(this);

    public Location lobbySpawn;

    @Override
    public void onEnable() {
        //Set up the spawn point manager
        spawnPointManager.setup();

        //Register commands
        this.getCommand("start").setExecutor(new StartCommand(this));
        this.getCommand("end").setExecutor(new EndCommand(this));
        this.getCommand("setlobbyspawn").setExecutor(new SetLobbySpawn(this));
        this.getCommand("blockprotection").setExecutor(new SetBlockProtection(this));
        this.getCommand("setbedspawn").setExecutor(new SetBedSpawn());
        this.getCommand("setteamspawn").setExecutor(new SetTeamSpawn(this));

        //Register events
        getServer().getPluginManager().registerEvents(new PlayerTakeDamage(this), this);
        getServer().getPluginManager().registerEvents(new OnBlockPlace(this), this);
        getServer().getPluginManager().registerEvents(new OnBlockBreak(this), this);
        getServer().getPluginManager().registerEvents(new OnHungerLoss(), this);

        System.out.println(spawnPointManager.getTeamSpawn(1));

    }

    @Override
    public void onDisable() {
        saveConfig();
    }



    /* ---CHECKLIST---
        -ALLOW PLAYER TO SET TEAM AND BED SPAWN POINTS
        -SET UP RESTRAINTS FOR PLAYERS
        -SET UP SYSTEM TO GIVE AND TAKE AWAY ITEMS FROM PLAYERS
        -CREATE GAME LOOP
    */
}
