package jeremynoesen.volleyball;

import jeremynoesen.volleyball.ball.BallListener;
import jeremynoesen.volleyball.ball.Balls;
import jeremynoesen.volleyball.command.CommandExec;
import jeremynoesen.volleyball.command.CommandTabComplete;
import jeremynoesen.volleyball.config.ConfigType;
import jeremynoesen.volleyball.config.Configs;
import jeremynoesen.volleyball.court.CourtManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Main class for the plugin. registers commands and listeners
 *
 * @author Jeremy Noesen
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
        
        pm.addPermission(new Permission("volleyball.help"));
        pm.addPermission(new Permission("volleyball.reload"));
        pm.addPermission(new Permission("volleyball.court.create"));
        pm.addPermission(new Permission("volleyball.court.remove"));
        pm.addPermission(new Permission("volleyball.court.info"));
        pm.addPermission(new Permission("volleyball.court.list"));
        pm.addPermission(new Permission("volleyball.court.select"));
        pm.addPermission(new Permission("volleyball.court.help"));
        pm.addPermission(new Permission("volleyball.court.set.animations"));
        pm.addPermission(new Permission("volleyball.court.set.speed"));
        pm.addPermission(new Permission("volleyball.court.set.texture"));
        pm.addPermission(new Permission("volleyball.court.set.bounds"));
        pm.addPermission(new Permission("volleyball.court.set.enabled"));
        pm.addPermission(new Permission("volleyball.court.set.net"));
        pm.addPermission(new Permission("volleyball.court.set.name"));
        pm.addPermission(new Permission("volleyball.court.set.restrictions"));
        
        getCommand("volleyball").setExecutor(new CommandExec());
        getCommand("volleyball").setTabCompleter(new CommandTabComplete());
        
        new BukkitRunnable() {
            @Override
            public void run() {
                int count = 0;
                
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (Balls.isBall(entity)) {
                            entity.remove();
                            count++;
                        }
                    }
                }
                
                plugin.getServer().getConsoleSender().sendMessage(Message.CLEANING);
                
                plugin.getServer().getConsoleSender().sendMessage(Message.DONE_CLEANING.replace("$COUNT$",
                        Integer.toString(count)));
                
                CourtManager.loadAll();
            }
        }.runTaskLater(plugin, 2);
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


