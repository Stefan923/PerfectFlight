package me.stefan923.perfectflight.utils;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.hooks.checkers.AbstractChecker;
import me.stefan923.perfectflight.hooks.checkers.CheckResult;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class User {

    private final PerfectFlight instance;
    private final Player player;

    private boolean enableFly;
    private boolean isBypassing;
    private long noFallDamageDuration;

    public User(PerfectFlight instance, Player player) {
        this.instance = instance;
        this.player = player;
        this.enableFly = true;
        this.isBypassing = false;
        this.noFallDamageDuration = 0;

        instance.getUsers().put(player, this);
    }

    public void setFly(boolean enableFly) {
        this.enableFly = enableFly;
    }

    public boolean getFly() {
        return enableFly;
    }

    public boolean isBypassing() {
        return isBypassing;
    }

    public void setBypassing(boolean bypassing) {
        isBypassing = bypassing;
    }

    public long getNoFallDamageDuration() {
        return noFallDamageDuration;
    }

    public void setFlight(boolean fly, CheckResult checkResult) {
        FileConfiguration language = instance.getLanguageManager().getConfig();
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        if (fly) {
            if (!settings.getBoolean("Hooks.Factions.Auto-Enable")) {
                return;
            }

            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(MessageUtils.formatAll(language.getString(checkResult.getLangOption())));
            return;
        }

        if (!settings.getBoolean("Hooks.Factions.Auto-Enable")) {
            enableFly = false;
        }

        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(MessageUtils.formatAll(language.getString(checkResult.getLangOption())));

        if (settings.getBoolean("Fly Settings.Disable Fall Damage.Enabled")) {
            int duration = settings.getInt("Fly Settings.Disable Fall Damage.Duration In Seconds");
            noFallDamageDuration = System.currentTimeMillis() + duration * 1000;
            player.sendMessage(MessageUtils.formatAll(language.getString("Auto Flight Mode.No Fall Damage")
                    .replace("%duration%", MessageUtils.convertTime(duration, language))));
        }
    }

    public void setFlight(boolean fly) {
        FileConfiguration language = instance.getLanguageManager().getConfig();
        FileConfiguration settings = instance.getSettingsManager().getConfig();

        player.setAllowFlight(fly);
        player.setFlying(fly);

        if (fly) {
            player.sendMessage(MessageUtils.formatAll(language.getString("Command.Fly.Enabled")));
            return;
        }

        player.sendMessage(MessageUtils.formatAll(language.getString("Command.Fly.Disabled")));

        if (settings.getBoolean("Fly Settings.Disable Fall Damage.Enabled")) {
            int duration = settings.getInt("Fly Settings.Disable Fall Damage.Duration In Seconds");
            noFallDamageDuration = System.currentTimeMillis() + duration * 1000;
            player.sendMessage(MessageUtils.formatAll(language.getString("Auto Flight Mode.No Fall Damage")
                    .replace("%duration%", MessageUtils.convertTime(duration, language))));
        }
    }

    public void checkFly() {
        if (!enableFly) {
            return;
        }

        CheckResult checkResult = canFly();
        if (checkResult.canFly()) {
            if (!player.getAllowFlight()) {
                setFlight(true, checkResult);
            }
            return;
        }

        if (player.getAllowFlight()) {
            setFlight(false, checkResult);
        }
    }

    public CheckResult canFly() {
        if (isBypassing) {
            return CheckResult.ALLOWED;
        }

        for (AbstractChecker checker : instance.getCheckers()) {
            CheckResult checkResult = checker.canFlyAtLocation(player);
            if (checkResult != CheckResult.ALLOWED) {
                return checkResult;
            }
        }
        return CheckResult.ALLOWED;
    }
}
