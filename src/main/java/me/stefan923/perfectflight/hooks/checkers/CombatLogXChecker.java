package me.stefan923.perfectflight.hooks.checkers;

import com.SirBlobman.combatlogx.api.ICombatLogX;
import com.SirBlobman.combatlogx.api.utility.ICombatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CombatLogXChecker extends AbstractChecker {

    private final ICombatManager combatManager;

    public CombatLogXChecker() {
        ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
        combatManager = plugin.getCombatManager();
    }

    @Override
    public CheckResult canFlyAtLocation(Player player) {
        return combatManager.isInCombat(player) ? CheckResult.IN_COMBAT : CheckResult.ALLOWED;
    }

}
