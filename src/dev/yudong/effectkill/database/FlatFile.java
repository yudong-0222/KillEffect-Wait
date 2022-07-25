package dev.yudong.effectkill.database;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.effect.MainEffectKill;
import dev.yudong.effectkill.utils.User;

public class FlatFile{
	private static File cfgFile = new File("plugins/ParticlesKillEffect/database.yml");
	private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
	public static void checkDatabase() {
		if (!cfgFile.exists()) {
			try {
				cfgFile.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	public static void setValue(UUID uuid) {
		if (User.getUsers().containsKey(uuid) && ((User)User.getUsers().get(uuid)).getEffectKill() != null) {
			String value = User.getUser(uuid).getEffectKill().getName();
			cfg.set(uuid.toString(), value);
		} else {
			cfg.set(uuid.toString(), null);
		} 
		try {
			cfg.save(cfgFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public static void getValue(UUID uuid) {
		if (cfg.contains(uuid.toString())) {
			User user = User.getUser(uuid);
			String ek = cfg.getString(uuid.toString());
			MainEffectKill m = (MainEffectKill)Main.getInstance().getEffectKill().get(ek);
			user.setEffectKill(m);
		} 
	}
}
