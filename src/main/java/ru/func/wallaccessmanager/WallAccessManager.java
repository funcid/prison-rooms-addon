package ru.func.wallaccessmanager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.func.wallaccessmanager.command.AcceptRoomCommand;
import ru.func.wallaccessmanager.data.DataManager;
import ru.func.wallaccessmanager.data.DataManagerImpl;
import ru.func.wallaccessmanager.listener.BuyInventoryHandler;
import ru.func.wallaccessmanager.listener.ExtraInventoryHandler;
import ru.func.wallaccessmanager.listener.PlayerJoinListener;
import ru.func.wallaccessmanager.room.Room;
import ru.func.wallaccessmanager.room.wall.Wall;

import java.util.stream.Stream;

/**
 * @author func 07.05.2020
 * @project WallAccessManager
 */
@Getter
public class WallAccessManager extends JavaPlugin {

    private DataManager dataManager;
    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("room").setExecutor(new AcceptRoomCommand(this));
        DataManager.WORLD.setAutoSave(false);
        Stream.of(Room.values())
                .filter(room -> room.getWall().isPresent())
                .map(room -> room.getWall().get())
                .forEach(Wall::init);
        dataManager = new DataManagerImpl(this);
        Bukkit.getPluginManager().registerEvents(new ExtraInventoryHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BuyInventoryHandler(this), this);
    }
}
