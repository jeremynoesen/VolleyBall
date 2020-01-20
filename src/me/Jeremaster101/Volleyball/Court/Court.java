package me.Jeremaster101.Volleyball.Court;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.Jeremaster101.Volleyball.Config.ConfigManager;
import me.Jeremaster101.Volleyball.Config.ConfigType;
import me.Jeremaster101.Volleyball.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Court {
    
    private String court;
    private Player player;
    private static ConfigManager courtConfig = new ConfigManager(ConfigType.COURT);
    
    /**
     * Creates a new court region
     * @param player player creating court
     * @param court court name
     */
    public Court(Player player, String court) {
        this.player = player;
        if (court != null) {
            this.court = court;
            if (courtConfig.getConfig().getConfigurationSection(court) == null) {
                
                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");//todo make option if there is no worldedit
                com.sk89q.worldedit.regions.Region selection;
                try {
                    selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
                } catch (Exception e) {
                    player.sendMessage(Message.ERROR_NULL_BOUNDS);
                    return;
                }
                
                if (selection != null) {
                    String courtName = court;
                    double minx = selection.getMinimumPoint().getX();
                    double miny = selection.getMinimumPoint().getY();
                    double minz = selection.getMinimumPoint().getZ();
                    double maxx = selection.getMaximumPoint().getX();
                    double maxy = selection.getMaximumPoint().getY();
                    double maxz = selection.getMaximumPoint().getZ();
                    
                    courtConfig.getConfig().set(courtName + ".enabled", false);
                    courtConfig.getConfig().set(courtName + ".location.min.x", minx);
                    courtConfig.getConfig().set(courtName + ".location.min.y", miny);
                    courtConfig.getConfig().set(courtName + ".location.min.z", minz);
                    courtConfig.getConfig().set(courtName + ".location.max.x", maxx);
                    courtConfig.getConfig().set(courtName + ".location.max.y", maxy);
                    courtConfig.getConfig().set(courtName + ".location.max.z", maxz);
                    courtConfig.getConfig().set(courtName + ".location.world", player.getWorld().getName());
                    courtConfig.saveConfig();
                    
                    player.sendMessage(Message.SUCCESS_COURT_SET.replace("$COURT$", court));
                    
                }
            }
        }
    }
    
    /**
     * @param player player to send messages to
     * @param court court name
     * @return return court of specified name
     */
    public static Court getCourt(Player player, String court) {
        if (courtConfig.getConfig().get(court) != null) {
            return new Court(player, court);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT);
            return null;
        }
    }
    
    /**
     * @return true if court exists
     */
    boolean exists() {
        if (courtConfig.getConfig().get(court) != null)
            return true;
        else player.sendMessage(Message.ERROR_UNKNOWN_COURT);
        return false;
    }
    
    /**
     * remove court
     */
    public void remove() {
        
        if (exists()) {
            courtConfig.getConfig().set(court, null);
            courtConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_COURT_REMOVED.replace("$COURT$", court));
        }
    }
    
    /**
     * set bounds with worldedit for a court
     */
    public void setBounds() {
        
        if (exists()) {
            WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            
            com.sk89q.worldedit.regions.Region selection;
            try {
                selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
            } catch (Exception e) {
                player.sendMessage(Message.ERROR_NULL_BOUNDS);
                return;
            }
            
            if (selection != null) {
                String areaname = court;
                double minx = selection.getMinimumPoint().getX();
                double miny = selection.getMinimumPoint().getY();
                double minz = selection.getMinimumPoint().getZ();
                double maxx = selection.getMaximumPoint().getX();
                double maxy = selection.getMaximumPoint().getY();
                double maxz = selection.getMaximumPoint().getZ();
                
                courtConfig.getConfig().set(areaname + ".location.min.x", minx);
                courtConfig.getConfig().set(areaname + ".location.min.y", miny);
                courtConfig.getConfig().set(areaname + ".location.min.z", minz);
                courtConfig.getConfig().set(areaname + ".location.max.x", maxx);
                courtConfig.getConfig().set(areaname + ".location.max.y", maxy);
                courtConfig.getConfig().set(areaname + ".location.max.z", maxz);
                courtConfig.saveConfig();
                
                player.sendMessage(Message.SUCCESS_SET_COURT_BOUNDS.replace("$COURT$", court));
                
            } else {
                player.sendMessage(Message.ERROR_NULL_BOUNDS);
            }
        }
    }
    
    /**
     * @return true if the court is enabled
     */
    public boolean isEnabled() {
        if (exists() && courtConfig.getConfig().get(court + ".enabled") != null) {
            return courtConfig.getConfig().getBoolean(court + ".enabled");
        }
        return false;
    }
    
    /**
     * enable a court if it is all set up
     * @param enabled true of false
     */
    public void setEnabled(boolean enabled) {
        if (exists()) {
            if (getSpeed() != 0.0 && getTexture() != null) {
                courtConfig.getConfig().set(court + ".enabled", enabled);
                player.sendMessage(Message.SUCCESS_COURT_ENABLED);
            } else {
                player.sendMessage(Message.ERROR_CANT_ENABLE);
            }
        }
    }
    
    /**
     * @return ball speed type double
     */
    public double getSpeed() {
        if (exists() && courtConfig.getConfig().get(court + ".speed") != null) {
            return courtConfig.getConfig().getDouble(court + ".speed");
        }
        return 0;
    }
    
    /**
     * set how fast the ball gets launched on hit
     * @param speed double speed
     */
    public void setSpeed(double speed) {
        if (exists()) {
            courtConfig.getConfig().set(court + ".speed", speed);
            player.sendMessage(Message.SUCCESS_SET_SPEED);
        }
    }
    
    /**
     * @return url of head texture
     */
    public String getTexture() {
        if (exists() && courtConfig.getConfig().get(court + ".texture") != null) {
            return courtConfig.getConfig().getString(court + ".texture");
        }
        return null;
    }
    
    /**
     * @param url url of head texture
     */
    public void setTexture(String url) {
        if (exists()) {
            courtConfig.getConfig().set(court + ".texture", url);
            player.sendMessage(Message.SUCCESS_SET_TEXTURE);
        }
    }
}
