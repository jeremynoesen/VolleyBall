package jndev.volleyball.command;

import jndev.volleyball.config.ConfigType;
import jndev.volleyball.Message;
import jndev.volleyball.config.Configs;
import jndev.volleyball.court.Court;
import jndev.volleyball.court.CourtHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

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
                            
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            
                            Configs.getConfig(ConfigType.COURT).reloadConfig();
                            Configs.getConfig(ConfigType.MESSAGE).reloadConfig();
                            
                            for (World world : Bukkit.getWorlds()) {
                                for (Entity entity : world.getEntities()) {
                                    if (entity.getName() != null && ((entity instanceof Slime &&
                                            entity.getName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) ||
                                            (entity instanceof Zombie && entity.getName().equalsIgnoreCase(ChatColor.BLACK + "BALL")))) {
                                        entity.remove();
                                    }
                                }
                            }
                            
                            p.sendMessage(Message.SUCCESS_RELOADED);
                            
                        }
                        
                    } else if (args.length == 2) {
                        
                        if (args[0].equalsIgnoreCase("court")) {
                            
                            if (args[1].equalsIgnoreCase("help")) {
                                
                                p.sendMessage(Message.HELP_COURT);
                                
                            }
                            
                            if (args[1].equalsIgnoreCase("list")) {
                                
                                p.sendMessage(Message.COURT_LIST.replace("$COURTS$",
                                        Configs.getConfig(ConfigType.COURT).getConfig().getKeys(false)
                                                .toString().replace("[", "").replace("]", "")));
                                
                            }
                            
                        }
                        
                    } else if (args.length == 3) {
                        
                        if (args[0].equalsIgnoreCase("court")) {
                            
                            if (!args[1].equals("defaults")) {
                                
                                
                                if (args[2].equalsIgnoreCase("create")) {
                                    
                                    if(!args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("defaults")) {
    
                                        Court court = new Court(p, args[1]);
    
                                    } else {
                                        
                                        p.sendMessage(Message.ERROR_DEFAULT);
                                        
                                    }
                                    
                                }
                                
                                if (args[2].equalsIgnoreCase("remove")) {
                                    
                                    Court.getCourt(p, args[1]).remove();
                                    
                                }
                                
                                if (args[2].equalsIgnoreCase("select")) {
                                    
                                    CourtHandler ch = new CourtHandler();
                                    ch.selectCourt(p, args[1]);
                                    
                                }
                                
                                if (args[2].equalsIgnoreCase("info")) {
                                    
                                    p.sendMessage(Court.getCourt(p, args[1]).toString());
                                    
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
