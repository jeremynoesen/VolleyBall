package jndev.volleyball.court;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import jndev.volleyball.config.ConfigManager;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.Message;
import jndev.volleyball.config.Configs;
import jndev.volleyball.VolleyBall;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Court creation class
 */
public class Court {
    
    private static ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
    private String court;
    private Player player;
    
    /**
     * Creates a new court region
     *
     * @param player player creating court
     * @param court  court name
     */
    public Court(Player player, String court) {
        this.player = player;
        if (court != null) {
            this.court = court;
            
            if (courtConfig.getConfig().getConfigurationSection(court) == null) { //fix
                
                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
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
                    courtConfig.getConfig().set(courtName + ".court.min.x", minx);
                    courtConfig.getConfig().set(courtName + ".court.min.y", miny);
                    courtConfig.getConfig().set(courtName + ".court.min.z", minz);
                    courtConfig.getConfig().set(courtName + ".court.max.x", maxx);
                    courtConfig.getConfig().set(courtName + ".court.max.y", maxy);
                    courtConfig.getConfig().set(courtName + ".court.max.z", maxz);
                    courtConfig.getConfig().set(courtName + ".court.world", player.getWorld().getName());
                    courtConfig.saveConfig();
                    
                    player.sendMessage(Message.SUCCESS_COURT_CREATED.replace("$COURT$", court));
                    
                }
            }
        }
    }
    
    /**
     * @param player player to send messages to
     * @param court  court name
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
                String courtName = court;
                double minx = selection.getMinimumPoint().getX();
                double miny = selection.getMinimumPoint().getY();
                double minz = selection.getMinimumPoint().getZ();
                double maxx = selection.getMaximumPoint().getX();
                double maxy = selection.getMaximumPoint().getY();
                double maxz = selection.getMaximumPoint().getZ();
                
                courtConfig.getConfig().set(courtName + ".court.min.x", minx);
                courtConfig.getConfig().set(courtName + ".court.min.y", miny);
                courtConfig.getConfig().set(courtName + ".court.min.z", minz);
                courtConfig.getConfig().set(courtName + ".court.max.x", maxx);
                courtConfig.getConfig().set(courtName + ".court.max.y", maxy);
                courtConfig.getConfig().set(courtName + ".court.max.z", maxz);
                courtConfig.getConfig().set(courtName + ".court.world", player.getWorld().getName());
                courtConfig.saveConfig();
                
                player.sendMessage(Message.SUCCESS_SET_NET_BOUNDS.replace("$COURT$", court));
                
            } else {
                player.sendMessage(Message.ERROR_NULL_BOUNDS);
            }
        }
    }
    
    /**
     * set net area with worldedit for a court
     */
    public void setNet() {
        
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
                String courtName = court;
                double minx = selection.getMinimumPoint().getX();
                double miny = selection.getMinimumPoint().getY();
                double minz = selection.getMinimumPoint().getZ();
                double maxx = selection.getMaximumPoint().getX();
                double maxy = selection.getMaximumPoint().getY();
                double maxz = selection.getMaximumPoint().getZ();
                
                courtConfig.getConfig().set(courtName + ".net.min.x", minx);
                courtConfig.getConfig().set(courtName + ".net.min.y", miny);
                courtConfig.getConfig().set(courtName + ".net.min.z", minz);
                courtConfig.getConfig().set(courtName + ".net.max.x", maxx);
                courtConfig.getConfig().set(courtName + ".net.max.y", maxy);
                courtConfig.getConfig().set(courtName + ".net.max.z", maxz);
                courtConfig.saveConfig();
                
                player.sendMessage(Message.SUCCESS_SET_NET_BOUNDS.replace("$COURT$", court));
                
            } else {
                player.sendMessage(Message.ERROR_NULL_BOUNDS);
            }
        }
    }
    
    /**
     * @return true if net region exists
     */
    public boolean isNetSet() {
        
        String courtName = court;
        
        return courtConfig.getConfig().get(courtName + ".net.min.x") != null
                && courtConfig.getConfig().get(courtName + ".net.min.y") != null
                && courtConfig.getConfig().get(courtName + ".net.min.z") != null
                && courtConfig.getConfig().get(courtName + ".net.max.x") != null
                && courtConfig.getConfig().get(courtName + ".net.max.y") != null
                && courtConfig.getConfig().get(courtName + ".net.max.z") != null;
        
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
     *
     * @param enabled true of false
     */
    public void setEnabled(boolean enabled) {
        if (exists()) {
            if (isNetSet()) {
                courtConfig.getConfig().set(court + ".enabled", enabled);
                courtConfig.saveConfig();
                player.sendMessage(Message.SUCCESS_SET_COURT_ENABLED.replace("$COURT$", court).replace("$BOOL$",
                        Boolean.toString(enabled)));
            } else {
                player.sendMessage(Message.ERROR_CANT_ENABLE.replace("$COURT$", court));
            }
        }
    }
    
    /**
     * @return ball speed type double
     */
    public double getSpeed() {
        if (exists()) {
            if (courtConfig.getConfig().get(court + ".speed") != null) {
                return courtConfig.getConfig().getDouble(court + ".speed");
            } else
                return VolleyBall.getInstance().getConfig().getDouble("default-speed");
        }
        return VolleyBall.getInstance().getConfig().getDouble("default-speed");
    }
    
    /**
     * set how fast the ball gets launched on hit
     *
     * @param speed double speed
     */
    public void setSpeed(double speed) {
        if (exists()) {
            courtConfig.getConfig().set(court + ".speed", speed);
            courtConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_SET_COURT_SPEED.replace("$COURT$", court).replace("$SPEED$",
                    Double.toString(speed)));
        }
    }
    
    /**
     * @return url of head texture
     */
    public String getTexture() {
        if (exists() && courtConfig.getConfig().get(court + ".texture") != null) {
            return courtConfig.getConfig().getString(court + ".texture");
        }
        return VolleyBall.getInstance().getConfig().getString("default-texture");
    }
    
    /**
     * @param url url of head texture
     */
    public void setTexture(String url) {
        if (exists()) {
            courtConfig.getConfig().set(court + ".texture", url);
            courtConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_SET_COURT_TEXTURE.replace("$COURT$", court));
        }
    }
    
    public boolean getAnimations() {
        if (exists()) {
            if (courtConfig.getConfig().get(court + ".animations") != null) {
                return courtConfig.getConfig().getBoolean(court + ".animations");
            } else {
                return VolleyBall.getInstance().getConfig().getBoolean("default-animations");
            }
        }
        return false;
    }
    
    public void setAnimations(boolean animations) {
        if (exists()) {
            courtConfig.getConfig().set(court + ".animations", animations);
            courtConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_SET_COURT_ANIMATIONS.replace("$COURT$", court).replace("$BOOL$",
                    Boolean.toString(animations)));
        }
    }
    
    public String getName() {
        if (exists()) return court;
        return null;
    }
    
    @Override
    public String toString() {
        return Message.COURT_INFO
                .replace("$NAME$", court)
                .replace("$ENABLED$", Boolean.toString(isEnabled()))
                .replace("$ANIMATIONS$", Boolean.toString(getAnimations()))
                .replace("$SPEED$", Double.toString(getSpeed()))
                .replace("$TEXTURE$", getTexture())
                .replace("$BOUNDS$",
                        "(" +
                                courtConfig.getConfig().getDouble(court + ".court.min.x") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".court.min.y") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".court.min.z") +
                                ") to (" +
                                courtConfig.getConfig().getDouble(court + ".court.max.x") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".court.max.y") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".court.max.z") +
                                ")")
                .replace("$NET$",
                        "(" +
                                courtConfig.getConfig().getDouble(court + ".net.min.x") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".net.min.y") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".net.min.z") +
                                ") to (" +
                                courtConfig.getConfig().getDouble(court + ".net.max.x") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".net.max.y") +
                                "," +
                                courtConfig.getConfig().getDouble(court + ".net.max.z") +
                                ")")
                .replace("$WORLD$", courtConfig.getConfig().getString(court + ".court.world"));
        
    }
}
//todo court list and info commands