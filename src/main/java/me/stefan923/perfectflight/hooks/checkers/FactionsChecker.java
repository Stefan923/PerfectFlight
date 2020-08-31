package me.stefan923.perfectflight.hooks.checkers;

import com.massivecraft.factions.*;
import com.massivecraft.factions.perms.Relation;
import org.bukkit.entity.Player;

public class FactionsChecker extends AbstractChecker {

    @Override
    public boolean canFlyAtLocation(Player player) {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));

        if (fplayer.isAdminBypassing()) {
            return true;
        }

        if (faction == fplayer.getFaction()) {
            return canFlyOwn(player);
        }

        Object relation = faction.getRelationTo(fplayer);
        return (faction.isWilderness() && canFlyWilderness(player)) || (faction.isWarZone() && canFlyWarZone(player))
                || (faction.isSafeZone() && canFlySafeZone(player)) || (relation == Relation.ENEMY && canFlyEnemy(player))
                || (relation == Relation.ALLY && canFlyAlly(player)) || (relation == Relation.TRUCE && canFlyTruce(player))
                || (relation == Relation.NEUTRAL && canFlyNeutral(player));
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
