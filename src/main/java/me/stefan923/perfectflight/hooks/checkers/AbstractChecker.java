package me.stefan923.perfectflight.hooks.checkers;

import org.bukkit.entity.Player;

public abstract class AbstractChecker {

    public abstract CheckResult canFlyAtLocation(Player player);

}
