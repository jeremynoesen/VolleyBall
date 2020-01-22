package me.Jeremaster101.Volleyball.Court;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import me.Jeremaster101.Volleyball.Config.ConfigManager;
import me.Jeremaster101.Volleyball.Config.ConfigType;
import me.Jeremaster101.Volleyball.Config.Configs;
import me.Jeremaster101.Volleyball.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Court handling
 */
public class CourtHandler {
    
    ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
    
    /**
     * check if a location is on a court
     *
     * @param l     location to check
     * @param court court to use to check
     * @return true if location is in a court region
     */
    public boolean isOnCourt(Location l, String court) {
        
        try {
            double maxx = courtConfig.getConfig().getDouble(court + ".court.max.x");
            double maxy = courtConfig.getConfig().getDouble(court + ".court.max.y");
            double maxz = courtConfig.getConfig().getDouble(court + ".court.max.z");
            double minx = courtConfig.getConfig().getDouble(court + ".court.min.x");
            double miny = courtConfig.getConfig().getDouble(court + ".court.min.y");
            double minz = courtConfig.getConfig().getDouble(court + ".court.min.z");
            String world = courtConfig.getConfig().getString(court + ".court.world");
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().getName().equals(world) && (tox <= maxx) && (tox >= minx) && (toy <= maxy) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isOnCourt(Location l) {
        
        for (String courts : courtConfig.getConfig().getKeys(false)) {
            
            try {
                double maxx = courtConfig.getConfig().getDouble(courts + ".court.max.x");
                double maxy = courtConfig.getConfig().getDouble(courts + ".court.max.y");
                double maxz = courtConfig.getConfig().getDouble(courts + ".court.max.z");
                double minx = courtConfig.getConfig().getDouble(courts + ".court.min.x");
                double miny = courtConfig.getConfig().getDouble(courts + ".court.min.y");
                double minz = courtConfig.getConfig().getDouble(courts + ".court.min.z");
                String world = courtConfig.getConfig().getString(courts + ".court.world");
                double tox = l.getBlock().getLocation().getX();
                double toy = l.getBlock().getLocation().getY();
                double toz = l.getBlock().getLocation().getZ();
                
                if (l.getWorld().getName().equals(world) && (tox <= maxx) && (tox >= minx) && (toy <= maxy) &&
                        (toy >= miny) && (toz <= maxz) && (toz >= minz)) return true;
            } catch (Exception e) {
                continue;
            }
        }
        
        return false;
        
    }
    
    /**
     * check if a location is above a net
     *
     * @param l     location to check
     * @param court court to use to check
     * @return true if location is above a net
     */
    public boolean isAboveNet(Location l, Court court) {
        
        String courtName = court.getName();
        
        try {
            double maxx = courtConfig.getConfig().getDouble(courtName + ".net.max.x");
            double maxz = courtConfig.getConfig().getDouble(courtName + ".net.max.z");
            double minx = courtConfig.getConfig().getDouble(courtName + ".net.min.x");
            double miny = courtConfig.getConfig().getDouble(courtName + ".net.min.y");
            double minz = courtConfig.getConfig().getDouble(courtName + ".net.min.z");
            String world = courtConfig.getConfig().getString(courtName + ".court.world");
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
        for (String court : courts) {
            if (courts.size() > 0) {
                if (isOnCourt(player.getLocation(), court)) {
                    return court;
                }
            }
        }
        return null;
    }
    
    public String getCourt(Location loc) {
        
        Set<String> courts = courtConfig.getConfig().getKeys(false);
        for (String court : courts) {
            if (courts.size() > 0) {
                if (isOnCourt(loc, court)) {
                    return court;
                }
            }
        }
        return null;
    }
    
    /**
     * select a court with worldedit
     *
     * @param player player to do the selecting
     * @param court  court to select
     */
    public void selectCourt(Player player, String court) {
        
        try {
            String world = courtConfig.getConfig().getString(court + ".court.world");
            BlockVector3 min = BlockVector3.at(courtConfig.getConfig().getDouble(court + ".court.min.x"),
                    courtConfig.getConfig().getDouble(court + ".court.min.y"),
                    courtConfig.getConfig().getDouble(court + ".court.min.z"));
            BlockVector3 max = BlockVector3.at(courtConfig.getConfig().getDouble(court + ".court.max.x"),
                    courtConfig.getConfig().getDouble(court + ".court.max.y"),
                    courtConfig.getConfig().getDouble(court + ".court.max.z"));
            
            com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
            ls.getRegionSelector(BukkitAdapter.adapt(Bukkit.getWorld(world))).selectPrimary(max, null);
            ls.getRegionSelector(BukkitAdapter.adapt(Bukkit.getWorld(world))).selectSecondary(min, null);
            
            player.sendMessage(Message.SUCCESS_COURT_SELECTED.replace("$COURT$", court));
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT.replace("$COURT$", court));
        }
    }
    
    public List<Player> getPlayersOnCourt(Court court) {
        String name = court.getName();
        List<Player> players = new ArrayList<>();
        
        World world = Bukkit.getServer().getWorld(courtConfig.getConfig().getString(name + ".court.world"));
        
        for (Player playersInWorld : world.getPlayers()) {
            if (!playersInWorld.isOnline()) continue;
            if (isOnCourt(playersInWorld.getLocation(), name)) {
                players.add(playersInWorld);
            }
        }
        
        return players;
    }
}
