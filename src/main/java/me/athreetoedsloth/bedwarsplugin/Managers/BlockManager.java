package me.athreetoedsloth.bedwarsplugin.Managers;

import me.athreetoedsloth.bedwarsplugin.BedwarsPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class BlockManager {

    BedwarsPlugin plugin;
    private boolean[][][] protectedBlocks;
    private boolean protectionEnabled = true;
    int x;
    int y;
    int z;

    public BlockManager(BedwarsPlugin plugin){
        this.plugin = plugin;
    }

    public void setupProtectedBlocks(World world){
        x = 100;
        y = 256;
        z = 100;
        protectedBlocks = new boolean[x*2][y][z*2];

        for(int i = -x; i < x; i++ ){
            for(int j = 0; j < y; j++){
                for(int k = -z; k < z; k++){
                    if(new Location(world, i, j, k).getBlock().getType() != Material.AIR){
                        protectedBlocks[i + x][j][k + z] = true;
                    }
                }
            }
        }
    }

    public boolean checkIfProtected(Location loc){
        if(
                loc.getBlockX() <= x && loc.getBlockX() >= -x
                && loc.getBlockY() <= y && loc.getBlockY() >= 0
                && loc.getBlockZ() <= z && loc.getBlockZ() >= -z
        ){
            return protectedBlocks[loc.getBlockX() + x][loc.getBlockY()][loc.getBlockZ() + z];
        }
        return false;
    }

    public void resetMap(World world){
        for(int i = -x; i < x; i++ ){
            for(int j = 0; j < y; j++){
                for(int k = -z; k < z; k++){
                    if(!protectedBlocks[i + x][j][k + z]){
                        new Location(world, i, j, k).getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

    public boolean getProtectionEnabled(){
        return protectionEnabled;
    }
    public void setProtectionEnabled(boolean protect){
        protectionEnabled = protect;
    }
}
