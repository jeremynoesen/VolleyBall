package jndev.volleyball.ball;

import jndev.volleyball.court.Court;
import jndev.volleyball.Message;
import jndev.volleyball.court.Courts;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

/**
 * Listeners related to the ball object
 */
public class BallListener implements Listener {
    
    /**
     * prevent the armor stand from breaking when hurt by players or mobs
     *
     * @param e entity damage by entity event
     */
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (Balls.isBall(e.getEntity()))
            e.setCancelled(true);
    }
    
    /**
     * protect the court from breaking
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        if (action == Action.LEFT_CLICK_BLOCK) {
            if ((Courts.isOnCourt(e.getClickedBlock().getLocation()) || Courts.isOnCourt(player.getLocation())) &&
                    Courts.get(e.getClickedBlock().getLocation()).isEnabled()) {
                e.setCancelled(true);
            }
            hitBall(player);
        } else if (action == Action.LEFT_CLICK_AIR) {
            hitBall(player);
        }
    }
    
    /**
     * make a player hit the nearest ball
     *
     * @param player player hitting
     */
    private void hitBall(Player player) {
        if (Courts.isOnCourt(player.getLocation())) {
            Court court = Courts.get(player);
            
            for (Entity s : player.getNearbyEntities(1, 1, 1)) {
                if (Balls.isBall(s) && court.getBall().isOut()) {
                    s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
                    s.setVelocity(player.getLocation().getDirection().setY(Math.abs(player.getLocation().getDirection().getY()))
                            .normalize().add(player.getVelocity().multiply(0.25)).multiply(court.getSpeed())
                            .add(new Vector(0, Math.max(0, player.getEyeHeight() - s.getLocation().getY()), 0)));
                    break;
                }
            }
        }
    }
    
    /**
     * prevent players from taking the head off the volleyball armorstand
     */
    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() != null && Balls.isBall(e.getRightClicked()))
            e.setCancelled(true);
    }
    
    /**
     * Serve the volleyball
     */
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (p.isSneaking()) {
            if (Courts.get(p) != null) {
                
                Court court = Courts.get(p);
                
                if (court.isEnabled()) {
                    
                    if (court.getBall() != null && court.getBall().isOut()) {
                        p.sendMessage(Message.ERROR_BALL_OUT);
                        return;
                    } else {
                        Ball ball = new Ball(p);
                        ball.serve();
                    }
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
        if (Balls.isBall(s)) {
            e.getDrops().clear();
        }
    }
}
