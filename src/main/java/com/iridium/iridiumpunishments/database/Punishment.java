package com.iridium.iridiumpunishments.database;

import com.iridium.iridiumpunishments.PunishmentType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "punishments")
public class Punishment {
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @DatabaseField(columnName = "user", canBeNull = false)
    @NotNull
    private UUID user;

    @DatabaseField(columnName = "punisher")
    private UUID punisher;

    @DatabaseField(columnName = "time", canBeNull = false)
    private long time;

    @DatabaseField(columnName = "expires")
    @Nullable
    private Long expires;

    @DatabaseField(columnName = "reason", canBeNull = false)
    @NotNull
    private String reason;

    @DatabaseField(columnName = "type", canBeNull = false)
    @NotNull
    @Setter(AccessLevel.PRIVATE)
    private PunishmentType punishmentType;

    public Punishment(@NotNull UUID user, UUID punisher, LocalDateTime expires, @NotNull String reason, @NotNull PunishmentType punishmentType) {
        this.user = user;
        this.punisher = punisher;
        setExpires(expires);
        setTime(LocalDateTime.now());
        this.reason = reason;
        this.punishmentType = punishmentType;
    }

    public LocalDateTime getTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this.time), ZoneId.systemDefault());
    }

    public void setTime(LocalDateTime time) {
        this.time = ZonedDateTime.of(time, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime getExpires() {
        if (expires == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(expires), ZoneId.systemDefault());
    }

    public void setExpires(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            this.expires = null;
        } else {
            this.expires = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }
}
