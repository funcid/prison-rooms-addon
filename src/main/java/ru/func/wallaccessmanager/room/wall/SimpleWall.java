package ru.func.wallaccessmanager.room.wall;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Map;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
public class SimpleWall implements Wall {
    private Map<Location, Integer> wallPoints;
    private Location startPos;
    private Location endpoint;

    public SimpleWall(Location startPos, Location endpoint) {
        wallPoints = Maps.newHashMap();
        this.startPos = startPos;
        this.endpoint = endpoint;
    }

    public Map<Location, Integer> getBlocks() {
        return wallPoints;
    }

    @Override
    public void init() {
        for (int x = startPos.getBlockX(); x <= endpoint.getBlockX(); x++) {
            for (int y = startPos.getBlockY(); y <= endpoint.getBlockY(); y++) {
                for (int z = startPos.getBlockZ(); z <= endpoint.getBlockZ(); z++) {
                    Location location = new Location(startPos.getWorld(), x, y, z);
                    Block block = location.getBlock();
                    wallPoints.put(location, block.getType().getId() + (block.getData() << 12));
                    block.setType(Material.AIR);
                }
            }
        }
    }
}
