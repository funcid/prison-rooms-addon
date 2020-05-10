package ru.func.wallaccessmanager.data;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.func.wallaccessmanager.BlockFixHandler;
import ru.func.wallaccessmanager.PlayerAccess;
import ru.func.wallaccessmanager.WallAccessManager;
import ru.func.wallaccessmanager.action.chest.Chest;
import ru.func.wallaccessmanager.room.Room;

import java.util.*;

/**
 * @author func 08.05.2020
 * @project WallAccessManager
 */
@AllArgsConstructor
public class DataManagerImpl implements DataManager {
    private WallAccessManager plugin;

    @Override
    public void load(Player player) {
        BlockFixHandler.preparePlayerPipeline(player);
        Map<Long, ItemStack[]> map = new HashMap<>();
        map.put(1L, new ItemStack[]{new ItemStack(Material.PAPER)});
        PlayerAccess access = new PlayerAccess(Chest.NO_CHEST, new ArrayList<>(Collections.singletonList(Room.MAIN)), map);
        DataManager.PLAYER_ACCESS.put(player.getUniqueId(), access);
        Bukkit.getOnlinePlayers().stream()
                .filter(plr -> !plr.equals(player))
                .forEach(plr -> player.hidePlayer(plugin, plr));
        Bukkit.getScheduler().runTaskLater(plugin, () -> access.updateWalls(player, player), 5L);
    }

    @Override
    public void save(Player player) {
    }

    @Override
    public boolean withdraw(UUID uuid, double value) {
        return true;
    }
}
