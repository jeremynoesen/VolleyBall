package me.Jeremaster101.Volleyball;

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

        Message.saveDefaultConfig();

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
}

//todo count volleys

