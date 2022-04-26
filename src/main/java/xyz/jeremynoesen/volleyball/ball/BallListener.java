package xyz.jeremynoesen.volleyball.ball;

import xyz.jeremynoesen.volleyball.Message;
import xyz.jeremynoesen.volleyball.court.Court;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
 *
 * @author Jeremy Noesen
 */
public class BallListener implements Listener {
    
    /**
     * prevent the armor stand from breaking when hurt by players or mobs
     *
     * @param e entity damage by entity event
     */
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (Ball.getBalls().contains(e.getEntity()))
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
            if ((Court.isOnCourt(e.getClickedBlock().getLocation()) && Court.get(e.getClickedBlock().getLocation()).isEnabled()) ||
                    (Court.isOnCourt(player.getLocation()) && Court.get(player.getLocation()).isEnabled())) {
                e.setCancelled(true);
            }
            hitBall(player);
        } else if (action == Action.LEFT_CLICK_AIR) {
            hitBall(player);
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            if ((Court.isOnCourt(e.getClickedBlock().getLocation()) && Court.get(e.getClickedBlock().getLocation()).isEnabled()) ||
                    (Court.isOnCourt(player.getLocation()) && Court.get(player.getLocation()).isEnabled())) {
                e.setCancelled(true);
            }
        }
    }
    
    /**
     * make a player hit the nearest ball
     *
     * @param player player hitting
     */
    private void hitBall(Player player) {
        if (Court.isOnCourt(player.getLocation())) {
            Court court = Court.get(player);
            double hitRadius = court.getHitRadius();
            for (Entity s : player.getNearbyEntities(hitRadius, hitRadius, hitRadius)) {
                if (Ball.getBalls().contains(s) && court.getBall().isOut()) {
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
        if (e.getRightClicked() != null && Ball.getBalls().contains(e.getRightClicked()))
            e.setCancelled(true);
    }
    
    /**
     * Serve the volleyball
     */
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (p.isSneaking()) {
            if (Court.get(p) != null) {
                
                Court court = Court.get(p);
                
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
        if (Ball.getBalls().contains(s)) {
            e.getDrops().clear();
        }
    }
}
