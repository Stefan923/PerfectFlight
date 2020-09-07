package me.stefan923.perfectflight.utils;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.hooks.checkers.AbstractChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class User implements MessageUtils {

    private final PerfectFlight instance;
    private final Player player;

    private boolean enableFly;
    private long noFallDamageDuration;

    public User(PerfectFlight instance, Player player) {
        this.instance = instance;
        this.player = player;
        this.enableFly = true;
        this.noFallDamageDuration = 0;

        instance.getUsers().put(player, this);
    }

    public void setFly(boolean enableFly) {
        this.enableFly = enableFly;
    }

    public boolean getFly() {
        return enableFly;
    }

    public long getNoFallDamageDuration() {
        return noFallDamageDuration;
    }

    public void setFlight(boolean fly) {
        FileConfiguration language = instance.getLanguageManager().getConfig();
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        player.setAllowFlight(fly);
        player.setFlying(fly);

        if (fly) {
            player.sendMessage(formatAll(language.getString("Auto Flight Mode.Enabled")));
            return;
        }

        player.sendMessage(formatAll(language.getString("Auto Flight Mode.Disabled")));

        if (settings.getBoolean("Fly Settings.Disable Fall Damage.Enabled")) {
            int duration = settings.getInt("Fly Settings.Disable Fall Damage.Duration In Seconds");
            noFallDamageDuration = System.currentTimeMillis() + duration * 1000;
            player.sendMessage(formatAll(language.getString("Auto Flight Mode.No Fall Damage")
                    .replace("%duration%", convertTime(duration, language))));
        }
    }

    public void checkFly() {
        if (!enableFly) {
            return;
        }

        if (canFly()) {
            if (!player.getAllowFlight()) {
                setFlight(true);
            }
            return;
        }

        if (player.getAllowFlight()) {
            setFlight(false);
        }
    }

    public boolean canFly() {
        for (AbstractChecker checker : instance.getCheckers()) {
            if (!checker.canFlyAtLocation(player)) {
                return false;
            }
        }
        return true;
    }

}
