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

public class HeadExplode extends MainEffectKill{

	public HeadExplode() {
		super("headexplode",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.headexplode.name")):("§6頭顱火箭"), new ArrayList<>(Arrays.asList("&f在原地生成&b頭顱火箭.",  "&8左鍵點擊來套用特效")), Heads.ANGRY.getTexture());
	}
	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
		skullMeta.setOwner(user.getPlayer().getName());
		skull.setItemMeta(skullMeta);
		ArmorStand armor = (ArmorStand)loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
		armor.setVisible(false);
		armor.setCustomName("§c§l" + user.getPlayer().getName());
		armor.setCustomNameVisible(true);
		armor.setHelmet(skull);
		armor.setGravity(false);
		as.add(armor);
		new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				i++;
				armor.teleport(armor.getLocation().add(0,0.5,0));  
				armor.setHeadPose(armor.getHeadPose().add(0.0, 1, 0.0));
				Particle.play(armor.getLocation().add(0.0, -0.2, 0.0), Effect.FLAME);
				if(i == 20) {
					as.remove(armor);
					armor.remove();
					Particle.play(armor.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 1);
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 1, 0);
	}
}
