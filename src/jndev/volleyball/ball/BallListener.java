package jndev.volleyball.ball;

import jndev.volleyball.court.Court;
import jndev.volleyball.Message;
import jndev.volleyball.court.CourtHandler;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

/**
 * Listeners related to the ball object
 */
public class BallListener implements Listener {
    
    /**
     * test when a player hits the volleyball so the ball can be launched in that direction
     */
    @EventHandler
    public void onBallHit(EntityDamageByEntityEvent e) {
        
        if (e.getDamager() instanceof Player && e.getEntity().getName() != null && e.getEntity()
                .getName().equals(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) {
            
            e.setCancelled(true);
            
            Player player = (Player) e.getDamager();
            
            CourtHandler ch = new CourtHandler();
            
            if (ch.getCourt(player) == null) return;
            
            Court court = Court.getCourt(player, ch.getCourt(player));
            
            Entity s = e.getEntity();
            s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
            s.setVelocity(e.getDamager().getLocation().getDirection().multiply(0.9).add(new Vector(0, 0.6, 0))
                    .add(e.getDamager().getVelocity().setY(e.getDamager().getVelocity().multiply(2).getY()))
                    .multiply(court.getSpeed()));
        }
        
        if (e.getDamager() instanceof Player && e.getEntity().getName() != null &&
                e.getEntity().getName().equals(ChatColor.BLACK + "BALL")) {
            for (Entity s : e.getEntity().getNearbyEntities(1, 1, 1)) {
                if (s.getName() != null && s.getName().equals(ChatColor.DARK_GREEN +
                        "" + ChatColor.BOLD + "BALL")) {
                    
                    e.setCancelled(true);
                    
                    Player player = (Player) e.getDamager();
                    
                    CourtHandler ch = new CourtHandler();
                    
                    if (ch.getCourt(player) == null) return;
                    
                    Court court = Court.getCourt(player, ch.getCourt(player));
                    
                    s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
                    s.setVelocity(e.getDamager().getLocation().getDirection().multiply(0.9).add(new Vector(0, 0.6, 0))
                            .add(e.getDamager().getVelocity().setY(e.getDamager().getVelocity().multiply(2).getY()))
                            .multiply(court.getSpeed()));
                    break;
                }
            }
        }
        
        if((e.getEntity() instanceof Zombie && e.getEntity().getName() != null &&
                e.getEntity().getName().equals(ChatColor.BLACK + "BALL")) ||
                (e.getEntity() instanceof Slime && e.getEntity().getName() != null &&
                e.getEntity().getName().equals(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL"))) {
            e.setCancelled(true);
        }
    }
    
    /**
     * protect the court from breaking
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        CourtHandler ch = new CourtHandler();
        if (a == Action.LEFT_CLICK_BLOCK) {
            if (ch.isOnCourt(e.getClickedBlock().getLocation()) && Court.getCourt(p, ch.getCourt(e.getClickedBlock().getLocation())).isEnabled())
                e.setCancelled(true);
        }
    }
    
    /**
     * block the slime and zombie from targetting players in survival mode
     */
    
    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        Entity s = e.getEntity();
        if (s.getName() != null && (s.getName().equals(ChatColor.DARK_GREEN +
                "" + ChatColor.BOLD + "BALL") || s.getName().equals(ChatColor.BLACK + "BALL")))
            e.setCancelled(true);
    }
    
    /**
     * prevent players from taking the head off the volleyball armorstand
     */
    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (e.getRightClicked() != null && e.getRightClicked() instanceof Zombie && e.getRightClicked().getName()
                != null && e.getRightClicked().getName().equals(ChatColor.BLACK + "BALL"))
            e.setCancelled(true);
    }
    
    /**
     * Serve the volleyball
     */
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        CourtHandler ch = new CourtHandler();
        if (p.isSneaking()) {
            if (ch.getCourt(p) != null) {
                
                Court court = Court.getCourt(p, ch.getCourt(p));
                
                if (court.isEnabled()) {
                    
                    double speed = court.getSpeed();
                    
                    for (Entity all : p.getNearbyEntities(20 * speed, 30 * speed, 20 * speed)) {
                        if (all.getName() != null && all.getName().equals(ChatColor.DARK_GREEN +
                                "" + ChatColor.BOLD + "BALL")) {
                            p.sendMessage(Message.ERROR_BALL_OUT);
                            return;
                        }
                    }
                    
                    Ball ball = new Ball(p);
                    ball.serve(court);
                    
                }
            }
        }
    }
    
    /**
     * prevent ball from dropping anything
     *
     * @param e entity death event
     */
    @EventHandler
    public void onBallDeath(EntityDeathEvent e) {
        Entity s = e.getEntity();
        if (s.getName() != null && (s.getName().equals(ChatColor.DARK_GREEN +
                "" + ChatColor.BOLD + "BALL") || s.getName().equals(ChatColor.BLACK + "BALL"))) {
            e.getDrops().clear();
        }
    }
}
