package xyz.jeremynoesen.volleyball.court;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.jeremynoesen.volleyball.Message;

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
            Court blockCourt = Court.get(e.getClickedBlock().getLocation());
            Court playerCourt = Court.get(player.getLocation());
            if ((blockCourt != null && blockCourt.isEnabled() && !blockCourt.isEditable()) ||
                    (playerCourt != null && playerCourt.isEnabled() && !playerCourt.isEditable())) {
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
            Court from = Court.get(e.getFrom());
            Court to = Court.get(e.getTo());
            if (to != null && from == null) {
                if (to.isEnabled() && to.hasHints()) {
                    player.sendMessage(Message.ENTER_COURT);
                    if (to.hasSounds())
                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 2);
                }
            } else if (to == null && from != null) {
                if (from.isEnabled() && from.hasHints()) {
                    player.sendMessage(Message.EXIT_COURT);
                    if (from.getPlayersOnCourt().size() <= 1) {
                        from.clearScores();
                        if (from.getBall() != null && from.getBall().isOut()) from.getBall().remove();
                    }
                    if (from.hasSounds())
                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 2);
                }
            }
        }
    }
}
