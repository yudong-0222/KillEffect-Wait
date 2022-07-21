package dev.waitpvp.ha.main;

import dev.waitpvp.ha.Listener.ListenerManger;
import dev.waitpvp.ha.Utils.InterfaceEntity;
import dev.waitpvp.ha.cmds.killeffect;
import dev.waitpvp.ha.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public  final  class Main extends JavaPlugin  {
private static Main plugin;
private ListenerManger listenerManger;
private InterfaceEntity interfaceEntity;
private PluginDescriptionFile pluginInfo;
private String pluginName = pluginInfo.getName();
private String pluginAuthor = pluginInfo.getAuthors().toString();
public String rutaConfig;
public String DebugPrefix;
public String CoolGay;
public Main() {
        Main.plugin = this;
        this.DebugPrefix = ChatColor.RED + "[Particle-Kill-Effect]";
        this.CoolGay = "WaitNetwork-Development";
        this.listenerManger = new ListenerManger(this);
        }


public void onEnable() {
        if (pluginName != "ParticleKillEffects") {
                Bukkit.getPluginManager().disablePlugin(this);
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   " +
                        "你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   你尛\n   ");
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+ "================================");
        Bukkit.getConsoleSender().sendMessage(DebugPrefix + ChatColor.GREEN + " 歡迎使用!!");
        Bukkit.getConsoleSender().sendMessage(DebugPrefix + ChatColor.AQUA + "版本: §FWaitNetwork Versions -1.8.8 NTUN ASHS");
        Bukkit.getConsoleSender().sendMessage(DebugPrefix + ChatColor.AQUA + "Creator:" + CoolGay);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+ "================================");
        this.regcmds();
        this.listenerManger.onload();
        this.regEvents();
        this.regConfig();

        }
public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+ "================================");
        Bukkit.getConsoleSender().sendMessage(DebugPrefix +"[擊殺特效]" + ChatColor.RED+ " 已關閉!!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+ "================================");
        }
public InterfaceEntity getEntity(){
        return  this.interfaceEntity;
        }
public static Main getPlugin(){
        return Main.plugin;
        }
public void regcmds() {
        this.getCommand("killeffect").setExecutor((CommandExecutor) new killeffect(this)); //練習新的註冊法
        }
public void regEvents(){
        this.getServer().getPluginManager().registerEvents((Listener) new Config(this), (Plugin) this);

}
public void regConfig(){
        final File config = new File(this.getDataFolder(), "config.yml");
        this.rutaConfig = config.getPath(); //獲取路徑.一些垃圾讀取啦
        if (!config.exists()) { //config 不存在的話
                this.getConfig().options().copyDefaults(true);
                this.saveConfig();
        }
}
        }