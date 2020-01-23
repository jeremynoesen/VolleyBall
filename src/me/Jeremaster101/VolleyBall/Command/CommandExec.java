package me.Jeremaster101.VolleyBall.Command;

import me.Jeremaster101.VolleyBall.Court.Court;
import me.Jeremaster101.VolleyBall.Court.CourtHandler;
import me.Jeremaster101.VolleyBall.Message;
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
                
                if (p.hasPermission("volleyball.admin")) {
                    
                    if (args.length == 1) {
                        
                        if (args[0].equalsIgnoreCase("help")) {
                            
                            p.sendMessage(Message.HELP_MAIN);
                            
                        }
                        
                    } else if (args.length == 2) {
                        
                        if(args[0].equalsIgnoreCase("court")) {
                            
                            if(args[1].equalsIgnoreCase("help")) {
                                
                                p.sendMessage(Message.HELP_COURT);
                                
                            }
                            
                        }
                        
                    } else if (args.length == 3) {
                        
                        if (args[0].equalsIgnoreCase("court")) {
                            
                            if (!args[1].equals("defaults")) {
                                
                                
                                if (args[2].equalsIgnoreCase("create")) {
                                    
                                    Court court = new Court(p, args[1]);
                                    
                                }
                                
                                if (args[2].equalsIgnoreCase("remove")) {
                                    
                                    Court.getCourt(p, args[1]).remove();
                                    
                                }
                                
                                if (args[2].equalsIgnoreCase("select")) {
                                    
                                    CourtHandler ch = new CourtHandler();
                                    ch.selectCourt(p, args[1]);
                                    
                                }
                                
                            } else p.sendMessage(Message.ERROR_DEFAULT);
                            
                        }
                        
                    } else if (args.length == 4) {
                        
                        if (args[0].equalsIgnoreCase("court")) {
                            
                            if (args[2].equalsIgnoreCase("set")) {
                                
                                if (!args[1].equals("defaults")) {
                                    
                                    if (args[3].equalsIgnoreCase("net")) {
                                        
                                        Court.getCourt(p, args[1]).setNet();
                                        
                                    } else if (args[3].equalsIgnoreCase("bounds")) {
                                        
                                        Court.getCourt(p, args[1]).setBounds();
                                        
                                    }
                                } else p.sendMessage(Message.ERROR_DEFAULT);
                                
                            }
                            
                        }
                        
                    } else if (args.length == 5) {
                        
                        if (args[0].equalsIgnoreCase("court")) {
                            
                            if (args[2].equalsIgnoreCase("set")) {
                                
                                if (args[3].equalsIgnoreCase("speed")) {
                                    
                                    Court.getCourt(p, args[1]).setSpeed(Double.parseDouble(args[4]));
                                    
                                } else if (args[3].equalsIgnoreCase("texture")) {
                                    
                                    Court.getCourt(p, args[1]).setTexture(args[4]);
                                    
                                } else if (args[3].equalsIgnoreCase("animations")) {
                                    
                                    Court.getCourt(p, args[1]).setAnimations(Boolean.parseBoolean(args[4]));
                                    
                                } else if (args[3].equalsIgnoreCase("enabled")) {
                                    
                                    if (!args[1].equals("defaults")) {
                                        
                                        Court.getCourt(p, args[1]).setEnabled(Boolean.parseBoolean(args[4]));
                                        
                                    } else p.sendMessage(Message.ERROR_DEFAULT);
                                    
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
