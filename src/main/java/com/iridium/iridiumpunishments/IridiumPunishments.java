package com.iridium.iridiumpunishments;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumpunishments.configs.Configuration;
import com.iridium.iridiumpunishments.configs.SQL;
import com.iridium.iridiumpunishments.managers.DatabaseManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.SQLException;

@Getter
public class IridiumPunishments extends IridiumCore {

    private DatabaseManager databaseManager;

    private Configuration configuration;
    private SQL sql;

    @Override
    public void onEnable() {
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

    }

    @Override
    public void loadConfigs() {
        this.configuration = getPersist().load(Configuration.class);
        this.sql = getPersist().load(SQL.class);
    }

    @Override
    public void saveConfig() {
        getPersist().save(sql);
    }
}
