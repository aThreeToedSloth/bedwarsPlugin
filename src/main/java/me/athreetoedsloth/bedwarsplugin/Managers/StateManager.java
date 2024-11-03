package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class StateManager {

    private BedwarsPlugin plugin;

    public StateManager(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    private States state = States.LOBBY;

    public void changeState(States state){
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

                changeState(States.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                Bukkit.broadcastMessage("The game is now in progress!");
                break;
            case END:
                break;
        }
    }

    private void teleportPlayersToSpawnPoint(){
        for(Team team : plugin.teams){
            for(Player p: team.getPlayers()){
                p.teleport(team.getSpawnPoint());
            }
        }
    }

    private void displayTitle(Player p){

        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        PlayerConnection playerConnection = entityPlayer.playerConnection;

        IChatBaseComponent message = IChatBaseComponent.ChatSerializer.a("Go!");

        for(Team team : plugin.teams){
            for(Player ignored : team.getPlayers()){
                message = IChatBaseComponent.ChatSerializer.a(team.getTeamChatColor() + "Go!");
            }
        }

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, message);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5,20,5);
        playerConnection.sendPacket(title);
        playerConnection.sendPacket(length);
    }
}
