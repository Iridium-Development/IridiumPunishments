package com.iridium.iridiumpunishments.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import com.iridium.iridiumpunishments.database.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

public class MuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("iridiumpunishments.mute")) {
            if (args.length >= 1) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (player.hasPlayedBefore()) {
                    String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    UUID uuid = sender instanceof Player ? ((Player) sender).getUniqueId() : null;

                    IridiumPunishments.getInstance().getDatabaseManager().savePunishment(new Punishment(player.getUniqueId(), uuid, null, reason, PunishmentType.MUTE));

                    if(player.getPlayer()!=null){
                        player.getPlayer().sendMessage(StringUtils.color(String.join("\n", IridiumPunishments.getInstance().getMessages().permMuteMessage)
                                .replace("%prefix%", IridiumPunishments.getInstance().getConfiguration().prefix)
                                .replace("%reason%", reason)
                        ));
                    }
                } else {
                    sender.sendMessage("player null");
                }
            } else {
                sender.sendMessage("/kick <player>");
            }
        } else {
            sender.sendMessage("no Perms");
        }
        return true;
    }
}
