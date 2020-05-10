package ru.func.wallaccessmanager.listener;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import ru.func.wallaccessmanager.PlayerAccess;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.blockaction.chest.Chest;
import ru.func.wallaccessmanager.util.ChestAnimationUtil;

/**
 * @author func 09.05.2020
 * @project WallAccessManager
 */
public class ExtraInventoryHandler implements Listener {
    @EventHandler
    public void closeChest(InventoryCloseEvent e) {
        if (e.getInventory().getType().equals(InventoryType.CHEST)) {
            Player player = (Player) e.getPlayer();
            PlayerAccess access = DataManager.PLAYER_ACCESS.get(player.getUniqueId());
            Chest chest = access.getOpenedChest();
            Location location = chest.getLocation();
            if (chest != Chest.NO_CHEST) {
                ChestAnimationUtil.animate(player, location, false);
                player.playSound(location, Sound.BLOCK_CHEST_CLOSE, 1, 1);
                access.getInventories().replace(chest.getId(), e.getInventory().getContents());
                access.setOpenedChest(Chest.NO_CHEST);
            }
        }
    }
}
