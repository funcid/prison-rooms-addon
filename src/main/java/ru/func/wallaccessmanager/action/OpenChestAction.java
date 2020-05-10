package ru.func.wallaccessmanager.action;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.func.wallaccessmanager.PlayerAccess;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.action.chest.Chest;
import ru.func.wallaccessmanager.util.ChestAnimationUtil;

/**
 * @author func 10.05.2020
 * @project WallAccessManager
 */
@AllArgsConstructor
public class OpenChestAction implements BlockAction {
    private Chest chest;

    @Override
    public void execute(Player player) {
        PlayerAccess access = DataManager.PLAYER_ACCESS.get(player.getUniqueId());
        if (access.getOpenedChest() != Chest.NO_CHEST)
            return;
        access.setOpenedChest(chest);
        ChestAnimationUtil.animate(player, chest.getLocation(), true);
        player.playSound(chest.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
        Inventory inventory = CHEST_TEMPLATE.get();
        inventory.setContents(access.getInventories().getOrDefault(chest.getId(), new ItemStack[]{}));
        player.openInventory(inventory);
    }

    @Override
    public Location getLocation() {
        return chest.getLocation();
    }
}
