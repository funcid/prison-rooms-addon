package ru.func.wallaccessmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.blockaction.chest.Chest;
import ru.func.wallaccessmanager.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
@Getter
@AllArgsConstructor
public class PlayerAccess {
    @Setter
    private Chest openedChest;
    private List<Room> availableRooms;
    private Map<Long, ItemStack[]> inventories;
    private static final IBlockData AIR_DATA = Block.getById(0).getBlockData();

    public void updateWalls(Player player, Player owner) {
        PlayerAccess ownerAccess = DataManager.PLAYER_ACCESS.get(owner.getUniqueId());
        World world = ((CraftWorld) player.getWorld()).getHandle();
        List<PacketPlayOutBlockChange> packets = new ArrayList<>();
        Stream.of(Room.values())
                .filter(room -> (ownerAccess.availableRooms == null || !ownerAccess.availableRooms.contains(room)) && room.getWall().isPresent())
                .forEach(room -> room.getWall().get().getBlocks().forEach((key, value) -> {
                    PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
                            world,
                            new BlockPosition(key.getX(), key.getY(), key.getZ())
                    );
                    packet.block = Block.getByCombinedId(value);
                    packets.add(packet);
                }));
        ownerAccess.getAvailableRooms().forEach(room -> room.getWall().ifPresent(wall -> wall.getBlocks()
                .keySet()
                .forEach(location -> {
                    PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
                            world,
                            new BlockPosition(location.getX(), location.getY(), location.getZ())
                    );
                    packet.block = AIR_DATA;
                    packets.add(packet);
                })));
        packets.forEach(((CraftPlayer) player).getHandle().playerConnection::sendPacket);
    }
}
