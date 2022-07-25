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
		super("redstone",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.redstone.name")):("§4紅石方塊"), new ArrayList<>(Arrays.asList("&4紅石方塊", "&8左鍵點擊來套用特效")), Heads.REDSTONE.getTexture());
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		for (double height = 0.0; height < 1.0; height += 0.8) {
			user.getPlayer().getWorld().playEffect(loc.clone().add((double)MathUtils.randomRange(-1.0f, 1.0f), height, (double)MathUtils.randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
			user.getPlayer().getWorld().playEffect(loc.clone().add((double)MathUtils.randomRange(1.0f, -1.0f), height, (double)MathUtils.randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		}
	}
}
