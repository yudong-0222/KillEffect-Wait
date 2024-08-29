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

public class Wave extends MainEffectKill{

	public Wave() {
		super(
				"wave",
				YAMLUtils.get("messages").getFile().exists()
						? (String) Utils.gfc("messages", "effectKill.wave.name")
						: "§e水之世紀",
				new ArrayList<>(Arrays.asList(
						"&7一段不為人知的水魔法...",
						"",
						"&7稀有度 » &2史詩"
				)),
				Heads.WAVE.getTexture()
		);
	}

	@Override
	public void update(User user) {
		Player player = user.getPlayer();
		Location loc = player.getLocation();
		new BukkitRunnable() {
			double t = Math.PI/4;
			public void run(){
				t = t + 0.1*Math.PI;
				for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
					double x = t*Math.cos(theta);
					double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					double z = t*Math.sin(theta);
					loc.add(x,y,z);
					Particle.play(loc, Effect.WATERDRIP);
					Particle.play(loc, Effect.SNOW_SHOVEL);
					loc.subtract(x,y,z);
					theta = theta + Math.PI/64;
				}    
				if (t > 8){
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 4, 0);
	}
}
