package dev.yudong.effectkill.effect.animation;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class SnowBa extends MainEffectKill{


	public SnowBa() {
		super(
				"snow",
				YAMLUtils.get("messages").getFile().exists()
						? (String) Utils.gfc("messages", "effectKill.snow.name")
						: "§e雪花飄飄",
				new ArrayList<>(Arrays.asList(
						"&7雪欸花飄飄，北風蕭蕭",
						"",
						"&7稀有度 » &b稀有"
				)),
				Heads.SNOW.getTexture()
		);
	}

	@Override
	public void update(User user) {
		Location location = user.getPlayer().getLocation();

		new BukkitRunnable() {
			private double rotationAngle = 0; //angle
			int max_height = 10;
			int tt = 0;
			double max_radius = 5;
			int lines = 10;
			double height_increment = 0.3;
			@Override
			public void run() {
				location.getWorld().playSound(location, Sound.AMBIENCE_RAIN, 1f, 1f);

				double radius_increment = max_radius / max_height;

				for (int l = 0; l < lines; l++) {
					for (double y = 0; y < max_height; y += height_increment) {
						double radius = y * radius_increment;

						double x = Math.cos(Math.toRadians(rotationAngle)) * radius;
						double z = Math.sin(Math.toRadians(rotationAngle)) * radius;

						Particle.play(location.clone().add(x, y, z), Effect.SNOW_SHOVEL);
					}
				}

				rotationAngle += 10;
				if (rotationAngle >= 360) {
					rotationAngle -= 360; //abg
				}

				tt++;
				if (tt >= 70) {
					cancel();
					//init all var
					this.rotationAngle = 0;
					this.max_height = 10;
					this.max_radius = 5;
					this.lines = 10;
					this.height_increment = 0.3;
					this.tt = 0;
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
}
