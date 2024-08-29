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
	private final Map<String, MainEffectKill> effectkill = new HashMap<>();

	public boolean putEffectKiller;
	public boolean giveItem;
	public static boolean isDebugMode;

	public void log(String... logs) {
		for (String log : logs)
			getServer().getConsoleSender().sendMessage(log);
	}

	@Override
	public void onEnable() {
		instance = this;
		manager = new Manager();

		getServer().getPluginManager().registerEvents(new Events(), this);
		getCommand("killeffect").setExecutor(new Commands(this));

		manager.loadMinions();

		Manager.buildConfigs("config", "messages");
		FlatFile.checkDatabase();

		log(
				"§bWaitKillEffect",
				"",
				"§3Loading effect...",
				" ",
				"§7 > Plugin EffectKill starting...",
				""
				);
		prefix = Utils.colorize((String) Utils.gfc("messages", "prefix"));

		Bukkit.getOnlinePlayers().forEach(p -> FlatFile.getValue(p.getUniqueId()));

		isDebugMode = false;
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
