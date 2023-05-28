package dev.yudong.effectkill.utils;

import org.bukkit.entity.Player;

public class Messages {
	public static void sendhelp(Player p) {
		p.sendMessage("§C§m---§C §CWaitKillEffect §m---");
		p.sendMessage("§e作者: YuDong");
		p.sendMessage("§e版本: 1.1");
		p.sendMessage("§f指令用法:");
		p.sendMessage("§c/killeffect gui §r: §7打開gui介面.");
		p.sendMessage("§c/killeffect remove §7: 移除效果");
		p.sendMessage(" ");
		p.sendMessage("§C§m---§C §CWaitKillEffect §m---");
	}
}
