package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;

public class FrostFlame extends MainEffectKill{

	public FrostFlame() {
		super("frostflame", YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.frostflame.name")):("§e死之焰"), new ArrayList<>(Arrays.asList("&6死亡之焰在古代是一種魔法結界，如今成了你送走對手的結尾!",  "&8左鍵點擊來套用特效")), Heads.FLAME.getTexture());
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		new BukkitRunnable() {
			double t = 0.0D;
			public void run() {
				t += 0.3;
				for (double phi = 0.0D; phi <= 6; phi += 1.5) {
					double x = 0.11D * (12.5 - t) * Math.cos(t + phi);
					double y = 0.23D * t;
					double z = 0.11D * (12.5 - t) * Math.sin(t + phi);
					loc.add(x, y, z);
					Particle.play(loc, Effect.FLAME);
					loc.subtract(x, y, z);

					if (t >= 12.5) {
						loc.add(x, y, z);
						if (phi > Math.PI) {
							cancel();
						} 
					} 
				} 
			}
		}.runTaskTimer(Main.getInstance(), 1L, 1L);
	}
}
