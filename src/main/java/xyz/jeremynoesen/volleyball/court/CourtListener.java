package xyz.jeremynoesen.volleyball.court;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.jeremynoesen.volleyball.Message;

import java.util.HashMap;

/**
 * Listener for some court events
 *
 * @author Jeremy Noesen
 */
public class CourtListener implements Listener {

    /**
     * protect the court from breaking
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
            if ((Court.get(e.getClickedBlock().getLocation()) != null && Court.get(e.getClickedBlock().getLocation()).isEnabled()) ||
                    (Court.get(player.getLocation()) != null && Court.get(player.getLocation()).isEnabled())) {
                e.setCancelled(true);
            }
        }
    }

    /**
     * send message to players who walk onto or off of any courts
     */
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() ||
                e.getFrom().getBlockY() != e.getTo().getBlockY() ||
                e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            Player player = e.getPlayer();
            if (Court.get(e.getTo()) != null && Court.get(e.getFrom()) == null) {
                Court court = Court.get(e.getTo());
                if (court.isEnabled() && court.hasHints()) {
                    player.sendMessage(Message.ENTER_COURT);
                    if (court.hasSounds())
                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 2);
                }
            } else if (Court.get(e.getTo()) == null && Court.get(e.getFrom()) != null) {
                Court court = Court.get(e.getFrom());
                if (court.isEnabled() && court.hasHints()) {
                    player.sendMessage(Message.EXIT_COURT);
                    if (court.getPlayersOnCourt().size() == 0) {
                        court.clearScores();
                        if (court.getBall() != null && court.getBall().isOut()) court.getBall().remove();
                    }
                    if (court.hasSounds())
                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 2);
                }
            }
        }
    }
}
