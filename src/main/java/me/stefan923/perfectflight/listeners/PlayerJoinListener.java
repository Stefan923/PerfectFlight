package me.stefan923.perfectflight.listeners;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PerfectFlight instance;

    public PlayerJoinListener(PerfectFlight instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = instance.getUser(event.getPlayer());

        if (instance.getSettingsManager().getConfig().getBoolean("Hooks.Factions.Auto-Fly.Enable")) {
            user.checkFactionsFly();
        }
    }

}
