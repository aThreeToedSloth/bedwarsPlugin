package me.athreetoedsloth.bedwarsplugin;

import me.athreetoedsloth.bedwarsplugin.Commands.*;
import me.athreetoedsloth.bedwarsplugin.Listeners.*;
import me.athreetoedsloth.bedwarsplugin.Managers.*;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BedwarsPlugin extends JavaPlugin {
    public int numberOfTeams = 2;

    public Team team = new Team(this);
    public TeamManager teamManager = new TeamManager(this);
    public ArrayList<Team> teams = new ArrayList<>();
    public StateManager stateManager = new StateManager(this);
    public SpawnPointManager spawnPointManager = new SpawnPointManager(this);
    public BlockManager blockManager = new BlockManager(this);
    public DeathManager deathManager = new DeathManager(this);
    public KitManager kitManager = new KitManager(this);

    public Location lobbySpawn;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        //Set up the spawn point manager
        spawnPointManager.setup();

        //Register commands
        this.getCommand("start").setExecutor(new StartCommand(this));
        this.getCommand("end").setExecutor(new EndCommand(this));
        this.getCommand("setlobbyspawn").setExecutor(new SetLobbySpawn(this));
        this.getCommand("blockprotection").setExecutor(new SetBlockProtection(this));
        this.getCommand("setbedspawn").setExecutor(new SetBedSpawn(this));
        this.getCommand("setteamspawn").setExecutor(new SetTeamSpawn(this));

        //Register events
        getServer().getPluginManager().registerEvents(new PlayerTakeDamage(this), this);
        getServer().getPluginManager().registerEvents(new OnBlockPlace(this), this);
        getServer().getPluginManager().registerEvents(new OnBlockBreak(this), this);
        getServer().getPluginManager().registerEvents(new OnHungerLoss(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(this), this);

    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
