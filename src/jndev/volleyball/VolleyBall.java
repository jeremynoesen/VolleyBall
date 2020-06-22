package jndev.volleyball;

import jndev.volleyball.ball.BallListener;
import jndev.volleyball.command.CommandExec;
import jndev.volleyball.command.CommandTabComplete;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.config.Configs;
import jndev.volleyball.court.CourtManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin. registers commands and listeners
 *
 * @author JNDev
 */
public class VolleyBall extends JavaPlugin implements Listener {
    
    /**
     * instance of the plugin
     */
    private static VolleyBall plugin;
    
    /**
     * load files, courts, messages, and delete leftover ball entities
     */
    public void onEnable() {
        plugin = this;
    
        Configs.getConfig(ConfigType.MESSAGE).saveDefaultConfig();
        Configs.getConfig(ConfigType.COURT).saveDefaultConfig();
        
        Message.reloadMessages();
        
        plugin.getServer().getConsoleSender().sendMessage(Message.STARTUP);

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new BallListener(), plugin);
        
        pm.addPermission(new Permission("volleybal.admin"));

        getCommand("volleyball").setExecutor(new CommandExec());
        getCommand("volleyball").setTabCompleter(new CommandTabComplete());
    
        int count = 0;
    
        plugin.getServer().getConsoleSender().sendMessage(Message.CLEANING);
    
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getName() != null && ((entity instanceof Slime &&
                        entity.getName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) ||
                        (entity instanceof Zombie && entity.getName().equalsIgnoreCase(ChatColor.BLACK + "BALL")))) {
                    entity.remove();
                    count++;
                }
            }
        }
    
        plugin.getServer().getConsoleSender().sendMessage(Message.DONE_CLEANING.replace("$COUNT$",
                Integer.toString(count)));
    
        CourtManager.loadAll();
        
    }

    /**
     * disables the plugin and save courts
     */
    public void onDisable() {
        CourtManager.saveAll();
        plugin = null;
    }
    
    /**
     * get the plugin instance
     *
     * @return plugin instance
     */
    public static VolleyBall getInstance() {
        return plugin;
    }
    
}


