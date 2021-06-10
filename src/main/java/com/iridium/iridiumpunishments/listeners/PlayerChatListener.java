package com.iridium.iridiumpunishments.listeners;

import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        IridiumPunishments.getInstance().getPunishmentManager().getActivePunishment(event.getPlayer().getUniqueId(), PunishmentType.MUTE).ifPresent(punishment -> {
            event.setCancelled(true);
        });
    }

}
