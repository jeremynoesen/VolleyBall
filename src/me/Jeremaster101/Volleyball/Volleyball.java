package me.Jeremaster101.Volleyball;

import me.Jeremaster101.Volleyball.Ball.BallListener;
import me.Jeremaster101.Volleyball.Config.ConfigManager;
import me.Jeremaster101.Volleyball.Config.ConfigType;
import me.Jeremaster101.Volleyball.Config.Configs;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin. registers commands and listeners
 *
 * @author Jeremy Noesen
 */
public class Volleyball extends JavaPlugin implements Listener {

    public static Volleyball plugin;
    
    private final Permission admin = new Permission("volleyball.admin");
    
    ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
    ConfigManager messageConfig = Configs.getConfig(ConfigType.MESSAGE);

    /**
     * runs when the plugin starts
     */
    public void onEnable() {
        plugin = this;
        
        messageConfig.saveDefaultConfig();
        courtConfig.saveDefaultConfig();
        
        Message.reloadMessages();

        Message msg = new Message();

        plugin.getServer().getConsoleSender().sendMessage(msg.STARTUP);

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new BallListener(), plugin);
        
        pm.addPermission(admin);

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


