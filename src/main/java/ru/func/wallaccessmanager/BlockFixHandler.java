package ru.func.wallaccessmanager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.func.wallaccessmanager.action.BlockActions;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.listener.BuyInventoryHandler;
import ru.func.wallaccessmanager.room.Room;

import java.util.List;
import java.util.UUID;

/**
 * @author func 08.05.2020
 * @project WallAccessManager
 */
public class BlockFixHandler {

    public static void preparePlayerPipeline(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline().addAfter("decoder",
                UUID.randomUUID().toString(), new MessageToMessageDecoder<Packet>() {
                    @Override
                    protected void decode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) {
                        boolean cancel = false;
                        try {
                            if (player.getWorld().equals(DataManager.WORLD)) {
                                if (packet instanceof PacketPlayInBlockDig) {
                                    PacketPlayInBlockDig pc = (PacketPlayInBlockDig) packet;
                                    Location location = new Location(DataManager.WORLD, pc.a().getX(), pc.a().getY(), pc.a().getZ());
                                    for (Room room : Room.values()) {
                                        if (room.getWall().isPresent() && room.getWall().get().getBlocks().containsKey(location)) {
                                            MinecraftServer.getServer().postToMainThread(() -> {
                                                PacketPlayOutBlockChange fixBreak = new PacketPlayOutBlockChange(
                                                        ((CraftWorld) DataManager.WORLD).getHandle(), pc.a()
                                                );
                                                fixBreak.block = Block.getByCombinedId(
                                                        room.getWall().get().getBlocks().get(location)
                                                );
                                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(fixBreak);
                                            });
                                            cancel = true;
                                            break;
                                        }
                                    }
                                } else if (packet instanceof PacketPlayInUseItem) {
                                    PacketPlayInUseItem pc = (PacketPlayInUseItem) packet;
                                    Location location = new Location(DataManager.WORLD, pc.a().getX(), pc.a().getY(), pc.a().getZ());
                                    // Обработка кастомных событийных блоков
                                    for (BlockActions action : BlockActions.values()) {
                                        if (action.getAction().getLocation().equals(location)) {
                                            MinecraftServer.getServer().postToMainThread(() -> action.getAction().execute(player));
                                            cancel = true;
                                            break;
                                        }
                                    }
                                    // Обработка правого клика по блоку стены
                                    for (Room room : Room.values()) {
                                        if (room.getWall().isPresent() && room.getWall().get().getBlocks().containsKey(location)) {
                                            MinecraftServer.getServer().postToMainThread(() -> BuyInventoryHandler.open(room, player));
                                            cancel = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        } finally {
                            if (!cancel)
                                list.add(packet);
                        }
                    }
                }
        );
    }
}
