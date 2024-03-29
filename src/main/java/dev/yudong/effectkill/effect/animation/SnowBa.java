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

	public SnowBa(){
		super("ea",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.ea.name")):("§f雪花飄飄"), new ArrayList<>(Arrays.asList("&f雪欸花飄飄，北風蕭蕭",  "&8左鍵點擊來套用特效")), Heads.SNOW.getTexture());
	}

	@Override
	public void update(User user) {
		Location location = user.getPlayer().getLocation();
		new BukkitRunnable() {
			int angle = 0;
			@Override
			public void run() {
				location.getWorld().playSound(location, Sound.AMBIENCE_RAIN,0.2f,1f);
				int max_height = 7;
				double max_radius = 5;
				int lines = 10;
				double height_increasement = 0.3;
				double radius_increasement = max_radius / max_height;
				for (int l = 0; l < lines; l++) {
					for (double y = 0; y < max_height; y+=height_increasement ) {
						double radius = y * radius_increasement;
						double x = Math.cos(Math.toRadians(180/lines*l + y*30 + angle)) * radius;
						double z = Math.sin(Math.toRadians(180/lines*l + y*180 *  angle)) * radius;
						Particle.play(location.clone().add(x,y,z), Effect.SNOW_SHOVEL);
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
