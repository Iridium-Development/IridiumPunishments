package com.iridium.iridiumpunishments.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import com.iridium.iridiumpunishments.database.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("iridiumpunishments.ban")) {
            if (args.length >= 1) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    UUID uuid = sender instanceof Player ? ((Player) sender).getUniqueId() : null;

                    IridiumPunishments.getInstance().getDatabaseManager().savePunishment(new Punishment(player.getUniqueId(), uuid, null, reason, PunishmentType.BAN));

                    player.kickPlayer(StringUtils.color(String.join("\n", IridiumPunishments.getInstance().getMessages().permBanMessage)
                            .replace("%reason%", reason)
                            .replace("%banner%", sender.getName())
                            .replace("%date%", LocalDateTime.now().format(DateTimeFormatter.ofPattern(IridiumPunishments.getInstance().getConfiguration().dateTimeFormat)))
                    ));
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
