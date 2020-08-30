package me.stefan923.perfectflight.utils;

import com.massivecraft.factions.*;
import com.massivecraft.factions.perms.Relation;
import me.stefan923.perfectflight.PerfectFlight;
import org.bukkit.entity.Player;

public class User {

    private final Player player;
    private final FPlayer fplayer;

    public User(PerfectFlight instance, Player player) {
        this.player = player;
        this.fplayer = FPlayers.getInstance().getByPlayer(player);

        instance.getUsers().put(player, this);
    }

    public void setFlight(boolean fly) {
        player.setAllowFlight(fly);
        player.setFlying(fly);
    }

    public void checkFactionsFly() {
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));

        if (canFlyOnTerritory(faction)) {
            if (player.isFlying()) {
                return;
            }

            setFlight(true);
        }

        if (player.isFlying()) {
            setFlight(false);
        }
    }

    public boolean canFlyOnTerritory(Faction faction) {
        if (fplayer.isAdminBypassing()) {
            return true;
        }

        if (faction == fplayer.getFaction()) {
            return canFlyOwn();
        }

        Relation relation = faction.getRelationTo(fplayer);
        return (faction.isWilderness() && canFlyWilderness()) || (faction.isWarZone() && canFlyWarZone())
                || (faction.isSafeZone() && canFlySafeZone()) || (relation == Relation.ENEMY && canFlyEnemy())
                || (relation == Relation.ALLY && canFlyAlly()) || (relation == Relation.TRUCE && canFlyTruce())
                || (relation == Relation.NEUTRAL && canFlyNeutral());
    }

    private boolean canFlyWilderness() {
        return player.hasPermission("perfectflight.factions.wilderness");
    }

    private boolean canFlySafeZone() {
        return player.hasPermission("perfectflight.factions.safezone");
    }

    private boolean canFlyWarZone() {
        return player.hasPermission("perfectflight.factions.warzone");
    }

    private boolean canFlyAlly() {
        return player.hasPermission("perfectflight.factions.ally");
    }

    private boolean canFlyTruce() {
        return player.hasPermission("perfectflight.factions.truce");
    }

    private boolean canFlyNeutral() {
        return player.hasPermission("perfectflight.factions.neutral");
    }

    private boolean canFlyEnemy() {
        return player.hasPermission("perfectflight.factions.enemy");
    }

    private boolean canFlyOwn() {
        return player.hasPermission("perfectflight.factions.own");
    }

}
