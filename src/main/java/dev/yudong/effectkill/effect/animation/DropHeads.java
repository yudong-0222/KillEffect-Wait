package dev.yudong.effectkill.effect.animation;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.ItemFactory;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import dev.yudong.effectkill.utils.maths.MathUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class DropHeads extends MainEffectKill {

    Random r = new Random();
    ArrayList<Item> items = new ArrayList<Item>();

    public DropHeads() {
        super("dropfirework", YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.dropfirework.name")):("§e§l煙花派對"), new ArrayList<>(Arrays.asList("&6故人西辭黃鶴樓，煙花三月下揚州","§f此為 2023 年 9 月 §a尋找頭顱活動§f獎品",  "&8左鍵點擊來套用特效")), Heads.PRESENT.getTexture());
    }

    @Override
    public void update(User user) {
        Player p = user.getPlayer();
        Location loc = p.getLocation();
        p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 5f, 1f);
        for (int i = 0; i < 100; i++) {
            Item ITEM = user.getPlayer().getWorld().dropItem(user.getPlayer().getLocation(), ItemFactory.create(Material.FIREWORK, (byte)0, UUID.randomUUID().toString()));
            ITEM.setPickupDelay(300);
            items.add(ITEM);
            ITEM.setVelocity(new Vector(r.nextDouble() - 0.5D, r.nextDouble() * 2.0D, r.nextDouble() - 0.5D));
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (Item i : items) {
                Particle.play(i.getLocation(), Effect.FIREWORKS_SPARK);

                for (int x = 0; x < 4; x++) {
                    Particle.play(loc.clone().add(MathUtils.randomRange(-3.0F, 3.0F), r.nextDouble() * 5.7D, MathUtils.randomRange(-3.0F, 3.0F)), Effect.FIREWORKS_SPARK);
                    Particle.play(loc.clone().add(MathUtils.randomRange(-3.0F, 3.0F), r.nextDouble() * 5.7D, MathUtils.randomRange(-3.0F, 3.0F)), Effect.FIREWORKS_SPARK);
                    Particle.play(loc.clone().add(MathUtils.randomRange(-3.0F, 3.0F), r.nextDouble() * 5.7D, MathUtils.randomRange(-3.0F, 3.0F)), Effect.FIREWORKS_SPARK);
                    Particle.play(loc.clone().add(MathUtils.randomRange(-3.0F, 3.0F), r.nextDouble() * 5.7D, MathUtils.randomRange(-3.0F, 3.0F)), Effect.FIREWORKS_SPARK);
                }
                i.remove();
            }
        },10L);
    }
}
