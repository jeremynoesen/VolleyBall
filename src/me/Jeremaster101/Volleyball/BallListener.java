package me.Jeremaster101.Volleyball;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class BallListener implements Listener {

    /**
     * test when a player hits the volleyball so the ball can be launched in that direction
     */
    @EventHandler
    public void onBallHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity().getCustomName() != null && e.getEntity()
                .getCustomName().equals(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) {
            e.setCancelled(true);
            Entity s = e.getEntity();
            s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
            s.setVelocity(e.getDamager().getLocation().getDirection().multiply(0.9).add(new Vector(0, 0.6, 0))
                    .add(e.getDamager().getVelocity().setY(e.getDamager().getVelocity().multiply(2).getY()))
                    .multiply(Volleyball.plugin.getConfig().getDouble("volleyball-speed")));//todo make ball speed per court
        }

        if (e.getDamager() instanceof Player && e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equals("BALLSTAND")) {
            for (Entity s : e.getEntity().getNearbyEntities(1, 1, 1)) {
                if (s.getCustomName() != null && s.getCustomName().equals(ChatColor.DARK_GREEN +
                        "" + ChatColor.BOLD + "BALL")) {
                    s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
                    s.setVelocity(e.getDamager().getLocation().getDirection().multiply(0.9).add(new Vector(0, 0.6, 0))
                            .add(e.getDamager().getVelocity().setY(e.getDamager().getVelocity().multiply(2).getY()))
                            .multiply(Volleyball.plugin.getConfig().getDouble("volleyball-speed")));
                    break;
                }
            }
        }
    }

    /**
     * protect the court from breaking
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
            if (p.getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.LAPIS_BLOCK) ||
                    p.getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.LAPIS_BLOCK))
                e.setCancelled(true);
        }
    }

    /**
     * block the slime from targetting players in survival mode
     */

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        Entity s = e.getEntity();
        if (s.getCustomName() != null && s.getCustomName().equals(ChatColor.DARK_GREEN +
                "" + ChatColor.BOLD + "BALL") && e.getTarget() instanceof Player) e.setCancelled(true);
    }

    /**
     * prevent players from taking the head off the volleyball armorstand
     */
    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (e.getRightClicked() != null && e.getRightClicked() instanceof ArmorStand && e.getRightClicked().getCustomName()
                != null && e.getRightClicked().getCustomName().equals("BALLSTAND"))
            e.setCancelled(true);
    }
}
