package ru.func.wallaccessmanager.blockaction.chest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

/**
 * @author func 09.05.2020
 * @project WallAccessManager
 */
@Getter
@AllArgsConstructor
public class Chest {
    public static Chest NO_CHEST = new Chest(0L, null);

    private Long id;
    private Location location;
}
