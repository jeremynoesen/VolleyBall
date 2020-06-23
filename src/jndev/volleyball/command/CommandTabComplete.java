package jndev.volleyball.command;

import jndev.volleyball.Message;
import jndev.volleyball.court.Courts;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * class to enable tab complete functionality for the volleyball commands
 */
public class CommandTabComplete implements TabCompleter {
    
    /**
     * method to implement the tab list for volleyball command
     *
     * @param sender  command sender
     * @param command command
     * @param label   command label followed after the /
     * @param args    command arguments
     * @return tab list
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        
        ArrayList<String> tabList = new ArrayList<>();
        
        Set<String> courts = Courts.getAll().keySet();
        
        if (sender instanceof Player && label.equalsIgnoreCase("volleyball")) {
            
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
                        
                    } else if (args[0].startsWith("c")) tabList.add("court");
                    else if (args[0].startsWith("h")) tabList.add("help");
                    else if (args[0].startsWith("r")) tabList.add("reload");
                    
                } else if (args.length == 2) {
                    
                    if (args[0].equalsIgnoreCase("court")) {
                        
                        if (args[1].equalsIgnoreCase("")) {
                            
                            tabList.add("help");
                            tabList.add("list");
                            tabList.add("create");
                            tabList.add("remove");
                            tabList.add("select");
                            tabList.add("info");
                            
                        } else if (args[1].startsWith("h")) tabList.add("help");
                        else if (args[1].startsWith("l")) tabList.add("list");
                        else if (args[1].startsWith("c")) tabList.add("create");
                        else if (args[1].startsWith("r")) tabList.add("remove");
                        else if (args[1].startsWith("s")) tabList.add("select");
                        else if (args[1].startsWith("i")) tabList.add("info");
                        
                        for (String court : courts) {
                            
                            if (court.startsWith(args[1])) {
                                
                                tabList.add(court);
                                
                            }
                            
                        }
                        
                    }
                    
                } else if (args.length == 3) {
                    
                    if (courts.contains(args[1])) {
                        
                        tabList.add("set");
                        
                    } else if (args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("remove") ||
                            args[1].equalsIgnoreCase("select") || args[1].equalsIgnoreCase("info")) {
                        
                        for (String court : courts) {
                            
                            if (court.startsWith(args[1])) {
                                
                                tabList.add(court);
                                
                            }
                            
                        }
                    }
                    
                } else if (args.length == 4) {
                    
                    if (args[2].equalsIgnoreCase("set")) {
                        
                        if (args[3].equalsIgnoreCase("")) {
                            
                            tabList.add("animations");
                            tabList.add("speed");
                            tabList.add("texture");
                            tabList.add("bounds");
                            tabList.add("enabled");
                            tabList.add("net");
                            tabList.add("name");
                            
                        } else if (args[3].startsWith("a")) {
                            
                            tabList.add("animations");
                            
                        } else if (args[3].startsWith("b")) {
                            
                            tabList.add("speed");
                            
                        } else if (args[3].startsWith("t")) {
                            
                            tabList.add("texture");
                            
                        } else if (args[3].startsWith("b")) {
                            
                            tabList.add("bounds");
                            
                        } else if (args[3].startsWith("e")) {
                            
                            tabList.add("enabled");
                            
                        } else if (args[3].startsWith("n")) {
                            
                            tabList.add("net");
                            tabList.add("name");
                            
                            if (args[3].startsWith("ne")) tabList.remove("name");
                            if (args[3].startsWith("na")) tabList.remove("net");
                            
                        }
                        
                    }
                    
                } else if (args.length == 5) {
                    
                    if (args[3].equalsIgnoreCase("animations") || args[3].equalsIgnoreCase("enabled")) {
                        
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
                
                return tabList;
                
            } else player.sendMessage(Message.ERROR_NO_PERMS);
            
        }
        
        return null;
        
    }
    
}
