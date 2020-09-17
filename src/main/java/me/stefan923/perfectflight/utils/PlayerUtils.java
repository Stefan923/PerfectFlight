package me.stefan923.perfectflight.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface PlayerUtils {

    default List<Player> getNearbyPlayers(Player player, int distance) {
        List<Player> list = new ArrayList<>();

        for (Entity entity : player.getNearbyEntities(distance, distance, distance)) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;

                if (nearbyPlayer.canSee(player) && player.canSee(nearbyPlayer))
                    list.add(nearbyPlayer);
            }
        }

        return list;
    }

}
