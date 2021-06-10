package com.iridium.iridiumpunishments.managers;

import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.configs.SQL;
import com.iridium.iridiumpunishments.database.Punishment;
import com.iridium.iridiumpunishments.database.RevokedPunishment;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.DatabaseTypeUtils;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.logger.NullLogBackend;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DatabaseManager {

    private Dao<Punishment, Integer> punishmentsDao;
    private Dao<RevokedPunishment, Integer> revokedPunishmentsDao;

    @Getter(AccessLevel.NONE)
    private ConnectionSource connectionSource;

    public void init(IridiumPunishments iridiumPunishments) throws SQLException {
        LoggerFactory.setLogBackendFactory(new NullLogBackend.NullLogBackendFactory());

        SQL sqlConfig = iridiumPunishments.getSql();
        String databaseURL = getDatabaseURL(sqlConfig, iridiumPunishments);

        this.connectionSource = new JdbcConnectionSource(
                databaseURL,
                sqlConfig.username,
                sqlConfig.password,
                DatabaseTypeUtils.createDatabaseType(databaseURL)
        );

        this.punishmentsDao = DaoManager.createDao(connectionSource, Punishment.class);
        this.revokedPunishmentsDao = DaoManager.createDao(connectionSource, RevokedPunishment.class);

        TableUtils.createTableIfNotExists(connectionSource, Punishment.class);
        TableUtils.createTableIfNotExists(connectionSource, RevokedPunishment.class);
    }

    /**
     * Database connection String used for establishing a connection.
     *
     * @return The database URL String
     */
    private @NotNull String getDatabaseURL(SQL sqlConfig, IridiumPunishments iridiumPunishments) {
        switch (sqlConfig.driver) {
            case MYSQL:
            case MARIADB:
            case POSTGRESQL:
                return "jdbc:" + sqlConfig.driver.name().toLowerCase() + "://" + sqlConfig.host + ":" + sqlConfig.port + "/" + sqlConfig.database + "?useSSL=" + sqlConfig.useSSL;
            case SQLSERVER:
                return "jdbc:sqlserver://" + sqlConfig.host + ":" + sqlConfig.port + ";databaseName=" + sqlConfig.database;
            case H2:
                return "jdbc:h2:file:" + sqlConfig.database;
            case SQLITE:
                return "jdbc:sqlite:" + new File(iridiumPunishments.getDataFolder(), sqlConfig.database + ".db");
            default:
                throw new UnsupportedOperationException("Unsupported driver (how did we get here?): " + sqlConfig.driver.name());
        }
    }

    /**
     * Gets all punishments given by this user
     *
     * @param uuid The user's UUID
     * @return A list of all punishments given by this user
     */
    public CompletableFuture<List<Punishment>> getPunishmentsByUser(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return punishmentsDao.queryBuilder().where().eq("punisher", uuid).query();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return new ArrayList<Punishment>();
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return Collections.emptyList();
        });
    }

    /**
     * Gets all punishments recieved by this user
     *
     * @param uuid The user's UUID
     * @return A list of all punishments this user has recieved
     */
    public CompletableFuture<List<Punishment>> getPunishmentsFromUser(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return punishmentsDao.queryBuilder().where().eq("user", uuid).query();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return new ArrayList<Punishment>();
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return Collections.emptyList();
        });
    }

    /**
     * Saves a punishment to the database
     *
     * @param punishment The punishment we are saving
     * @return A completable future for when the punishment is saved
     */
    public CompletableFuture<Void> savePunishment(Punishment punishment) {
        return CompletableFuture.runAsync(() -> {
            try {
                punishmentsDao.createOrUpdate(punishment);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    /**
     * Saves a revokedPunishment to the database
     *
     * @param revokedPunishment The revokedPunishment we are saving
     * @return A completable future for when the revokedPunishment is saved
     */
    public CompletableFuture<Void> saveRevokedPunishment(RevokedPunishment revokedPunishment) {
        return CompletableFuture.runAsync(() -> {
            try {
                revokedPunishmentsDao.createOrUpdate(revokedPunishment);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    /**
     * Gets the RevokedPunishment related to a punishment if present
     *
     * @param punishment The punishment
     * @return an Optional of the Revoked Punishment
     */
    public CompletableFuture<Optional<RevokedPunishment>> getRevokedPunishment(Punishment punishment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Optional.ofNullable(revokedPunishmentsDao.queryBuilder().where().eq("punishment_id", punishment).queryForFirst());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return Optional.<RevokedPunishment>empty();
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return Optional.empty();
        });
    }

    /**
     * Deletes a permission from the database
     *
     * @param punishment The permission we are deleting
     * @return A completable future for when the punishment is deleted
     */
    public CompletableFuture<Void> deletePunishment(Punishment punishment) {
        return CompletableFuture.runAsync(() -> {
            try {
                punishmentsDao.delete(punishment);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }
}
