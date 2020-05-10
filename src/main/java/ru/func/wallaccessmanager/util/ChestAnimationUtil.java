package ru.func.wallaccessmanager.util;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockAction;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author func 10.05.2020
 * @project WallAccessManager
 */
public class ChestAnimationUtil {

    public static void animate(Player player, Location location, boolean open) {
        BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(
                new PacketPlayOutBlockAction(pos, Blocks.CHEST, (byte) 1, open ? (byte) 1 : (byte) 0)
        );
    }
}
