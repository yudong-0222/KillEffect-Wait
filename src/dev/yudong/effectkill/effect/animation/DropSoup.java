package dev.yudong.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.ItemFactory;
import dev.yudong.effectkill.utils.Particle;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.Heads;

public class DropSoup extends MainEffectKill{

	Random r = new Random();
	ArrayList<Item> items = new ArrayList<Item>();

	public DropSoup() {
		super("dropsoup", YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.dropsoup.name")):("§e湯品開灑"), new ArrayList<>(Arrays.asList("&a和尚端湯上塔 塔滑湯灑湯燙塔 和尚端塔上湯 湯滑塔灑塔燙湯.",  "&8左鍵點擊來套用特效")), Heads.SOUP.getTexture());
	}

	@Override
	public void update(User user) {
		for (int i = 0; i < 30; i++) {
			Item ITEM = user.getPlayer().getWorld().dropItem(user.getPlayer().getLocation(), ItemFactory.create(Material.MUSHROOM_SOUP, (byte)0, UUID.randomUUID().toString()));
			ITEM.setPickupDelay(300);
			items.add(ITEM);
			ITEM.setVelocity(new Vector(r.nextDouble() - 0.5D, r.nextDouble() / 2.0D, r.nextDouble() - 0.5D));
		} 
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable(){
			public void run() {
				for (Item i : items) {
					Particle.play(i.getLocation(), Effect.COLOURED_DUST);
					i.remove();
				} 
			}
		},50L);
	}
}
