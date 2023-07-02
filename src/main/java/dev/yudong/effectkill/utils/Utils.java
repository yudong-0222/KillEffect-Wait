package dev.yudong.effectkill.utils;

import org.bukkit.ChatColor;

import dev.yudong.effectkill.utils.config.YAMLUtils;

public class Utils {

    public Utils() {
        throw new RuntimeException("Cannot create instance of Utils!");
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static Object gfc(String file, String prm) {
        return YAMLUtils.get(file).getConfig().get(colorize(prm));
    }

}
