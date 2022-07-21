package dev.waitpvp.ha.cmds;

import dev.waitpvp.ha.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class killeffect implements CommandExecutor {
    private Main plugin;

    public killeffect(final Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.AQUA + "Killeffect" + ChatColor.GREEN + " \n   Power by OceanTW :D\n   §e一個簡易的擊殺特效插件");
            sender.sendMessage(ChatColor.DARK_GREEN + "    /killeffect help了解更多 ");
            return true;
        }
        if (args[0].equals("cc")) { //killeffect cc 不知道
            sender.sendMessage(ChatColor.RED + "   我還在想XD " +
                    "\n   我還在想XD\n   我還在想XD\n   我還在想XD\n   我還在想XD\n   我還在想XD" + "\n   我還在想XD");
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) { //killeffect help - 顯示指令列表
            sender.sendMessage(ChatColor.AQUA + "Killeffect" + ChatColor.GREEN + " \n   Power by OceanTW :D\n   Commands:\n" +
                    ChatColor.DARK_GREEN + "    /killeffect - §F主要指令" + ChatColor.DARK_GREEN + "\n    /killeffect cc ");
            sender.sendMessage(ChatColor.DARK_GREEN + "    /killeffect reload " + "§5重讀插件-需要權限->killeffect.reload");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) { //killeffect reload -> 重讀插件
            if (!(sender instanceof Player)) {
                this.plugin.reloadConfig();
                Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[擊殺特效]" + ChatColor.GREEN + " Reloaded! 成功重讀!");
                return false;
            } else if (sender.hasPermission("killeffect.reload")) {
                final Player p = (Player) sender;
                this.plugin.reloadConfig();
                p.sendMessage(ChatColor.BLUE + "[擊殺特效]" + ChatColor.GREEN + " Reloaded! 成功重讀!");
                return true;
            }
            sender.sendMessage(ChatColor.BLUE + "[擊殺特效] " + ChatColor.RED + "You Dont Have Permission To Do this!");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "");
        sender.sendMessage(ChatColor.RED + "沒有此指令!!");
        sender.sendMessage(ChatColor.RED + "");
        sender.sendMessage(ChatColor.AQUA + "Killeffect" + ChatColor.GREEN + " \n   Power by OceanTW :D\n   Commands:\n" +
                ChatColor.DARK_GREEN + "    /killeffect - §F主要指令");
        sender.sendMessage(ChatColor.DARK_GREEN + "    /killeffect cc ");
        sender.sendMessage(ChatColor.DARK_GREEN + "    /killeffect reload " + "§5重讀插件-需要權限-> killeffect.reload");
        return true;
    }
}