package ru.func.wallaccessmanager.command;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.func.wallaccessmanager.WallAccessManager;
import ru.func.wallaccessmanager.data.DataManager;

/**
 * @author func 09.05.2020
 * @project WallAccessManager
 */
@AllArgsConstructor
public class AcceptRoomCommand implements CommandExecutor {

    private WallAccessManager plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings[0].equals("accept")) {
            Player player = Bukkit.getPlayer(strings[1]);
            Player senderPlayer = ((Player) sender);
            if(player == null) {
                sender.sendMessage("Игрок уже не в сети...");
                return true;
            }
            senderPlayer.showPlayer(plugin, player);
            player.showPlayer(plugin, senderPlayer);
            DataManager.PLAYER_ACCESS.get(senderPlayer.getUniqueId()).updateWalls(senderPlayer, player);
        }
        return true;
    }
}
