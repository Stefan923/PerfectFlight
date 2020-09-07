package me.stefan923.perfectflight.listeners;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private final PerfectFlight instance;

    public EntityDamageListener(PerfectFlight instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        FileConfiguration settings = instance.getSettingsManager().getConfig();
        if (settings.getBoolean("Fly Settings.Disable Fall Damage.Enabled")) {
            return;
        }

        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        if (instance.getUser((Player) entity).getNoFallDamageDuration() > System.currentTimeMillis()) {
            event.setCancelled(true);
        }
    }

}
