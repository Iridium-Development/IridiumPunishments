package com.iridium.iridiumpunishments.commands;

import com.iridium.iridiumpunishments.IridiumPunishments;
import com.iridium.iridiumpunishments.PunishmentType;
import com.iridium.iridiumpunishments.database.Punishment;
import com.iridium.iridiumpunishments.database.RevokedPunishment;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UnBanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("iridiumpunishments.ban")) {
            if (args.length >= 1) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (player.hasPlayedBefore()) {
                    String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    UUID uuid = sender instanceof Player ? ((Player) sender).getUniqueId() : null;

                    Optional<Punishment> punishmentOptional = IridiumPunishments.getInstance().getPunishmentManager().getActivePunishment(player.getUniqueId(), PunishmentType.BAN);
                    if (punishmentOptional.isPresent()) {
                        IridiumPunishments.getInstance().getDatabaseManager().saveRevokedPunishment(new RevokedPunishment(punishmentOptional.get(), uuid, reason));
                    } else {

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
