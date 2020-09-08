package me.stefan923.perfectflight.hooks.checkers;

import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Relation;
import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FactionsChecker extends AbstractChecker implements PlayerUtils {

    private final PerfectFlight instance;
    private final FileConfiguration settings;

    public FactionsChecker(PerfectFlight instance) {
        this.instance = instance;
        this.settings = instance.getSettingsManager().getConfig();
    }

    @Override
    public CheckResult canFlyAtLocation(Player player) {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));

        if (fplayer.isAdminBypassing()) {
            Bukkit.broadcastMessage(player.getName() + " 1");
            return CheckResult.ALLOWED;
        }

        // Checks if player can fly near nearby players.
        if (settings.getBoolean("Hooks.Factions.Auto-Disable Near Enemies.Enabled") && !checkNearbyPlayers(player, fplayer)) {
            return CheckResult.NEARBY_ENEMIES;
        }

        if (faction == fplayer.getFaction()) {
            return canFlyOwn(player) ? CheckResult.ALLOWED : CheckResult.PRIVATE_TERRITORY;
        }

        Object relation = faction.getRelationTo(fplayer);
        return (faction.isWilderness() && canFlyWilderness(player)) || (faction.isWarZone() && canFlyWarZone(player))
                || (faction.isSafeZone() && canFlySafeZone(player)) || (relation == com.massivecraft.factions.struct.Relation.ENEMY && canFlyEnemy(player))
                || (relation == com.massivecraft.factions.struct.Relation.ALLY && canFlyAlly(player)) || (relation == com.massivecraft.factions.struct.Relation.TRUCE && canFlyTruce(player))
                || (relation == Relation.NEUTRAL && canFlyNeutral(player)) ? CheckResult.ALLOWED : CheckResult.PRIVATE_TERRITORY;
    }

    private boolean checkNearbyPlayers(Player player, FPlayer fplayer) {
        boolean canFly = true;

        FPlayers fpInstance = FPlayers.getInstance();
        for (Player nearbyPlayer : getNearbyPlayers(player, settings.getInt("Hooks.Factions.Auto-Disable Near Enemies.Check Radius"))) {
            if (fplayer.getRelationTo(fpInstance.getByPlayer(nearbyPlayer)) == com.massivecraft.factions.struct.Relation.ENEMY) {
                if (nearbyPlayer.getAllowFlight()) {
                    instance.getUser(nearbyPlayer).setFlight(false, CheckResult.NEARBY_ENEMIES);
                }
                canFly = false;
            }
        }

        return canFly;
    }


    private boolean canFlyWilderness(Player player) {
        return player.hasPermission("perfectflight.factions.wilderness");
    }

    private boolean canFlySafeZone(Player player) {
        return player.hasPermission("perfectflight.factions.safezone");
    }

    private boolean canFlyWarZone(Player player) {
        return player.hasPermission("perfectflight.factions.warzone");
    }

    private boolean canFlyAlly(Player player) {
        return player.hasPermission("perfectflight.factions.ally");
    }

    private boolean canFlyTruce(Player player) {
        return player.hasPermission("perfectflight.factions.truce");
    }

    private boolean canFlyNeutral(Player player) {
        return player.hasPermission("perfectflight.factions.neutral");
    }

    private boolean canFlyEnemy(Player player) {
        return player.hasPermission("perfectflight.factions.enemy");
    }

    private boolean canFlyOwn(Player player) {
        return player.hasPermission("perfectflight.factions.own");
    }

}
