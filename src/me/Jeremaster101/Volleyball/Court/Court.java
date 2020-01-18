package me.Jeremaster101.Volleyball.Court;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.Jeremaster101.Volleyball.Message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Court {
    
    private String court;
    private Player player;
    
    public Court(Player player, String court) {
        this.player = player;
        if (court != null) {
            this.court = court;
            if (CourtConfig.getConfig().getConfigurationSection(court) == null) {
                
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
                    
                    CourtConfig.getConfig().set(courtName + ".enabled", false);
                    CourtConfig.getConfig().set(courtName + ".location.min.x", minx);
                    CourtConfig.getConfig().set(courtName + ".location.min.y", miny);
                    CourtConfig.getConfig().set(courtName + ".location.min.z", minz);
                    CourtConfig.getConfig().set(courtName + ".location.max.x", maxx);
                    CourtConfig.getConfig().set(courtName + ".location.max.y", maxy);
                    CourtConfig.getConfig().set(courtName + ".location.max.z", maxz);
                    CourtConfig.saveConfig();
                    
                    player.sendMessage(Message.SUCCESS_COURT_SET.replace("$COURT$", court));
                    
                }
            }
        }
    }
    
    public static Court getCourt(Player player, String court) {
        if (CourtConfig.getConfig().get(court) != null) {
            return new Court(player, court);
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT);
            return null;
        }
    }
    
    boolean exists() {
        if (CourtConfig.getConfig().get(court) != null)
            return true;
        else player.sendMessage(Message.ERROR_UNKNOWN_COURT);
        return false;
    }
    
    public void remove() {
        
        if (exists()) {
            CourtConfig.getConfig().set(court, null);
            CourtConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_COURT_REMOVED.replace("$COURT$", court));
        }
    }
    
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
                
                CourtConfig.getConfig().set(areaname + ".location.min.x", minx);
                CourtConfig.getConfig().set(areaname + ".location.min.y", miny);
                CourtConfig.getConfig().set(areaname + ".location.min.z", minz);
                CourtConfig.getConfig().set(areaname + ".location.max.x", maxx);
                CourtConfig.getConfig().set(areaname + ".location.max.y", maxy);
                CourtConfig.getConfig().set(areaname + ".location.max.z", maxz);
                CourtConfig.saveConfig();
                
                player.sendMessage(Message.SUCCESS_SET_COURT_BOUNDS.replace("$COURT$", court));
                
            } else {
                player.sendMessage(Message.ERROR_NULL_BOUNDS);
            }
        }
    }
    
    public boolean isEnabled() {
        if (exists() && CourtConfig.getConfig().get(court + ".enabled") != null) {
            return CourtConfig.getConfig().getBoolean(court + ".enabled");
        }
        return false;
    }
    
    public void setEnabled(boolean enabled) {
        if (exists()) CourtConfig.getConfig().set(court + ".enabled", enabled);
    }
    
    public double getSpeed() {
        if (exists()) {
        
        }
    }
    
    public void setSpeed(double speed) {
        if (exists()) {
        
        }
    }
    
    public String getTexture() {
        if (exists()) {
        
        }
    }
    
    public void setTexture(String url) {
        if (exists()) {
        
        }
    }
}
