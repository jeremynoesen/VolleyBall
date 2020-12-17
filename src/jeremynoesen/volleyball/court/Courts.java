package jeremynoesen.volleyball.court;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Court handling
 *
 * @author Jeremy Noesen
 */
public class Courts {
    
    /**
     * can't make instance of this class
     */
    private Courts() {
    }
    
    /**
     * all loaded courts
     */
    private static final HashMap<String, Court> courts = new HashMap<>();
    
    /**
     * get a court by name
     *
     * @param name name of court
     * @return court if it exists
     */
    public static Court get(String name) {
        if (courts.containsKey(name))
            return courts.get(name);
        return null;
    }
    
    /**
     * get the hashmap with all loaded courts
     *
     * @return hashmap of all courts
     */
    public static HashMap<String, Court> getAll() {
        return courts;
    }
    
    /**
     * add a court to the court hashmap
     *
     * @param court court
     * @param name  court name
     */
    public static void add(Court court, String name) {
        courts.put(name, court);
    }
    
    /**
     * remove a court from the hashmap
     *
     * @param court court to remove
     */
    public static void remove(Court court) {
        if (courts.containsValue(court))
            courts.remove(court.getName());
    }
    
    /**
     * remove a court from the hashmap
     *
     * @param name name of court to remove
     */
    public static void remove(String name) {
        courts.remove(name);
    }
    
    /**
     * check if a location is on any court
     *
     * @param l location to check
     * @return true if on any court
     */
    public static boolean isOnCourt(Location l) {
        
        for (Court court : courts.values()) {
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
    public static Court get(Player player) {
        
        for (Court court : courts.values()) {
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
    public static Court get(Location loc) {
        
        for (Court court : courts.values()) {
            if (court.contains(loc)) {
                return court;
            }
        }
        return null;
    }
}
