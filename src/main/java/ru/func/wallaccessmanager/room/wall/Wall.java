package ru.func.wallaccessmanager.room.wall;

import org.bukkit.Location;

import java.util.Map;

public interface Wall {
    /**
     * @return список координат точек стены
     */
    Map<Location, Integer> getBlocks();

    void init();
}
