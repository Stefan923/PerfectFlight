package me.stefan923.perfectflight.utils;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.hooks.checkers.AbstractChecker;
import org.bukkit.entity.Player;

public class User {

    private final PerfectFlight instance;
    private final Player player;

    public User(PerfectFlight instance, Player player) {
        this.instance = instance;
        this.player = player;

        instance.getUsers().put(player, this);
    }

    public void setFlight(boolean fly) {
        player.setAllowFlight(fly);
        player.setFlying(fly);
    }

    public void checkFly() {
        for (AbstractChecker checker : instance.getCheckers()) {
            if (!checker.canFlyAtLocation(player)) {
                if (player.isFlying()) {
                    setFlight(false);
                }
                return;
            }
        }

        if (player.isFlying()) {
            return;
        }

        setFlight(true);
    }

}
