package dev.yudong.effectkill.utils;

import com.google.common.collect.Maps;

import dev.yudong.effectkill.effect.MainEffectKill;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class User {

    private static Map<UUID, User> users = Maps.newHashMap();

    private UUID uuid;
    private MainEffectKill effectKill;

    public User(UUID uid) {
        this.uuid = uid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public MainEffectKill getEffectKill() {
        return effectKill;
    }

    public void setEffectKill(MainEffectKill effectKill) {
    	
        this.effectKill = effectKill;
    }

    public static User getUser(UUID uuid) {
        return users.computeIfAbsent(uuid, User::new);
    }

    public static Map<UUID, User> getUsers() {
        return users;
    }

    public static Stream<User> getAllUsers() {
        return users.values().stream();
    }
    public void playSoundNearby(Player player, Sound sound, float volume, float pitch) {
        Location playerLocation = player.getLocation();
        double radius = 5.0;

        for (Player nearbyPlayer : player.getWorld().getPlayers()) {
            if (nearbyPlayer.getLocation().distance(playerLocation) <= radius) {
                nearbyPlayer.playSound(nearbyPlayer.getLocation(), sound, volume, pitch);
            }
        }
    }

}
