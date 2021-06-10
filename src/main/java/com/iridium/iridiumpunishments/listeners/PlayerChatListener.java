package com.iridium.iridiumpunishments.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        IridiumPunishments.getInstance().getPunishmentManager().getActivePunishment(event.getPlayer().getUniqueId(), PunishmentType.MUTE).ifPresent(punishment -> {
            event.setCancelled(true);
            LocalDateTime expires = punishment.getExpires();
            if (expires == null) {
                event.getPlayer().sendMessage(StringUtils.color(String.join("\n", IridiumPunishments.getInstance().getMessages().permMuteMessage)
                        .replace("%prefix%", IridiumPunishments.getInstance().getConfiguration().prefix)
                        .replace("%reason%", punishment.getReason())
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

                event.getPlayer().sendMessage(StringUtils.color(String.join("\n", IridiumPunishments.getInstance().getMessages().tempMuteMessage)
                        .replace("%prefix%", IridiumPunishments.getInstance().getConfiguration().prefix)
                        .replace("%reason%", punishment.getReason())
                        .replace("%days%", String.valueOf(days))
                        .replace("%hours%", String.valueOf(hours))
                        .replace("%minutes%", String.valueOf(minutes))
                        .replace("%seconds%", String.valueOf(seconds))
                ));
            }
        });
    }

}
