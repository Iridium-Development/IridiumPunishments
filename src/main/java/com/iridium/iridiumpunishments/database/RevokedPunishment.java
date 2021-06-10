package com.iridium.iridiumpunishments.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "revoked_punishments")
public class RevokedPunishment {
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @DatabaseField(columnName = "punishment_id", canBeNull = false, foreign = true)
    @NotNull
    private Punishment punishment;

    @DatabaseField(columnName = "revoker")
    private UUID revoker;

    @DatabaseField(columnName = "time", canBeNull = false)
    private long time;

    @DatabaseField(columnName = "reason", canBeNull = false)
    @NotNull
    private String reason;

    public RevokedPunishment(@NotNull Punishment punishment, UUID revoker, @NotNull String reason) {
        this.punishment = punishment;
        this.revoker = revoker;
        setTime(LocalDateTime.now());
        this.reason = reason;
    }

    public LocalDateTime getTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this.time), ZoneId.systemDefault());
    }

    public void setTime(LocalDateTime time) {
        this.time = ZonedDateTime.of(time, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
