package com.iridium.iridiumpunishments;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumpunishments.configs.Configuration;
import com.iridium.iridiumpunishments.configs.SQL;
import com.iridium.iridiumpunishments.listeners.InventoryClickListener;
import com.iridium.iridiumpunishments.listeners.PlayerLoginListener;
import com.iridium.iridiumpunishments.managers.DatabaseManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.SQLException;

@Getter
public class IridiumPunishments extends IridiumCore {

    private static IridiumPunishments instance;

    private DatabaseManager databaseManager;

    private Configuration configuration;
    private SQL sql;

    @Override
    public void onEnable() {
        instance = this;
        this.databaseManager = new DatabaseManager();
        try {
            databaseManager.init(this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        super.onEnable();
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
    }

    @Override
    public void loadConfigs() {
        this.configuration = getPersist().load(Configuration.class);
        this.sql = getPersist().load(SQL.class);
    }

    @Override
    public void saveConfigs() {
        getPersist().save(configuration);
        getPersist().save(sql);
    }

    public static IridiumPunishments getInstance() {
        return instance;
    }
}
