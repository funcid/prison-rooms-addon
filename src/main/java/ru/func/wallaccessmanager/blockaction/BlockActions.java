package ru.func.wallaccessmanager.blockaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import ru.func.wallaccessmanager.blockaction.chest.Chest;
import ru.func.wallaccessmanager.data.DataManager;

/**
 * @author func 10.05.2020
 * @project WallAccessManager
 */
@Getter
@AllArgsConstructor
public enum BlockActions {

    INVITE(new InvitePlayerAction()),
    WC_CHEST(new OpenChestAction(new Chest(1L, new Location(DataManager.WORLD, 253, 68, 279))));

    private BlockAction action;
}
