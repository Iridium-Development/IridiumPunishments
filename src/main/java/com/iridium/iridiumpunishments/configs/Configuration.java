package com.iridium.iridiumpunishments.configs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Configuration {
    public String prefix = "<GRADIENT:647DEE>&lIridiumPunishments</GRADIENT:7F53AC> &8»";
    public String dateTimeFormat = "EEEE, MMMM dd HH:mm:ss";
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
}
