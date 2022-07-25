package dev.yudong.effectkill.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.effect.animation.Tornado;
import dev.yudong.effectkill.effect.animation.DropSoup;
import dev.yudong.effectkill.effect.animation.FrostFlame;
import dev.yudong.effectkill.effect.animation.HeadExplode;
import dev.yudong.effectkill.effect.animation.Heart;
import dev.yudong.effectkill.effect.animation.Redstone;
import dev.yudong.effectkill.effect.animation.Satan;
import dev.yudong.effectkill.effect.animation.Wave;
import dev.yudong.effectkill.effect.animation.Squid;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.CustomInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager {
	public static void buildConfigs(String... configs) {
		for (String config : configs) {
			if (config.equalsIgnoreCase("config")) {
				YAMLUtils yaml = YAMLUtils.get("config");
				if (yaml.getFile().exists()) return;
				yaml.build("This is the base config of EffectKill");

				yaml.getConfig().set("menu-item.give-on-join", false);
				yaml.getConfig().set("menu-item.slot", 3);
				yaml.getConfig().set("menu-item.type", "NETHER_STAR");
				yaml.getConfig().set("menu-item.name", "&6&lEffectKill");
				yaml.getConfig().set("putEffectKiller", true);
				yaml.save();
			} else if (config.equalsIgnoreCase("messages")) {
				YAMLUtils yaml = YAMLUtils.get("messages");
				if (yaml.getFile().exists()) return;
				yaml.build("Messages configuration");

				yaml.getConfig().set("prefix", "&b[&cParticleKillEffect&b]");
				yaml.getConfig().set("no-permission", "%prefix% &c你沒有權限做這個");
				yaml.getConfig().set("no-player", "%prefix% &c %player% 不存在");
				yaml.getConfig().set("list-effect", "%prefix% &c該特效 &e%effectname% &c不存在. 這是所有特效列表:&a ");
				yaml.getConfig().set("remove", "%prefix% &c你移除了你的特效");
				yaml.getConfig().set("spawn", "%prefix% &e你選擇了 %effectname%");
				yaml.getConfig().set("menu.effectKill", "&d選擇一個擊殺特效吧!");
				yaml.getConfig().set("menu.spawn", "&a套用");
				yaml.getConfig().set("menu.despawn", "&c取消套用");
				yaml.getConfig().set("menu.effect", "&f你的特效 -> ");
				for (MainEffectKill effectKill : MainEffectKill.instanceList) {
					yaml.getConfig().set("effectKill."+effectKill.getName()+".name", effectKill.getDisplayName());
					yaml.getConfig().set("effectKill."+effectKill.getName()+".description", effectKill.getDescription());
				}
				yaml.save();
			}else throw new NullPointerException(config+" 尚未定義!");
		}
	}
	private Map<String,MainEffectKill> effectKills = new HashMap<>();

	public void loadMinions() {
		MainEffectKill.instanceList.addAll(Arrays.asList(
				new Heart(),
				new Wave(),
				new FrostFlame(),
				new Squid(),
				new Satan(),
				new DropSoup(),
				new Tornado(),
				new Redstone(),
				new HeadExplode()));
		MainEffectKill.instanceList.forEach(o -> MainEffectKill.effectList.add(o.getClass())); }
	
	public Map<String, MainEffectKill> getEffectKills() {
		return effectKills;
	}

	@SuppressWarnings("unchecked")
	public CustomInventory buildInventory(User user) {
		return new CustomInventory(Main.getInstance(), "effectkill", false, null, Utils.colorize((String) Utils.gfc("messages", "menu.effectKill")), 27).advManipule(customInventory -> {
			if (user.getEffectKill() != null) {
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta skull = (SkullMeta) item.getItemMeta();
				skull.setDisplayName(Utils.colorize((String) Utils.gfc("messages", "menu.effect")) + user.getEffectKill().getDisplayName());
				skull.setOwner(user.getPlayer().getName());
				item.setItemMeta(skull);
				customInventory.addItem(item, 0);
				customInventory.addItem(ItemsUtils.create(Material.BARRIER, (byte)0, Utils.colorize((String) Utils.gfc("messages", "menu.despawn"))), 4);
			}
			int i = 9;
			for (MainEffectKill effectKills : MainEffectKill.instanceList) {
				String itemName = (String) Utils.gfc("messages", "menu.spawn");
				if (user.getEffectKill() != null && user.getEffectKill().getName().equalsIgnoreCase(effectKills.getName()))
					itemName = (String) Utils.gfc("messages", "menu.despawn");

				List<String> lores = ((List<String>) Utils.gfc("messages", "effectKill."+effectKills.getName()+".description")).stream().map(Utils::colorize).collect(Collectors.toList());
				customInventory.addItem(
						ItemsUtils.create(effectKills.getItemStack(), Utils.colorize(itemName + " " + Utils.gfc("messages", "effectKill."+effectKills.getName()+".name")), lores)
						, i);
				i++;
			}
		});
	}
}
