package xyz.jeremynoesen.volleyball;

import xyz.jeremynoesen.volleyball.ball.Ball;
import xyz.jeremynoesen.volleyball.ball.BallListener;
import xyz.jeremynoesen.volleyball.command.CommandExec;
import xyz.jeremynoesen.volleyball.command.CommandTabComplete;
import xyz.jeremynoesen.volleyball.court.CourtIO;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.jeremynoesen.volleyball.court.CourtListener;

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
    
        Config.getMessageConfig().saveDefaultConfig();
        Config.getCourtConfig().saveDefaultConfig();
        
        Message.reloadMessages();
        
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(new BallListener(), plugin);
        pm.registerEvents(new CourtListener(), plugin);

        pm.addPermission(new Permission("volleyball.help"));
        pm.addPermission(new Permission("volleyball.reload"));
        pm.addPermission(new Permission("volleyball.list"));
        pm.addPermission(new Permission("volleyball.create"));
        pm.addPermission(new Permission("volleyball.info"));
        pm.addPermission(new Permission("volleyball.remove"));
        pm.addPermission(new Permission("volleyball.animations"));
        pm.addPermission(new Permission("volleyball.bounds"));
        pm.addPermission(new Permission("volleyball.enabled"));
        pm.addPermission(new Permission("volleyball.hints"));
        pm.addPermission(new Permission("volleyball.hitradius"));
        pm.addPermission(new Permission("volleyball.name"));
        pm.addPermission(new Permission("volleyball.net"));
        pm.addPermission(new Permission("volleyball.particles"));
        pm.addPermission(new Permission("volleyball.restrictions"));
        pm.addPermission(new Permission("volleyball.scoring"));
        pm.addPermission(new Permission("volleyball.sounds"));
        pm.addPermission(new Permission("volleyball.speed"));
        pm.addPermission(new Permission("volleyball.teams"));
        pm.addPermission(new Permission("volleyball.texture"));

        getCommand("volleyball").setExecutor(new CommandExec());
        getCommand("volleyball").setTabCompleter(new CommandTabComplete());
    
        new BukkitRunnable() {
            @Override
            public void run() {
                CourtIO.loadAll();
            }
        }.runTaskLater(plugin, 2);
    }
    
    /**
     * disables the plugin and save courts
     */
    public void onDisable() {
        CourtIO.saveAll();
        for(Entity ball : Ball.getBalls()) {
            ball.remove();
            Ball.getBalls().remove(ball);
        }
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


