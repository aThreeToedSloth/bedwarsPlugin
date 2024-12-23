package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public class TeamManager {

    BedwarsPlugin plugin;

    public TeamManager (BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    //List of colors
    private final Color[] colors = {Color.RED, Color.BLUE, Color.PURPLE, Color.ORANGE};
    private final ChatColor[] chatColors = {ChatColor.RED, ChatColor.BLUE, ChatColor.DARK_PURPLE, ChatColor.GOLD};
    private final String[] colorNames = {"Red","Blue","Fuchsia","Orange"};
    //--------------

    //Creates a new bedwars team
    private Team createTeam(Color color){
        Team team = new Team(plugin);
        team.setTeamColor(color);
        team.setBedAlive(true);
        team.setTeamAlive(true);

        for(int i = 0; i < colors.length; i++){
            if(color.equals(colors[i])){
                team.setTeamName(colorNames[i]);
                team.setTeamChatColor(chatColors[i]);
            }
        }

        return team;
    }

    //Sets up the teams when the game starts
    public void setupTeams(int numOfTeams){
        if(numOfTeams <= colors.length && numOfTeams > 1){
            for(int i = 0; i < numOfTeams; i++){
                plugin.teams.add(createTeam(colors[i]));
                plugin.teams.get(i).setSpawnPoint(plugin.spawnPointManager.getTeamSpawn(i));
            }

            int j = 0;
            for(Player p : plugin.getServer().getOnlinePlayers()){
                Team team = plugin.teams.get(j);
                team.addPlayer(p);
                p.sendMessage("You joined the " + team.getTeamChatColor() + team.getTeamName() + ChatColor.RESET + " team!");

                j++;
                if(j >= numOfTeams){
                    j = 0;
                }
            }

            for(Team team : plugin.teams){
                if(team.getPlayers().isEmpty()){
                    team.setTeamAlive(false);
                }
            }
        }
    }
}
