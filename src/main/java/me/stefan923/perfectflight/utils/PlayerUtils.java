package me.stefan923.perfectflight.utils;

import me.stefan923.perfectflight.PerfectFlight;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class PlayerUtils {

    public static List<Player> getNearbyPlayers(Player player, int distance, PerfectFlight instance) {
        List<Player> list = new ArrayList<>();

        for (Entity entity : player.getNearbyEntities(distance, distance, distance)) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                User nearbyUser = instance.getUser(nearbyPlayer);

                if (!nearbyUser.isBypassing() && nearbyPlayer.canSee(player) && player.canSee(nearbyPlayer))
                    list.add(nearbyPlayer);
            }
        }

        return list;
    }

}
