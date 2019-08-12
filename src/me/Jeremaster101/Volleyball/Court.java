package me.Jeremaster101.Volleyball;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
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
                //todo for all config stuff remove the first key, in this case, "courts."
                
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
    
    public void remove() {
        
        if (CourtConfig.getConfig().get(court) != null) {
            CourtConfig.getConfig().set(court, null);
            CourtConfig.saveConfig();
            player.sendMessage(Message.SUCCESS_COURT_REMOVED.replace("$COURT$", court));
        } else {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT.replace("$COURT$", court));
        }
    }
    
    public void setBounds() {
        
        if (CourtConfig.getConfig().get(court) != null) {
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
                
                player.sendMessage(Message.SUCCESS_UPDATED_COURT_BOUNDS.replace("$COURT$", court));
                
            } else {
                player.sendMessage(Message.ERROR_NULL_BOUNDS);
            }
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_COURT);
    }
    
    public boolean isEnabled() {
        if (CourtConfig.getConfig().get(court) != null &&
                CourtConfig.getConfig().get(court + ".enabled") != null) {
            return CourtConfig.getConfig().getBoolean(court + ".enabled");
        }
        return false;
    }
    
    public void setEnabled(boolean enabled) {
        if (CourtConfig.getConfig().get(court) != null) {
                CourtConfig.getConfig().set(court + ".enabled", enabled);
        } else
            player.sendMessage(Message.ERROR_UNKNOWN_COURT);
    }
    
}
