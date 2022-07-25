package dev.yudong.effectkill;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.yudong.effectkill.cmd.Commands;
import dev.yudong.effectkill.database.FlatFile;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.event.Events;
import dev.yudong.effectkill.utils.Manager;
import dev.yudong.effectkill.utils.Utils;

public class Main extends JavaPlugin{

	private static Main instance;
	public static String prefix;
	private static Manager manager;
	private Map<String, MainEffectKill> effectkill = new HashMap<>();

	public boolean putEffectKiller;
	public boolean giveItem;

	public void log(String... logs) {
		for (String log : logs)
			getServer().getConsoleSender().sendMessage(log);
	}

	@Override
	public void onEnable() {
		instance = this;
		manager = new Manager();
		getServer().getPluginManager().registerEvents(new Events(), this);
		getCommand("killeffect").setExecutor(new Commands());
		manager.loadMinions();
		Manager.buildConfigs("config", "messages");
		FlatFile.checkDatabase();
		if(((Boolean)Utils.gfc("config", "putEffectKiller")).booleanValue()) {
			putEffectKiller = true;
		}else {
			putEffectKiller = false;
		}
		if (((Boolean)Utils.gfc("config", "menu-item.give-on-join")).booleanValue()) {
			giveItem = true;
		}else {
			giveItem = false;
		}
		log(
				"§bParticleKillEffect",
				"",
				"§3Loading effect...",
				" ",
				"§7 > Plugin EffectKill starting...",
				"",
				"§9Give item => §3" + giveItem,
				"§9Put effect on killer => §3" + putEffectKiller,
				"",
				"§7 > Author: §oWaitNetwork",
				""
				);
		prefix = Utils.colorize((String) Utils.gfc("messages", "prefix"));
		Bukkit.getOnlinePlayers().forEach(p -> FlatFile.getValue(p.getUniqueId()));
	}
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p -> FlatFile.setValue(p.getUniqueId()));
	}
	public static Main getInstance() {
		return instance;
	}
	public static Manager getManager() {
		return manager;
	}
	public Map<String, MainEffectKill> getEffectKill() {
		return effectkill; 
	}
}
