package jndev.volleyball.ball;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

/**
 * class to deal with ball checking
 */
public class Balls {
    
    /**
     * check if an entity is a ball
     *
     * @param entity entity to check
     * @return true if the entity is a ball
     */
    public static boolean isBall(Entity entity) {
        return entity instanceof ArmorStand &&
                entity.getName().equals(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL");
    }
    
}
