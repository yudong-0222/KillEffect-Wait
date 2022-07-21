package dev.waitpvp.ha.config;

import dev.waitpvp.ha.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class Config implements Listener {
    private Main plugin;
    public Config(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void killpr(final EntityDeathEvent e) {
        final Player killer = e.getEntity().getKiller();
        final EntityType enti = e.getEntityType();
        final FileConfiguration config = this.plugin.getConfig();
        final String path = "FIREWORK";
        final String path2 = "FIREWORK.enable";
        if (config.getString(path2).equalsIgnoreCase("true") && killer != null &&  killer.getType().equals(EntityType.PLAYER)
                && enti.equals(EntityType.PLAYER)){
           final Firework f = (Firework) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.FIREWORK);
            FireworkMeta fm = f.getFireworkMeta();
            fm.setPower(3);
            f.getFireworkMeta();
            }

        }
    }
