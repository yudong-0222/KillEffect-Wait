package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.bukkit.Effect;
import org.bukkit.Location;

import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import dev.yudong.effectkill.utils.maths.MathUtils;

public class Heart extends MainEffectKill{

	public Heart() {
		super(
				"love",
				YAMLUtils.get("messages").getFile().exists()
						? (String) Utils.gfc("messages", "effectKill.love.name")
						: "§e愛心",
				new ArrayList<>(Arrays.asList(
						"&7生成愛心粒子效果",
						"",
						"&7稀有度 » &7常見"
				)),
				Heads.HEART.getTexture()
		);
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		for (double height = 0.0; height < 2.0; height += 0.1) {
			Particle.play(loc.clone().add(MathUtils.randomRange(-1.2f, 1.6f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.HEART);
		}
	}
}
