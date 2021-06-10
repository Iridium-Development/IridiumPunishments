package com.iridium.iridiumpunishments.managers;

import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import com.iridium.iridiumpunishments.database.Punishment;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class PunishmentManager {

    public Optional<Punishment> getActivePunishment(UUID uuid, PunishmentType punishmentType) {
        return IridiumPunishments.getInstance().getDatabaseManager().getPunishmentsFromUser(uuid).join().stream().filter(punishment ->
                punishment.getPunishmentType() == punishmentType && (punishment.getExpires() == null || punishment.getExpires().isAfter(LocalDateTime.now()))
        ).findFirst();
    }

}
