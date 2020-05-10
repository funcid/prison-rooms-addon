package ru.func.wallaccessmanager.listener;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.func.wallaccessmanager.WallAccessManager;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private WallAccessManager plugin;

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        plugin.getDataManager().load(e.getPlayer());
    }
}
