package dev.yudong.effectkill.cmd;

import com.avaje.ebeaninternal.server.core.Message;
import dev.yudong.effectkill.Main;
import dev.yudong.effectkill.utils.Messages;
import dev.yudong.effectkill.utils.User;
import dev.yudong.effectkill.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg0 instanceof Player) {
            Player player = (Player) arg0;
            if (arg3.length == 0)
                Messages.sendhelp(player);
            if (arg3.length == 1) {
                if (arg3[0].equalsIgnoreCase("help"))
                    Messages.sendhelp(player);
                if (arg3[0].equalsIgnoreCase("remove")) {
                    User user = User.getUser(player.getUniqueId());
                    if (user.getEffectKill() == null)
                        return false;
                    user.getEffectKill().despawn(user);
                    user.setEffectKill(null);
                    player.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "remove")).replace("%player%", user.getPlayer().getName()).replace("%prefix%", Main.prefix)));
                }
                if (arg3[0].equalsIgnoreCase("gui")) {
                    Main.getManager().buildInventory(User.getUser(player.getUniqueId())).open(new Player[]{player});
                }
            }
        } else if (!(arg0 instanceof Player)) {
            arg0.sendMessage("§c--- §CParticleKillEffect §c---");
            arg0.sendMessage("§a作者: WaitNetwork");
            arg0.sendMessage("§b版本: 1.0");
            arg0.sendMessage("§4用法:");
            arg0.sendMessage("§c/killeffect gui §r: §7打開gui介面.");
            arg0.sendMessage("§c/killeffect remove §7: 移除效果");
            arg0.sendMessage("§c/killeffect help §r: §7傳送help");
            arg0.sendMessage(" ");
            arg0.sendMessage("§c--- §CParticleKillEffect §c---");
            return true;
        }
        return false;
    }
}
