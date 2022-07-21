package dev.waitpvp.ha.Listener;

import dev.waitpvp.ha.config.Config;
import dev.waitpvp.ha.main.Main;
import org.bukkit.event.Listener;

public class ListenerManger {
    private Main plugin;
    public ListenerManger(final Main plugin) {
        this.plugin = plugin;
    }
    public void onload(){
        this.onregister(new Config(this.plugin));
    }
    public void onregister(final Listener listener){
        this.plugin.getServer().getPluginManager().registerEvents(listener,  this.plugin);
    }

}
