package ru.func.wallaccessmanager.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.room.wall.SimpleWall;
import ru.func.wallaccessmanager.room.wall.Wall;

import java.util.Optional;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
@Getter
@AllArgsConstructor
public enum Room {
    MAIN(0L, "Основная", null, 0, Optional.empty(), new Location(DataManager.WORLD, 254, 68, 278)),
    WC(1L, "Туалет", MAIN, 100, Optional.of(new SimpleWall(
            new Location(DataManager.WORLD, 253, 68, 276),
            new Location(DataManager.WORLD, 255, 71, 276)
    )), new Location(DataManager.WORLD, 254, 68, 274)),;

    private Long id;
    private String name;
    private Room parent;
    private double price;
    private Optional<Wall> wall;
    private Location position;
}
