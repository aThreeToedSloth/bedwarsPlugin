package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class StateManager {

    BedwarsPlugin plugin;

    public StateManager(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    private GameStates state = GameStates.LOBBY;

    //Changes the game state
    public void changeState(GameStates state){
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        World world = (players.get(0).getWorld());

        this.state = state;
        switch (state){
            case LOBBY:

                break;
            case START:
                //Enable block protection
                plugin.blockManager.setupProtectedBlocks(world);
                plugin.blockManager.setProtectionEnabled(true);

                //Place beds
                placeBeds(plugin.numberOfTeams);

                //Set up teams and teleport players
                plugin.teamManager.setupTeams(plugin.numberOfTeams);
                teleportPlayersToSpawnPoint();

                //Display the start title for all players
                for(Player p : plugin.getServer().getOnlinePlayers()){
                    displayTitle(p);
                }

                plugin.kitManager.giveKits();

                changeState(GameStates.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                Bukkit.broadcastMessage("The game is now in progress!");
                break;
            case END:
                Bukkit.broadcastMessage("The game has ended!");
                plugin.teams.clear();
                teleportPlayersToLobby();
                plugin.blockManager.resetMap(world);

                plugin.kitManager.clearKits();

                changeState(GameStates.LOBBY);
                break;
        }
    }

    //Teleports all players to their team's spawn
    private void teleportPlayersToSpawnPoint(){
        for(Team team : plugin.teams){
            for(UUID p: team.getPlayers()){
                if(plugin.getServer().getOfflinePlayer(p).isOnline()){
                    Player player = (Player) plugin.getServer().getOfflinePlayer(p);
                    player.teleport(team.getSpawnPoint());
                }
            }
        }
    }

    //Teleports all players to the lobby spawn
    private void teleportPlayersToLobby(){
        for(Player p: plugin.getServer().getOnlinePlayers()){
            p.teleport(plugin.spawnPointManager.getLobbySpawn());
        }
    }

    private void displayTitle(Player p){

        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        PlayerConnection playerConnection = entityPlayer.playerConnection;

        IChatBaseComponent message = IChatBaseComponent.ChatSerializer.a("Go!");

        for(Team team : plugin.teams){
            for(UUID player : team.getPlayers()){
                if(plugin.getServer().getOfflinePlayer(player).isOnline()){
                    Player _player = (Player) plugin.getServer().getOfflinePlayer(player);
                    if(_player == p){
                        message = IChatBaseComponent.ChatSerializer.a(team.getTeamChatColor() + "Go!");
                    }
                }
            }
        }

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, message);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5,20,5);
        playerConnection.sendPacket(title);
        playerConnection.sendPacket(length);
    }

    //Place the beds down at the start of a match
    private void placeBeds(int teams){
        for(int i = 0; i < teams; i++){
            plugin.spawnPointManager.getBedSpawn(i).getBlock().setType(Material.WOOL);
        }
    }

    public GameStates getState(){
        return this.state;
    }
}
