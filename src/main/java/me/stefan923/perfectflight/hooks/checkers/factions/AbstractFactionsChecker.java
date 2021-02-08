package me.stefan923.perfectflight.hooks.checkers.factions;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.hooks.checkers.AbstractChecker;
import me.stefan923.perfectflight.hooks.checkers.CheckResult;
import me.stefan923.perfectflight.utils.PlayerUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class AbstractFactionsChecker extends AbstractChecker {

    protected final PerfectFlight instance;
    protected final FileConfiguration settings;

    public AbstractFactionsChecker(PerfectFlight instance) {
        this.instance = instance;
        this.settings = instance.getSettingsManager().getConfig();
    }

    protected boolean checkNearbyPlayers(Player player, FPlayer fplayer) {
        boolean canFly = true;

        FPlayers fpInstance = FPlayers.getInstance();
        for (Player nearbyPlayer : PlayerUtils.getNearbyPlayers(player, settings.getInt("Hooks.Factions.Auto-Disable Near Enemies.Check Radius"), instance)) {
            if (fplayer.getRelationTo(fpInstance.getByPlayer(nearbyPlayer)) == com.massivecraft.factions.struct.Relation.ENEMY) {
                if (nearbyPlayer.getAllowFlight()) {
                    instance.getUser(nearbyPlayer).setFlight(false, CheckResult.NEARBY_ENEMIES);
                }
                canFly = false;
            }
        }

        return canFly;
    }

    protected boolean canFlyWilderness(Player player) {
        return player.hasPermission("perfectflight.factions.wilderness");
    }

    protected boolean canFlySafeZone(Player player) {
        return player.hasPermission("perfectflight.factions.safezone");
    }

    protected boolean canFlyWarZone(Player player) {
        return player.hasPermission("perfectflight.factions.warzone");
    }

    protected boolean canFlyAlly(Player player) {
        return player.hasPermission("perfectflight.factions.ally");
    }

    protected boolean canFlyTruce(Player player) {
        return player.hasPermission("perfectflight.factions.truce");
    }

    protected boolean canFlyNeutral(Player player) {
        return player.hasPermission("perfectflight.factions.neutral");
    }

    protected boolean canFlyEnemy(Player player) {
        return player.hasPermission("perfectflight.factions.enemy");
    }

    protected boolean canFlyOwn(Player player) {
        return player.hasPermission("perfectflight.factions.own");
    }

}
