package dev.yudong.effectkill.utils;

import dev.yudong.effectkill.effect.animation.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.config.YAMLUtils;
import dev.yudong.effectkill.utils.inventory.CustomInventory;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class Manager {

	public static void buildConfigs(String... configs) {
		for (String config : configs) {
			if (config.equalsIgnoreCase("config")) {
				YAMLUtils yaml = YAMLUtils.get("config");
				if (yaml.getFile().exists()) return;
				yaml.build("This is the base config of EffectKill");
			} else if (config.equalsIgnoreCase("messages")) {
				YAMLUtils yaml = YAMLUtils.get("messages");
				if (yaml.getFile().exists()) return;
				yaml.build("Messages configuration");

				yaml.getConfig().set("prefix", "&cKillEffect &8» &r");
				yaml.getConfig().set("already", "%prefix% &c你已經套用此特效了！");
				yaml.getConfig().set("no-permission", "%prefix% &c你沒有權限這麼做！");
				yaml.getConfig().set("unlocked", "&a已擁有！");
				yaml.getConfig().set("locked", "&c尚未擁有！");
				yaml.getConfig().set("no-effect", "%prefix% &c你尚未擁有這項特效");
				yaml.getConfig().set("no-player", "%prefix% &c %player% 不存在！");
				yaml.getConfig().set("list-effect", "%prefix% &c該特效 &e%effectname% &c不存在！這是所有特效列表:&a ");
				yaml.getConfig().set("remove", "%prefix% &c你取消套用了擊殺特效 &7» &r %effectname%");
				yaml.getConfig().set("spawn", "%prefix% &e你套用了擊殺特效 &7»&r %effectname%！");
				yaml.getConfig().set("menu.effectKill", "&d選擇一個擊殺特效");
				yaml.getConfig().set("menu.spawn", "&a套用");
				yaml.getConfig().set("menu.despawn", "&c移除當前特效 &7» &r %effectname");
				yaml.getConfig().set("menu.effect", "&e使用中的擊殺特效 &7» &r");
				for (MainEffectKill effectKill : MainEffectKill.instanceList) {
					yaml.getConfig().set("effectKill."+effectKill.getName()+".name", effectKill.getDisplayName());
					yaml.getConfig().set("effectKill."+effectKill.getName()+".description", effectKill.getDescription());
				}
				yaml.save();
			}else throw new NullPointerException(config +" 尚未定義!");
		}
	}
	private final Map<String,MainEffectKill> effectKills = new HashMap<>();

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
				new DropDiamond(),
				new DropHeads(),
				new HeadExplode(),
				new SnowBa()
		));
		MainEffectKill.instanceList.forEach(o -> MainEffectKill.effectList.add(o.getClass())); }
	
	public Map<String, MainEffectKill> getEffectKills() {
		return effectKills;
	}

	@SuppressWarnings("unchecked")
	public CustomInventory buildInventory(User user) {
		return new CustomInventory(Main.getInstance(), "effectkill", false, null, Utils.colorize((String) Utils.gfc("messages", "menu.effectKill")), 45).advManipule(customInventory -> {
			for (int j = 0; j < 45; j++) {
				customInventory.addItem(ItemsUtils.create(Material.STAINED_GLASS_PANE, (byte) 7, " "), j);
			}
			if (user.getEffectKill() != null) {
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta skull = (SkullMeta) item.getItemMeta();
				skull.setDisplayName(Utils.colorize((String) Utils.gfc("messages", "menu.effect")) + user.getEffectKill().getDisplayName());
				skull.setOwner(user.getPlayer().getName());
				item.setItemMeta(skull);
				customInventory.addItem(item, 40);
				customInventory.addItem(ItemsUtils.create(Material.BARRIER, (byte)0, Utils.colorize((String) Utils.gfc("messages", "menu.despawn"))), 4);
			}

			int i = 0;
			int[] slots = {
					10, 11, 12, 13, 14, 15, 16,
					19, 20, 21, 22, 23, 24, 25,
					28, 29, 30, 31, 32, 33, 34,
					37, 38, 39, 40, 41, 42, 43
			};
			for (MainEffectKill effectKills : MainEffectKill.instanceList) {
				if (i >= slots.length) break;
				String itemName = (String) Utils.gfc("messages", "menu.spawn");
				if (user.getEffectKill() != null && user.getEffectKill().getName().equalsIgnoreCase(effectKills.getName()))
					itemName = (String) Utils.gfc("messages", "menu.despawn");

				String yes = (String) Utils.gfc("messages", "unlocked");
				String no = (String) Utils.gfc("messages", "locked");
				boolean getPerm = user.getPlayer().hasPermission("effectKill." + getEffectKills().get(effectKills.getName()) + ".name");
				List<String> lores = new ArrayList<>();
				lores.add(Utils.colorize(" "));
				List<String> descriptionLines = ((List<String>) Utils.gfc("messages", "effectKill."+effectKills.getName()+".description"))
						.stream()
						.map(Utils::colorize)
						.collect(Collectors.toList());

				lores.addAll(descriptionLines);
				lores.add(Utils.colorize(" "));
				lores.add(Utils.colorize("&f當前狀態 &7» &r" + (getPerm ? yes  : no)));

				customInventory.addItem(
						ItemsUtils.create(effectKills.getItemStack(), Utils.colorize(Utils.gfc("messages", "effectKill."+effectKills.getName()+".name") + ""), lores),
						slots[i]
				);
				i++;
			}
		});
	}
}
