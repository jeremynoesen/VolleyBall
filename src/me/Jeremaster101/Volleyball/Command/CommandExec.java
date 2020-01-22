package me.Jeremaster101.Volleyball.Command;

import me.Jeremaster101.Volleyball.Court.Court;
import me.Jeremaster101.Volleyball.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command class, listens for volleyball command
 */
public class CommandExec implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            Player p = (Player) sender;
            
            if (label.equalsIgnoreCase("volleyball")) {
                if(p.hasPermission("volleyball.admin")) {
                    if(args.length == 3) {
                        
                        if(args[0].equalsIgnoreCase("court")) {
                            
                            if(args[2].equalsIgnoreCase("create")) {
                                
                                Court court = new Court(p, args[1]);
                                
                            }
                            
                        }
                        
                    } else if (args.length == 4) {
    
                        if(args[0].equalsIgnoreCase("court")) {
    
                            if (args[2].equalsIgnoreCase("set")) {
                                
                                if(args[3].equalsIgnoreCase("net")) {
    
                                    Court.getCourt(p, args[1]).setNet();
                                    
                                } else if(args[3].equalsIgnoreCase("bounds")) {
    
                                    Court.getCourt(p, args[1]).setBounds();
    
                                }
        
                            }
    
                        }
                    
                    } else if (args.length == 5) {
    
                        if(args[0].equalsIgnoreCase("court")) {
        
                            if (args[2].equalsIgnoreCase("set")) {
            
                                if(args[3].equalsIgnoreCase("speed")) {
    
                                    Court.getCourt(p, args[1]).setSpeed(Double.parseDouble(args[4]));
                
                                } else if(args[3].equalsIgnoreCase("texture")) {
    
                                    Court.getCourt(p, args[1]).setTexture(args[4]);
    
                                } else if(args[3].equalsIgnoreCase("animations")) {
    
                                    Court.getCourt(p, args[1]).setAnimations(Boolean.parseBoolean(args[4]));
    
                                } else if(args[3].equalsIgnoreCase("enabled")) {
    
                                    Court.getCourt(p, args[1]).setEnabled(Boolean.parseBoolean(args[4]));
                                
                                }
            
                            }
        
                        }
                    
                    } else {
                        p.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                    }
                } else {
                    p.sendMessage(Message.ERROR_NO_PERMS);
                }
            }
        }
        return true;
    }
}
