package ru.func.wallaccessmanager.action;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.func.wallaccessmanager.data.DataManager;

/**
 * @author func 10.05.2020
 * @project WallAccessManager
 */
public class InvitePlayerAction implements BlockAction {

    private Location location = new Location(DataManager.WORLD, 254, 68, 273);

    @Override
    public void execute(Player player) {
        // открытие таблички
        // дальше происходит если игрок указан верно
        String INVITE = "{\"text\":\"Вас пригласил в дом NAME. \"," +
                "\"extra\":[{\"text\":\"§bЧто бы принять нажмите сюда.\"," +
                "\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§c*Клик*!\"}," +
                "\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/room accept NAME\"}}]}";
        ((CraftPlayer) Bukkit.getPlayer("func1")).getHandle().playerConnection.sendPacket(
                new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(
                        INVITE.replace("NAME", player.getName())
                ))
        );
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
