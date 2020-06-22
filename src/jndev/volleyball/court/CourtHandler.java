package jndev.volleyball.court;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import jndev.volleyball.Message;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Court handling
 */
public class CourtHandler {
    
    /**
     * check if a location is on a court
     *
     * @param l     location to check
     * @param court court to use to check
     * @return true if location is in a court region
     */
    public boolean isOnCourt(Location l, Court court) {
        
        try {
            double maxx = court.getBounds()[0][0];
            double maxy = court.getBounds()[0][1];
            double maxz = court.getBounds()[0][2];
            double minx = court.getBounds()[1][0];
            double miny = court.getBounds()[1][1];
            double minz = court.getBounds()[1][2];
            World world = court.getWorld();
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().equals(world) && (tox <= maxx) && (tox >= minx) && (toy <= maxy) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * check if a location is on any court
     *
     * @param l location to check
     * @return true if on any court
     */
    public boolean isOnCourt(Location l) {
        
        for (Court court : Court.getCourts().values()) {
            
            if (isOnCourt(l, court)) return true;
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
        
        try {
            double maxx = court.getBounds()[0][1];
            double maxz = court.getBounds()[0][2];
            double minx = court.getBounds()[1][0];
            double miny = court.getBounds()[1][1];
            double minz = court.getBounds()[1][2];
            World world = court.getWorld();
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().equals(world) && (tox <= maxx) && (tox >= minx) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * get the court the player is on
     *
     * @param player player to check for courts
     * @return court player is on
     */
    public Court getCourt(Player player) {
        
        for (Court court : Court.getCourts().values()) {
            if (isOnCourt(player.getLocation(), court)) {
                return court;
            }
        }
        return null;
    }
    
    /**
     * get the court a location is in
     *
     * @param loc location to check
     * @return court location is in
     */
    public Court getCourt(Location loc) {
        
        for (Court court : Court.getCourts().values()) {
            if (isOnCourt(loc, court)) {
                return court;
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
    public void selectCourt(Player player, Court court) {
        
        try {
            World world = court.getWorld();
            BlockVector3 min = BlockVector3.at(court.getBounds()[0][0],
                    court.getBounds()[0][1], court.getBounds()[0][2]);
            BlockVector3 max = BlockVector3.at(court.getBounds()[1][0],
                    court.getBounds()[1][1], court.getBounds()[1][2]);
            
            com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
            ls.getRegionSelector(BukkitAdapter.adapt(world)).selectPrimary(max, null);
            ls.getRegionSelector(BukkitAdapter.adapt(world)).selectSecondary(min, null);
            
            player.sendMessage(Message.SUCCESS_COURT_SELECTED.replace("$COURT$", court.getName()));
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT.replace("$COURT$", court.getName()));
        }
    }
    
    /**
     * get a list of all players on the specified court
     *
     * @param court court to get players form
     * @return list of players on court
     */
    public List<Player> getPlayersOnCourt(Court court) {
        List<Player> players = new ArrayList<>();
        
        World world = court.getWorld();
        
        for (Player playersInWorld : world.getPlayers()) {
            if (!playersInWorld.isOnline()) continue;
            if (isOnCourt(playersInWorld.getLocation(), court)) {
                players.add(playersInWorld);
            }
        }
        
        return players;
    }
}
