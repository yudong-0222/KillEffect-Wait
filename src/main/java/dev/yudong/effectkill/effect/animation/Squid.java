package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import dev.yudong.effectkill.utils.Particle;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import org.bukkit.scheduler.BukkitTask;

public class Squid extends MainEffectKill{
//	EntitySquid squid;
	private BukkitTask bk;
	private int i = 0;
	public Squid() {
		super(
				"squid",
				YAMLUtils.get("messages").getFile().exists()
						? (String) Utils.gfc("messages", "effectKill.squid.name")
						: "§e魷魚火箭",
				new ArrayList<>(Arrays.asList(
						"&7生成魷魚火箭，魷魚起飛!",
						"",
						"&7稀有度 » &6傳奇"
				)),
				Heads.SQUID.getTexture()
		);
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation().add(0, -0.3, 0);
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
			armor.setVisible(false);
			armor.setGravity(false);
			org.bukkit.entity.Entity e = user.getPlayer().getWorld().spawnEntity(loc, EntityType.SQUID);
			armor.setPassenger(e);
			as.add(armor);

			bk = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
				i++;
				org.bukkit.entity.Entity passenger =  armor.getPassenger();
				armor.eject();
				armor.teleport(armor.getLocation().add(0, 0.5, 0));
				armor.setPassenger(passenger);
				Particle.play(armor.getLocation().add(0.0, -0.2, 0.0), Effect.FLAME);
//				user.getPlayer().playSound(loc, Sound.CHICKEN_EGG_POP, 1f, 1f);
				user.playSoundNearby(user.getPlayer(), Sound.CHICKEN_EGG_POP,1f,1f);
				if (i == 25) {
					as.remove(armor);
					armor.remove();
					(e).remove();
					Particle.play(armor.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 1);
					bk.cancel();
					this.i = 0;
				}
			}, 1L, 0L);
		});
	}
}
