package xyz.jeremynoesen.volleyball.ball;

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
import xyz.jeremynoesen.volleyball.Message;
import xyz.jeremynoesen.volleyball.court.Court;

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
     * hit the ball
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        if ((action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) &&
                Court.get(player) != null && Court.get(player).getBall() != null) {
            Court.get(player).getBall().hit(player);
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
                        if (court.hasHints())
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
