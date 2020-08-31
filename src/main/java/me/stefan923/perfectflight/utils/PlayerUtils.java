package me.stefan923.perfectflight.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface PlayerUtils {

    default List<Player> getNearbyPlayers(Player player, int distance) {
        List<Player> list = new ArrayList<>();

        player.getNearbyEntities(distance, distance, distance)
                .stream().filter(entity -> entity instanceof Player)
                .forEach(entity -> list.add((Player) entity));

        return list;
    }

}
