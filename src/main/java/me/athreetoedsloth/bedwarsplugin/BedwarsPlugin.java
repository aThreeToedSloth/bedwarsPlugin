package me.athreetoedsloth.bedwarsplugin;

import me.athreetoedsloth.bedwarsplugin.Commands.StartCommand;
import me.athreetoedsloth.bedwarsplugin.Managers.StateManager;
import me.athreetoedsloth.bedwarsplugin.Managers.Team;
import me.athreetoedsloth.bedwarsplugin.Managers.TeamManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BedwarsPlugin extends JavaPlugin {

    public Team team = new Team(this);
    public TeamManager teamManager = new TeamManager(this);
    public ArrayList<Team> teams = new ArrayList<>();
    public StateManager stateManager = new StateManager(this);

    @Override
    public void onEnable() {

        this.getCommand("start").setExecutor(new StartCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
