package me.Jeremaster101.Volleyball;

import me.Jeremaster101.Volleyball.Ball.Ball;
import me.Jeremaster101.Volleyball.Message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Command class, listens for volleyball command
 */
public class CommandExec implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            Player p = (Player) sender;
            double speed = Volleyball.getInstance().getConfig().getDouble("volleyball-speed");
            
            if (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb"))
                if (p.getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.LAPIS_BLOCK) ||
                        p.getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.LAPIS_BLOCK)) {
                    
                    for (Entity all : p.getNearbyEntities(20 * speed, 30 * speed, 20 * speed)) {
                        if (all.getCustomName() != null && all.getCustomName().equals(ChatColor.DARK_GREEN +
                                "" + ChatColor.BOLD + "BALL")) {
                            p.sendMessage(Message.ERROR_BALL_OUT);
                            return true;
                        }
                    }
                    
                    Ball ball = new Ball(p);
                    ball.serve(true
                    );
                    
                } else p.sendMessage(Message.ERROR_NOT_ON_COURT);
        }
        
        return true;
    }
}
