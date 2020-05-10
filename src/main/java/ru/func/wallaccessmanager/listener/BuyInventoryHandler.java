package ru.func.wallaccessmanager.listener;

import lombok.AllArgsConstructor;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.func.wallaccessmanager.PlayerAccess;
import ru.func.wallaccessmanager.WallAccessManager;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
@AllArgsConstructor
public class BuyInventoryHandler implements Listener {

    private static Supplier<Inventory> inventory =
            () -> Bukkit.createInventory(null, 9, "Покупка комнаты");
    private WallAccessManager plugin;

    public static void open(Room room, Player player) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("[КУПИТЬ] Доступ в " + room.getName());
        meta.setLore(Arrays.asList(
                "",
                "Номер: " + room.getId(),
                "Цена: " + room.getPrice()
        ));
        itemStack.setItemMeta(meta);
        Inventory inv = inventory.get();
        inv.setItem(4, itemStack);
        player.openInventory(inv);
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("Покупка комнаты")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                ItemStack itemStack = e.getCurrentItem();
                String val = ChatColor.stripColor(itemStack.getItemMeta().getLore().get(1).split("\\s+")[1]);
                if (itemStack.getItemMeta() != null) {
                    Stream.of(Room.values())
                            .filter(room -> room.getId().toString().equals(val))
                            .findAny()
                            .ifPresent(room -> {
                                Player player = (Player) e.getWhoClicked();
                                UUID uuid = player.getUniqueId();
                                PlayerAccess playerAccess = DataManager.PLAYER_ACCESS.get(uuid);
                                List<Room> rooms = playerAccess.getAvailableRooms();
                                // Если удалось списать деньги, комната еще не куплена и родительская комната есть у игрока
                                if (plugin.getDataManager().withdraw(uuid, room.getPrice()) &&
                                        !rooms.contains(room) &&
                                        rooms.contains(room.getParent())
                                ) {
                                    rooms.add(room);
                                    playerAccess.updateWalls(player, player);
                                    player.sendMessage("Вы приобрели комнату!");
                                } else
                                    player.sendMessage("Вы не приобрели комнату!");
                                player.closeInventory();
                            });
                }
            }
        }
    }
}
