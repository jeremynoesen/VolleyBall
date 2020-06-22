package jndev.volleyball.command;

import jndev.volleyball.Message;
import jndev.volleyball.court.Court;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandTabComplete implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        
        ArrayList<String> tabList = new ArrayList<>();
        
        if (sender instanceof Player) {
            
            Player player = (Player) sender;
            
            if (player.hasPermission("volleyball.admin")) {
                
                for (int i = 0; i < args.length; i++) {
                    args[i] = args[i].toLowerCase();
                }
                
                if (args.length == 0 || args.length == 1) {
                    
                    if (args[0].equalsIgnoreCase("")) {
                        
                        tabList.add("court");
                        tabList.add("help");
                        tabList.add("reload");
                        
                    } else if (args[0].startsWith("c")) {
                        
                        tabList.add("court");
                        
                    } else if (args[0].startsWith("h")) {
                        
                        tabList.add("help");
                    } else if (args[0].startsWith("r")) {
                        
                        tabList.add("reload");
                    }
                    
                } else if (args.length == 3) {
                    
                    Set<String> courts = Court.getCourts().keySet();
                    
                    if (courts.contains(args[1])) {
                        
                        if (args[2].equalsIgnoreCase("")) {
                            
                            tabList.add("set");
                            tabList.add("select");
                            tabList.add("info");
                            
                        } else if (args[2].startsWith("s")) {
                            tabList.add("set");
                            tabList.add("select");
                        } else if (args[2].startsWith("i")) {
                            tabList.add("info");
                        }
                        
                        if (args[2].startsWith("set")) {
                            
                            tabList.remove("select");
                            
                        } else if (args[2].startsWith("sel")) {
                            
                            tabList.remove("set");
                            
                        }
                        
                    } else {
                        
                        if (args[2].equalsIgnoreCase("")) {
                            
                            if (!args[1].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("help") &&
                                    !args[0].equalsIgnoreCase("reload") && !args[1].equalsIgnoreCase("list"))
                                tabList.add("create");
                            
                            if (courts.contains(args[1])) tabList.add("remove");
                            
                        } else if (args[2].startsWith("c")) {
                            
                            if (!args[1].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("help") &&
                                    !args[0].equalsIgnoreCase("reload") && !args[1].equalsIgnoreCase("list"))
                                tabList.add("create");
                            
                        } else if (args[2].startsWith("r")) {
                            
                            if (courts.contains(args[1])) tabList.add("remove");
                            
                        }
                        
                    }
                    
                } else if (args.length == 2) {
    
                    Set<String> courts = Court.getCourts().keySet();
    
                    if (!args[0].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("reload")) {
                        
                        if (args[1].equalsIgnoreCase("")) {
                            
                            tabList.addAll(courts);
                            tabList.add("help");
                            tabList.add("list");
                            
                        }
                        
                        for (String court : courts) {
                            
                            if (court.startsWith(args[1])) {
                                
                                tabList.add(court);
                                
                            }
                            
                            if (args[1].startsWith("h")) tabList.add("help");
                            if (args[1].startsWith("l")) tabList.add("list");
    
                        }
                        
                    }
                    
                } else if (args.length == 4) {
                    
                    if (!args[2].equalsIgnoreCase("create") && !args[2].equalsIgnoreCase("remove")
                            && !args[1].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("help")
                            && !args[0].equalsIgnoreCase("reload") && !args[2].equalsIgnoreCase("info")
                            && !args[1].equalsIgnoreCase("list")) {
                        
                        if (args[3].equalsIgnoreCase("")) {
                            
                            tabList.add("animations");
                            tabList.add("speed");
                            tabList.add("texture");
                            
                        } else if (args[3].startsWith("a")) {
                            
                            tabList.add("animations");
                            
                        } else if (args[3].startsWith("b")) {
                            
                            tabList.add("speed");
                            
                        } else if (args[3].startsWith("t")) {
                            
                            tabList.add("texture");
                            
                        }
                        
                        if (!args[1].equalsIgnoreCase("help")
                                && !args[0].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("reload")
                                && !args[1].equalsIgnoreCase("list")) {
                            
                            if (args[3].equalsIgnoreCase("")) {
                                
                                tabList.add("bounds");
                                tabList.add("enabled");
                                tabList.add("net");
                                
                            } else if (args[3].startsWith("b")) {
                                
                                tabList.add("bounds");
                                
                            } else if (args[3].startsWith("e")) {
                                
                                tabList.add("enabled");
                                
                            } else if (args[3].startsWith("n")) {
                                
                                tabList.add("net");
                                
                            }
                            
                        }
                        
                    }
                    
                } else if (args.length == 5) {
                    
                    if (args[3].equalsIgnoreCase("animations") || args[3].equalsIgnoreCase("enabled")) {
                        
                        if (!args[2].equalsIgnoreCase("create") && !args[2].equalsIgnoreCase("remove")
                                && !args[2].equalsIgnoreCase("info")) {
                            
                            if (args[4].equalsIgnoreCase("")) {
                                
                                tabList.add("true");
                                tabList.add("false");
                                
                            } else if (args[4].startsWith("t")) {
                                
                                tabList.add("true");
                                
                            } else if (args[4].startsWith("f")) {
                                
                                tabList.add("false");
                                
                            }
                            
                        }
                        
                    }
                    
                }
                
                return tabList;
                
            } else player.sendMessage(Message.ERROR_NO_PERMS);
            
        }
        
        return null;
        
    }
    
}
