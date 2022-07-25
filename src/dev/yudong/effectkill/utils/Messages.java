package dev.yudong.effectkill.utils;

import org.bukkit.entity.Player;

public class Messages {
	public static void sendhelp(Player p) {
		p.sendMessage("§c--- §CParticleKillEffect §c---");
		p.sendMessage("§a作者: WaitNetwork");
		p.sendMessage("§b版本: 1.0");
		p.sendMessage("§4用法:");
		p.sendMessage("§c/killeffect gui §r: §7打開gui介面.");
		p.sendMessage("§c/killeffect remove §7: 移除效果");
		p.sendMessage("§c/killeffect help §r: §7傳送help");
		p.sendMessage(" ");
		p.sendMessage("§c--- §CParticleKillEffect §c---");
	}
}
