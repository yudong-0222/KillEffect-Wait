package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import dev.yudong.effectkill.utils.maths.MathUtils;

public class Satan extends MainEffectKill{

	public Satan() {
		super("satan",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.satan.name")):("§c撒旦"), new ArrayList<>(Arrays.asList("&c撒旦要來囉 &c恐懼圍繞你心中...",   "&8左鍵點擊來套用特效")), Heads.DEVIL.getTexture());
	}
	
	@Override
	public void update(User user) {
	    Location loc = user.getPlayer().getLocation();
	    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
	    SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
	    skullMeta.setOwner(user.getPlayer().getName());
	    skull.setItemMeta(skullMeta);
	    ArmorStand armor = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
	    armor.setVisible(false);
	    armor.setCustomName("§4§l" + user.getPlayer().getName());
	    armor.setCustomNameVisible(true);
	    armor.setHelmet(skull);
	    armor.setGravity(false);
		as.add(armor);
	    new BukkitRunnable() {
	        int i = 0;
	        public void run() {
	        	i++;
	            for (int i = 0; i < 2; i++) {
	            	Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.5D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
	            	Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.5D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
	            	Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.5D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
	            	Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.7D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
	             } 
	            Particle.play(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.5D, MathUtils.randomRange(-0.8F, 0.8F)), Effect.FLAME); 
	            Particle.play(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.5D, MathUtils.randomRange(0.8F, -0.8F)), Effect.FLAME); 
	            Particle.play(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.5D, MathUtils.randomRange(-0.8F, 0.8F)), Effect.LAVADRIP); 
	            if(i == 100) {
					as.remove(armor);
	            	armor.remove();
	            	cancel();
	            }
	        }
	    }.runTaskTimer(Main.getInstance(), 1, 0);
	}
}
