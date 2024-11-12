package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class StateManager {

    private BedwarsPlugin plugin;

    public StateManager(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    private GameStates state = GameStates.LOBBY;

    //Changes the game state
    public void changeState(GameStates state){
        this.state = state;
        switch (state){
            case LOBBY:

                break;
            case START:
                plugin.teamManager.setupTeams(2);
                teleportPlayersToSpawnPoint();

                for(Player p : plugin.getServer().getOnlinePlayers()){
                    displayTitle(p);
                }

                changeState(GameStates.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                Bukkit.broadcastMessage("The game is now in progress!");
                break;
            case END:
                Bukkit.broadcastMessage("The game has ended!");
                plugin.teams.clear();
                teleportPlayersToLobby();
                changeState(GameStates.LOBBY);
                break;
        }
    }

    //Teleports all players to their team's spawn
    private void teleportPlayersToSpawnPoint(){
        for(Team team : plugin.teams){
            for(Player p: team.getPlayers()){
                p.teleport(team.getSpawnPoint());
            }
        }
    }

    //Teleports all players to the lobby spawn
    private void teleportPlayersToLobby(){
        for(Player p: plugin.getServer().getOnlinePlayers()){
            p.teleport(plugin.LOBBY_SPAWN);
        }
    }

    private void displayTitle(Player p){

        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        PlayerConnection playerConnection = entityPlayer.playerConnection;

        IChatBaseComponent message = IChatBaseComponent.ChatSerializer.a("Go!");

        for(Team team : plugin.teams){
            for(Player player : team.getPlayers()){
                if(player == p){
                    message = IChatBaseComponent.ChatSerializer.a(team.getTeamChatColor() + "Go!");
                }
            }
        }

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, message);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5,20,5);
        playerConnection.sendPacket(title);
        playerConnection.sendPacket(length);
    }

    public GameStates getState(){
        return this.state;
    }
}
