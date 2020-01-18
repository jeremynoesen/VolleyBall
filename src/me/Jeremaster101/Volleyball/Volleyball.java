package me.Jeremaster101.Volleyball;

import me.Jeremaster101.Volleyball.Ball.BallListener;
import me.Jeremaster101.Volleyball.Message.Message;
import me.Jeremaster101.Volleyball.Message.MessageConfig;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin. registers commands and listeners
 *
 * @author Jeremy Noesen
 */
public class Volleyball extends JavaPlugin implements Listener {

    public static Volleyball plugin;

    /**
     * runs when the plugin starts
     */
    public void onEnable() {
        plugin = this;

        MessageConfig.saveDefaultConfig();

        Message msg = new Message();

        plugin.getServer().getConsoleSender().sendMessage(msg.STARTUP);

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new BallListener(), plugin);

        getCommand("volleyball").setExecutor(new CommandExec());


        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    /**
     * runs when the plugin stops
     */
    public void onDisable() {
        plugin = null;
    }
    
    public static Volleyball getInstance() {
        return plugin;
    }
    
}

//todo count volleys

