package dev.yudong.effectkill.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.database.FlatFile;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.ItemsUtils;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import dev.yudong.effectkill.utils.config.YAMLUtils;

public class Events implements Listener{

	private ItemStack item = YAMLUtils.get("config").getFile().exists() ? (ItemsUtils.create(
			Material.getMaterial((String)Utils.gfc("config", "menu-item.type")), (byte)0,
			Utils.colorize((String)Utils.gfc("config", "menu-item.name")), new String[0])):
			(ItemsUtils.create(
					Material.getMaterial("NETHER_STAR"), (byte)0,
					Utils.colorize("&6&lEffectKill"), new String[0]));

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (Main.getInstance().giveItem)
			event.getPlayer().getInventory().setItem((
					(Integer)Utils.gfc("config", "menu-item.slot")).intValue(), item);
		FlatFile.getValue(event.getPlayer().getUniqueId());
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		FlatFile.setValue(player.getUniqueId());
	}
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack() != null && event.getItemDrop().getItemStack().isSimilar(item))
			event.setCancelled(true);
	}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if (event.getItem().getItemStack() != null && event.getItem().getItemStack().isSimilar(item))
			event.setCancelled(true);
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() != null && event.getItem().isSimilar(this.item)) {
			event.setCancelled(true);
			Main.getManager().buildInventory(User.getUser(event.getPlayer().getUniqueId())).open(new Player[] { event.getPlayer() });
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory() == null || event.getCurrentItem() == null || event.getWhoClicked() == null) {
			return;
		}
		if (event.getCurrentItem().isSimilar(item)) {
			event.setCancelled(true);
		}
		if (event.getView().getTitle().equalsIgnoreCase(Utils.colorize((String)Utils.gfc("messages", "menu.effectKill")))) {
			event.setCancelled(true);
			if (!event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName()) {
				return;
			}
			String despawn = Utils.colorize((String)Utils.gfc("messages", "menu.despawn"));
			String spawn = Utils.colorize((String)Utils.gfc("messages", "menu.spawn"));
			User user = User.getUser(event.getWhoClicked().getUniqueId());
			if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(despawn) && user.getEffectKill() != null) {
				user.getEffectKill().despawn(user);
				event.getWhoClicked().closeInventory();
				user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.NOTE_SNARE_DRUM,1f,1f);
				user.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "remove")).replace("%prefix%", Main.prefix)));
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(spawn)) {
				String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
				String name = getEffectByName(displayName);

				MainEffectKill ek = Main.getInstance().getEffectKill().get(name);
				if(ek!=null) {
					if (!user.getPlayer().hasPermission(ek.getPermission())) {
						user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.NOTE_BASS,1f,1f);
						user.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "no-effect")).replace("%prefix%", Main.prefix)));
						return;
					}
					if (user.getEffectKill() != null) {
						user.getEffectKill().despawn(user);
					}
					user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.NOTE_PLING,1f,1f);
					user.setEffectKill(ek);
					event.getWhoClicked().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "spawn")).replaceAll("%effectname%", ek.getDisplayName()).replaceAll("%prefix%", Main.prefix)));
					event.getWhoClicked().closeInventory();
				}else {
					event.getWhoClicked().sendMessage("§c請稍後再試一次...若仍是顯示此訊息 請告知服主");
				}
			}
		}
	}

	public String getEffectByName(String name) {
		for (MainEffectKill effectKills : MainEffectKill.instanceList) {
			String displayname = name.replaceAll(Utils.colorize((String)Utils.gfc("messages", "menu.spawn")) + " ", "");
			if (effectKills.getDisplayName().equalsIgnoreCase(displayname)){
				return effectKills.getName();
			}
		}
		return null;
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity() != null) {
			Player player = event.getEntity().getPlayer();
			User userDeath = User.getUser(player.getUniqueId());
			if(Main.getInstance().putEffectKiller) {
				Player killer = event.getEntity().getKiller();
				if (killer != null) {
					User userKill = User.getUser(killer.getUniqueId());
					if (userKill.getEffectKill() != null) {
						userKill.getEffectKill().update(userDeath);
					}
				}
			} else {
				if(userDeath.getEffectKill() != null) {
					userDeath.getEffectKill().update(userDeath);
				}
			}
		}
	}
}
