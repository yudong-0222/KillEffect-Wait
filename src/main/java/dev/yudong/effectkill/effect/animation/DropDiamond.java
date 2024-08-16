package dev.yudong.effectkill.effect.animation;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.ItemFactory;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.*;

public class DropDiamond extends MainEffectKill {

    Random r = new Random();
    ArrayList<Item> items = new ArrayList<Item>();

    public DropDiamond() {
        super(
                "dropdiamond",
                YAMLUtils.get("messages").getFile().exists() ?
                        ((String) Utils.gfc("messages", "ek.dropdiamond")) :
                        ("§e鑽石碎落"),
                new ArrayList<>(Arrays.asList(
                        "&7招喚散落的鑽石碎片",
                        "",
                        "&7稀有度 » &2罕見"
                )),
                Heads.DIAMOND.getTexture()
        );
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < 30; i++) {
            Item ITEM = user.getPlayer().getWorld().dropItem(user.getPlayer().getLocation(), ItemFactory.create(Material.DIAMOND, (byte)0, UUID.randomUUID().toString()));
            ITEM.setPickupDelay(300);
            items.add(ITEM);
            ITEM.setVelocity(new Vector(r.nextDouble() - 0.5D, r.nextDouble() / 2.0D, r.nextDouble() - 0.5D));
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (Item i : items) {
                Particle.play(i.getLocation(), Effect.COLOURED_DUST);
                i.remove();
            }
        },50L);
    }
}
