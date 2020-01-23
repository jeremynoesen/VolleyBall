package me.Jeremaster101.VolleyBall.Command;

import me.Jeremaster101.VolleyBall.Config.ConfigManager;
import me.Jeremaster101.VolleyBall.Config.ConfigType;
import me.Jeremaster101.VolleyBall.Config.Configs;
import me.Jeremaster101.VolleyBall.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
                    
                    ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
                    
                    if (courtConfig.getConfig().getKeys(false).contains(args[1])) {
                        
                        if (args[2].equalsIgnoreCase("") || args[2].startsWith("s")) {
                            
                            tabList.add("set");
                            tabList.add("select");
                            
                        } else if (args[2].startsWith("set")) {
    
                            tabList.add("set");
                            
                        } else if (args[2].startsWith("sel")) {
    
                            tabList.add("select");
                            
                        }
                        
                    } else if (!args[1].equalsIgnoreCase("defaults")) {
                        
                        if (args[2].equalsIgnoreCase("")) {
                            
                            if (!args[1].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("help") &&
                                    !args[0].equalsIgnoreCase("reload"))
                                tabList.add("create");
                            
                            if (courtConfig.getConfig().getKeys(false).contains(args[1])) tabList.add("remove");
                            
                        } else if (args[2].startsWith("c")) {
                            
                            if (!args[1].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("help") &&
                                    !args[0].equalsIgnoreCase("reload"))
                                tabList.add("create");
                            
                        } else if (args[2].startsWith("r")) {
                            
                            if (courtConfig.getConfig().getKeys(false).contains(args[1])) tabList.add("remove");
                            
                        }
                        
                    } else if (args[1].equalsIgnoreCase("defaults")) {
                        
                        tabList.add("set");
                        
                    }
                    
                } else if (args.length == 2) {
                    
                    ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
                    
                    if (!args[0].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("reload")) {
                        
                        if (args[1].equalsIgnoreCase("")) {
                            
                            tabList.addAll(courtConfig.getConfig().getKeys(false));
                            tabList.add("defaults");
                            tabList.add("help");
                            
                        }
                        
                        for (String courts : courtConfig.getConfig().getKeys(false)) {
                            
                            if (courts.startsWith(args[1])) {
                                
                                tabList.add(courts);
                                
                            }
                            
                            if (args[1].startsWith("d")) tabList.add("defaults");
                            if (args[1].startsWith("h")) tabList.add("help");
                            
                        }
                        
                    }
                    
                } else if (args.length == 4) {
                    
                    if (!args[2].equalsIgnoreCase("create") && !args[2].equalsIgnoreCase("remove")
                            && !args[1].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("help")
                            && !args[0].equalsIgnoreCase("reload")) {
                        
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
                        
                        if (!args[1].equalsIgnoreCase("defaults") && !args[1].equalsIgnoreCase("help")
                                && !args[0].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("reload")) {
                            
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
                        
                        if (!args[2].equalsIgnoreCase("create") && !args[2].equalsIgnoreCase("remove")) {
                            
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
