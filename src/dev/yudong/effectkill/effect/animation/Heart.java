package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

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
		super("love",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.love.name")):("§c愛心"), new ArrayList<>(Arrays.asList("&e為他的死去以愛心帶過!",  "&8左鍵點擊來套用特效")), Heads.HEART.getTexture());
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		for (double height = 0.0; height < 1.0; height += 0.2) {
			Particle.play(loc.clone().add((double)MathUtils.randomRange(-1.0f, 1.0f), height, (double)MathUtils.randomRange(-1.0f, 1.0f)), Effect.HEART);
		}
	}
}
