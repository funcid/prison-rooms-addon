package ru.func.wallaccessmanager.data;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.func.wallaccessmanager.PlayerAccess;

import java.util.Map;
import java.util.UUID;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
public interface DataManager {
    Map<UUID, PlayerAccess> PLAYER_ACCESS = Maps.newHashMap();
    World WORLD = Bukkit.getWorld("world");

    void load(Player player);

    void save(Player player);

    boolean withdraw(UUID uuid, double value);
}
