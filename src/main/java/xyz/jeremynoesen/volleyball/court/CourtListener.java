package xyz.jeremynoesen.volleyball.court;

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
     * set of players on courts, used to check when they enter or exit them
     */
    HashMap<Player, Court> onCourt = new HashMap<>();

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
            if (Court.get(player.getLocation()) != null) {
                Court court = Court.get(player.getLocation());
                if (!onCourt.containsKey(player) && court.isEnabled() && court.hasHints()) {
                    player.sendMessage(Message.ENTER_COURT);
                    onCourt.put(player, court);
                }
            } else {
                if (onCourt.containsKey(player)) {
                    player.sendMessage(Message.EXIT_COURT);
                    if (onCourt.get(player).getPlayersOnCourt().size() == 0)
                        onCourt.get(player).clearScores();
                    onCourt.remove(player);
                }
            }
        }
    }
}
