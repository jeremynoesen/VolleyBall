package jndev.volleyball.court;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Court handling
 */
public class Courts {
    
    /**
     * check if a location is on any court
     *
     * @param l location to check
     * @return true if on any court
     */
    public static boolean isOnCourt(Location l) {
        
        for (Court court : Court.getCourts().values()) {
            if (court.contains(l)) return true;
        }
        
        return false;
        
    }
    
    /**
     * get the court the player is on
     *
     * @param player player to check for courts
     * @return court player is on
     */
    public static Court getCourt(Player player) {
        
        for (Court court : Court.getCourts().values()) {
            if (court.contains(player)) {
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
    public static Court getCourt(Location loc) {
        
        for (Court court : Court.getCourts().values()) {
            if (court.contains(loc)) {
                return court;
            }
        }
        return null;
    }
}
