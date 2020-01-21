package me.Jeremaster101.Volleyball.Court;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import me.Jeremaster101.Volleyball.Config.ConfigManager;
import me.Jeremaster101.Volleyball.Config.ConfigType;
import me.Jeremaster101.Volleyball.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Court handling
 */
public class CourtHandler {
    
    ConfigManager courtConfig = new ConfigManager(ConfigType.COURT);
    
    /**
     * check if a location is on a court
     * @param l location to check
     * @param court court to use to check
     * @return true if location is in a court region
     */
    public boolean isOnCourt(Location l, String court) {
        
        try {
            double maxx = courtConfig.getConfig().getDouble(court + ".location.max.x");
            double maxy = courtConfig.getConfig().getDouble(court + ".location.max.y");
            double maxz = courtConfig.getConfig().getDouble(court + ".location.max.z");
            double minx = courtConfig.getConfig().getDouble(court + ".location.min.x");
            double miny = courtConfig.getConfig().getDouble(court + ".location.min.y");
            double minz = courtConfig.getConfig().getDouble(court + ".location.min.z");
            String world = courtConfig.getConfig().getString(court + ".location.world");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().getName().equals(world) && (tox <= maxx) && (tox >= minx) && (toy <= maxy) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * check if a location is above a net
     * @param l location to check
     * @param court court to use to check
     * @return true if location is above a net
     */
    public boolean isAboveNet(Location l, Court court) {
        
        String courtName = court.getName();
        
        try {
            double maxx = courtConfig.getConfig().getDouble(courtName + ".net.location.max.x");
            double maxz = courtConfig.getConfig().getDouble(courtName + ".net.location.max.z");
            double minx = courtConfig.getConfig().getDouble(courtName + ".net.location.min.x");
            double miny = courtConfig.getConfig().getDouble(courtName + ".net.location.min.y");
            double minz = courtConfig.getConfig().getDouble(courtName + ".net.location.min.z");
            String world = courtConfig.getConfig().getString(courtName + ".location.world");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().getName().equals(world) && (tox <= maxx) && (tox >= minx) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * @param player player to check for courts
     * @return name of court player is on
     */
    public String getCourt(Player player) {
        
        Set<String> courts = courtConfig.getConfig().getKeys(false);
        int end = 0;
        for (String court : courts) {
            if (courts.size() > 0) {
                if (isOnCourt(player.getLocation(), court)) {
                    return court;
                }
            }
        }
        return null;
    }
    
    /**
     * select a court with worldedit
     * @param player player to do the selecting
     * @param court court to select
     */
    public void selectCourt(Player player, String court) {
        
        try {
            String world = courtConfig.getConfig().getString(court + ".location.world");
            BlockVector3 min = BlockVector3.at(courtConfig.getConfig().getDouble(court + ".location.min.x"),
                    courtConfig.getConfig().getDouble(court + ".location.min.y"),
                    courtConfig.getConfig().getDouble(court + ".location.min.z"));
            BlockVector3 max = BlockVector3.at(courtConfig.getConfig().getDouble(court + ".location.max.x"),
                    courtConfig.getConfig().getDouble(court + ".location.max.y"),
                    courtConfig.getConfig().getDouble(court + ".location.max.z"));
            
            com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
            ls.getRegionSelector(BukkitAdapter.adapt(Bukkit.getWorld(world))).selectPrimary(max, null);
            ls.getRegionSelector(BukkitAdapter.adapt(Bukkit.getWorld(world))).selectSecondary(min, null);
            
            player.sendMessage(Message.SUCCESS_COURT_SELECTED.replace("$COURT$", court));
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT.replace("$COURT$", court));
        }
    }
}
