package me.Jeremaster101.Volleyball;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Command class, listens for volleyball command
 */
public class CommandExec implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb"))
                if (p.getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.LAPIS_BLOCK) ||
                        p.getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.LAPIS_BLOCK)) {
                    for (Entity all : p.getNearbyEntities(20, 30, 20)) {
                        if (all.getCustomName() != null && all.getCustomName().equals(ChatColor.DARK_GREEN +
                                "" + ChatColor.BOLD + "BALL")) {
                            p.sendMessage(Message.ERROR_BALL_OUT);
                            return true;
                        }
                    }

                    BallCreation ballCreation = new BallCreation();
                    ballCreation.launchVolleyball(p);
                    Location loc = p.getLocation();
                    double radius = 0.5;
                    for (double y = 0; y <= 10; y += 0.2) {
                        double finalY = y;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                double x = (radius + 0.05 * finalY) * Math.cos(finalY);
                                double z = (radius + 0.05 * finalY) * Math.sin(finalY);
                                p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,
                                        (float) (loc.getX() + x), (float) (loc.getY() + finalY * 0.1),
                                        (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                            }
                        }.runTaskLater(Volleyball.plugin, (long) y);
                    }
                } else p.sendMessage(Message.ERROR_NOT_ON_COURT);
        }

        return true;
    }
}
