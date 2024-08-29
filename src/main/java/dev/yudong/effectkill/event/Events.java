package dev.yudong.effectkill.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		FlatFile.setValue(player.getUniqueId());
	}
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory() == null || event.getCurrentItem() == null || event.getWhoClicked() == null) {
			return;
		}
		if (event.getView().getTitle().equalsIgnoreCase(Utils.colorize((String)Utils.gfc("messages", "menu.effectKill")))) {
			event.setCancelled(true);
			if (!event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName()) {
				return;
			}
			String current = Utils.colorize((String)Utils.gfc("messages", "menu.effect"));
			String despawn = Utils.colorize((String)Utils.gfc("messages", "menu.despawn"));
			User user = User.getUser(event.getWhoClicked().getUniqueId());

			if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(current) && user.getEffectKill() != null) {
				user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.CLICK,1f,1f);
			}

			if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(despawn) && user.getEffectKill() != null) {
				user.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "remove")).replaceAll("%effectname%", user.getEffectKill().getDisplayName()).replaceAll("%prefix%", Main.prefix)));
				user.getEffectKill().despawn(user);
				event.getWhoClicked().closeInventory();
				user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.DIG_GRAVEL,1f,1f);
			} else {
				String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
				MainEffectKill ek = Main.getInstance().getEffectKill().get(getEffectName(displayName));
				if(ek!=null) {
					if (!user.getPlayer().hasPermission(ek.getPermission())) {
						user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.NOTE_BASS,1f,1f);
						user.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "no-effect")).replace("%prefix%", Main.prefix)));
						return;
					}
					if(user.getEffectKill() == ek) {
						user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.DIG_WOOD,1f,1f);
						return;
					}
					if (user.getEffectKill() != null) user.getEffectKill().despawn(user);

					user.getPlayer().playSound(user.getPlayer().getLocation(),Sound.NOTE_PLING,1f,1f);
					user.setEffectKill(ek);
					event.getWhoClicked().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "spawn")).replaceAll("%effectname%", ek.getDisplayName()).replaceAll("%prefix%", Main.prefix)));
					event.getWhoClicked().closeInventory();
				}
			}
		}
	}

	public String getEffectName(String displayName) {
		for (MainEffectKill effectKills : MainEffectKill.instanceList) {
			if(effectKills.getDisplayName() != null) {
				if (effectKills.getDisplayName().equalsIgnoreCase(displayName)) {
					return effectKills.getName();
				}
			}
		}
		return null;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Squid) e.setCancelled(true);
		if(e.getEntity() instanceof ArmorStand) e.setCancelled(true);
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity() != null) {
			Player player = event.getEntity().getPlayer();
			User userDeath = User.getUser(player.getUniqueId());

			//debug mode
			if(Main.isDebugMode) {
				if(player.getKiller() != null) player.getKiller().sendMessage(Main.prefix + "處於 Debug 模式，因此立即重生！");
				player.sendMessage(Main.prefix + "處於 Debug 模式，因此立即重生！");
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> {
					player.spigot().respawn();
				},1L);

			}

			Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
				if (Main.getInstance().putEffectKiller) {
					Player killer = event.getEntity().getKiller();
					if (killer != null) {
						User userKill = User.getUser(killer.getUniqueId());
						if (userKill.getEffectKill() != null) {
							userKill.getEffectKill().update(userDeath);
						}
					}
				} else {
					if (userDeath.getEffectKill() != null) {
						userDeath.getEffectKill().update(userDeath);
					}
				}
			});
		}
	}
}
