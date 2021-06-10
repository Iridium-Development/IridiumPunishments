package com.iridium.iridiumpunishments.configs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Messages {
    public List<String> permBanMessage = Arrays.asList(
            "&c&lIridiumPunishments &8» &7Your account has been suspended!",
            "",
            "&cReason &8» &7%reason%",
            "&cBanned By &8» &7%banner%",
            "&cDate &8» &7%date%"
    );
    public List<String> tempBanMessage = Arrays.asList(
            "&c&lIridiumPunishments &8» &7Your account has been suspended!",
            "",
            "&cReason &8» &7%reason%",
            "&cBanned By &8» &7%banner%",
            "&cDate &8» &7%date%",
            "",
            "&7Expires in %days% days %hours% hours and %minutes% minutes"
    );
    public List<String> permMuteMessage = Collections.singletonList(
            "%prefix% &7You have been muted for %reason%!"
    );
    public List<String> tempMuteMessage = Arrays.asList(
            "%prefix% &7You have been muted for %reason%!",
            "&7Expires in %days% days %hours% hours and %minutes% minutes"
    );
    public List<String> kickMessage = Arrays.asList(
            "&c&lIridiumPunishments &8» &7You have been kicked from the server!",
            "",
            "&cReason &8» &7%reason%",
            "&cKicked By &8» &7%banner%"
    );
}
