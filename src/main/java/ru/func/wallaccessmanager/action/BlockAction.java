package ru.func.wallaccessmanager.action;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Supplier;

public interface BlockAction {
    Supplier<Inventory> CHEST_TEMPLATE =
            () -> Bukkit.createInventory(null, 27, "Сыендук");

    void execute(Player player);

    Location getLocation();
}
