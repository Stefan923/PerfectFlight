package me.stefan923.perfectflight.hooks.checkers.factions;

import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.hooks.checkers.CheckResult;
import me.stefan923.perfectflight.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class SaberFactionsChecker extends AbstractFactionsChecker {

    public SaberFactionsChecker(PerfectFlight instance) {
        super(instance);
    }

    @Override
    public CheckResult canFlyAtLocation(Player player) {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));

        if (fplayer.isAdminBypassing()) {
            return CheckResult.ALLOWED;
        }

        // Checks if player can fly near nearby players.
        if (settings.getBoolean("Hooks.Factions.Auto-Disable Near Enemies.Enabled") && !checkNearbyPlayers(player, fplayer)) {
            return CheckResult.NEARBY_ENEMIES;
        }

        if (faction.getAccess(fplayer, PermissableAction.FLY) == Access.ALLOW) {
            return CheckResult.ALLOWED;
        }

        Object relation = faction.getRelationTo(fplayer);
        return ((faction.isWilderness() && canFlyWilderness(player)) || (faction.isWarZone() && canFlyWarZone(player))
                || (faction.isSafeZone() && canFlySafeZone(player)) || (relation == Relation.ENEMY && canFlyEnemy(player))
                || (relation == Relation.ALLY && canFlyAlly(player)) || (relation == Relation.TRUCE && canFlyTruce(player))
                || (relation == Relation.NEUTRAL && canFlyNeutral(player)) || (faction == fplayer.getFaction() && canFlyOwn(player)))
                ? CheckResult.ALLOWED : CheckResult.PRIVATE_TERRITORY;
    }

}
