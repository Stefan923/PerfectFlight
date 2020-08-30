package me.stefan923.perfectflight.listeners;

import me.stefan923.perfectflight.PerfectFlight;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PerfectFlight instance;

    public PlayerQuitListener(PerfectFlight instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        instance.getUsers().remove(event.getPlayer());
    }

}
