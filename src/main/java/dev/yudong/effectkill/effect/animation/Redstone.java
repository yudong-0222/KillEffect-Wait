package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;
import dev.yudong.effectkill.utils.maths.MathUtils;

public class Redstone extends MainEffectKill{

	public Redstone() {
		super(
				"redstone",
				YAMLUtils.get("messages").getFile().exists()
						? (String) Utils.gfc("messages", "effectKill.redstone.name")
						: "§e血爆",
				new ArrayList<>(Arrays.asList(
						"&7就像噴血一般，宣告死亡",
						"",
						"&7稀有度 » &b稀有"
				)),
				Heads.REDSTONE.getTexture()
		);
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		for (double height = 1.0; height <= 2.0; height += 0.8) {
			user.getPlayer().getWorld().playEffect(loc.clone().add((double)MathUtils.randomRange(-1.0f, 1.0f), height, (double)MathUtils.randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
			user.getPlayer().getWorld().playEffect(loc.clone().add((double)MathUtils.randomRange(1.0f, -1.0f), height, (double)MathUtils.randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		}
	}
}
