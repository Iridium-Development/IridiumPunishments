package com.iridium.iridiumpunishments.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        IridiumPunishments.getInstance().getPunishmentManager().getActivePunishment(event.getPlayer().getUniqueId(), PunishmentType.BAN).ifPresent(punishment -> {
            LocalDateTime expires = punishment.getExpires();
            String banner = punishment.getPunisher() != null ? Bukkit.getOfflinePlayer(punishment.getPunisher()).getName() : null;
            if (expires == null) {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, StringUtils.color(String.join("\n", IridiumPunishments.getInstance().getMessages().permBanMessage)
                        .replace("%reason%", punishment.getReason())
                        .replace("%date%", punishment.getTime().format(DateTimeFormatter.ofPattern(IridiumPunishments.getInstance().getConfiguration().dateTimeFormat)))
                        .replace("%banner%", banner == null ? "Console" : banner)
                ));
            } else {
                LocalDateTime now = LocalDateTime.now();
                int days = (int) now.until(expires, ChronoUnit.DAYS);
                now = now.plusDays(days);
                int hours = (int) (now.until(expires, ChronoUnit.HOURS));
                now = now.plusHours(hours);
                int minutes = (int) (now.until(expires, ChronoUnit.MINUTES));
                now = now.plusMinutes(minutes);
                int seconds = (int) (now.until(expires, ChronoUnit.SECONDS));

                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, StringUtils.color(String.join("\n", IridiumPunishments.getInstance().getMessages().tempBanMessage)
                        .replace("%reason%", punishment.getReason())
                        .replace("%days%", String.valueOf(days))
                        .replace("%hours%", String.valueOf(hours))
                        .replace("%minutes%", String.valueOf(minutes))
                        .replace("%seconds%", String.valueOf(seconds))
                        .replace("%date%", punishment.getTime().format(DateTimeFormatter.ofPattern(IridiumPunishments.getInstance().getConfiguration().dateTimeFormat)))
                        .replace("%banner%", banner == null ? "Console" : banner)
                ));
            }
        });
    }

}
