package dev.yudong.effectkill.cmd;

import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    Main main;

    public Commands(Main main) {
        this.main = main;
    }
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg0 instanceof Player) {
            Player player = (Player) arg0;
            if (arg3.length == 0 || arg3.length == 1 && arg3[0].equalsIgnoreCase("help")) {
                player.sendMessage("§C§m----------------------------------------------------");
                player.sendMessage("§cKillEffect §7» " + main.getDescription().getVersion());
                player.sendMessage("");
                player.sendMessage("§c/killeffect gui §7» 打開gui介面.");
                player.sendMessage("§c/killeffect remove §7» 移除效果");
                player.sendMessage(player.hasPermission("ek.admin") ? ("§c/killeffect respawn §7» 管理員調試-立即重生") : "");
                player.sendMessage(" ");
                player.sendMessage("§C§m----------------------------------------------------");
            } else if (arg3.length == 1) {
                if (arg3[0].equalsIgnoreCase("remove")) {
                    User user = User.getUser(player.getUniqueId());
                    if (user.getEffectKill() == null) return true;
                    player.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "remove")).replaceAll("%effectname%", user.getEffectKill().getDisplayName()).replaceAll("%prefix%", Main.prefix)));
                    user.getEffectKill().despawn(user);
                    user.setEffectKill(null);
                }
                if (arg3[0].equalsIgnoreCase("gui")) {
                    Main.getManager().buildInventory(User.getUser(player.getUniqueId())).open(player);
                } if (arg3[0].equalsIgnoreCase("debug") && player.hasPermission("ke.admin")) {
                    Main.isDebugMode = !Main.isDebugMode;
                    player.sendMessage("§c[管理員調試] 立即重生" + ((Main.isDebugMode) ? "§e開啟" : "§e關閉"));
                }
                return true;
            }
        } else {
            arg0.sendMessage("§c--- §cWaitKillEffect §c---");
            arg0.sendMessage("§b版本: " + main.getDescription().getVersion());
            arg0.sendMessage("§4用法:");
            arg0.sendMessage("§c/killeffect gui §r: §7打開gui介面.");
            arg0.sendMessage("§c/killeffect remove §7: 移除效果");
            arg0.sendMessage("§c/killeffect help §r: §7傳送help");
            arg0.sendMessage(" ");
            arg0.sendMessage("§c--- §cWaitKillEffect §c---");
            return true;
        }
        return false;
    }
}
