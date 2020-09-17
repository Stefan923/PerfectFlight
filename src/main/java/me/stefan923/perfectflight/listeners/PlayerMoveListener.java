package me.stefan923.perfectflight.listeners;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final PerfectFlight instance;

    public PlayerMoveListener(PerfectFlight instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        User user = instance.getUser(event.getPlayer());
        user.checkFly();
    }

}
