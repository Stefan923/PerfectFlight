package me.stefan923.perfectflight.listeners;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    private final PerfectFlight instance;

    public PlayerTeleportListener(PerfectFlight instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (instance.getSettingsManager().getConfig().getBoolean("Hooks.Factions.Auto-Enable")) {
            User user = instance.getUser(event.getPlayer());
            user.checkFly();
        }
    }

}
