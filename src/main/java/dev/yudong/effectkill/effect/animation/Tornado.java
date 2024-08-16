package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;

public class Tornado extends MainEffectKill{

	public Tornado(){
		super(
				"tornado",
				YAMLUtils.get("messages").getFile().exists()
						? (String) Utils.gfc("messages", "ek.tornado")
						: "§e龍捲風",
				new ArrayList<>(Arrays.asList(
						"&7龍捲風，席捲而來！",
						"",
						"&7稀有度 » &2史詩"
				)),
				Heads.TORNADO.getTexture()
		);
	}

	@Override
	public void update(User user) {
		Location location = user.getPlayer().getLocation();
		new BukkitRunnable() {
			int angle = 0;
			@Override
			public void run() {
				location.getWorld().playSound(location, Sound.ENDERDRAGON_WINGS,0.2f,1f);
				int max_height = 7;
				double max_radius = 5;
				int lines = 5;
				double height_increasement = 0.25;
				double radius_increasement = max_radius / max_height;
				for (int l = 0; l < lines; l++) {
					for (double y = 0; y < max_height; y+=height_increasement ) {
						double radius = y * radius_increasement;
						double x = Math.cos(Math.toRadians(360/lines*l + y*30 - angle)) * radius;
						double z = Math.sin(Math.toRadians(360/lines*l + y*30 - angle)) * radius;
						Particle.play(location.clone().add(x,y,z), Effect.CLOUD);
					}
				}
				angle++;
				if(angle == 70) {
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 2, 0);
	}
}
