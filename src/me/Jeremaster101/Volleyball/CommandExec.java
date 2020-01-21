package me.Jeremaster101.Volleyball;

import me.Jeremaster101.Volleyball.Ball.Ball;
import me.Jeremaster101.Volleyball.Court.Court;
import me.Jeremaster101.Volleyball.Court.CourtHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Command class, listens for volleyball command
 */
public class CommandExec implements CommandExecutor {//todo add commands to config courts
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            Player p = (Player) sender;
            
            CourtHandler ch = new CourtHandler();
            
            if (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb")) { //todo launch volleyball by shifting
                if (args.length == 0) {
                    if (ch.getCourt(p) != null) {
        
                        Court court = Court.getCourt(p, ch.getCourt(p));
        
                        double speed = Volleyball.getInstance().getConfig().getDouble("default-speed");
                        if (court.getSpeed() != 0.0) speed = court.getSpeed();
        
                        for (Entity all : p.getNearbyEntities(20 * speed, 30 * speed, 20 * speed)) {
                            if (all.getCustomName() != null && all.getCustomName().equals(ChatColor.DARK_GREEN +
                                    "" + ChatColor.BOLD + "BALL")) {
                                p.sendMessage(Message.ERROR_BALL_OUT);
                                return true;
                            }
                        }
        
                        Ball ball = new Ball(p);
                        ball.serve(court);
        
                    } else p.sendMessage(Message.ERROR_NOT_ON_COURT);
                }
            }
        }
        return true;
    }
}
