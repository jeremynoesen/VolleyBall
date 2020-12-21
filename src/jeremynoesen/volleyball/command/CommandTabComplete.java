package jeremynoesen.volleyball.command;

import jeremynoesen.volleyball.court.Court;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * class to enable tab complete functionality for the volleyball commands
 *
 * @author Jeremy Noesen
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
        
        Set<String> courts = Court.getCourts().keySet();
        
        if (sender instanceof Player && (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb"))) {
            
            Player player = (Player) sender;
            
            if (args.length == 0 || args.length == 1) {
                
                if (args[0].equalsIgnoreCase("")) {
                    
                    tabList.add("court");
                    if (player.hasPermission("volleyball.help")) tabList.add("help");
                    if (player.hasPermission("volleyball.reload")) tabList.add("reload");
                    
                } else if (args[0].startsWith("c")) tabList.add("court");
                else if (args[0].startsWith("h") && player.hasPermission("volleyball.help")) tabList.add("help");
                else if (args[0].startsWith("r") && player.hasPermission("volleyball.reload")) tabList.add("reload");
                
            } else if (args.length == 2) {
                
                if (args[0].equalsIgnoreCase("court")) {
                    
                    if (args[1].equalsIgnoreCase("")) {
                        
                        if (player.hasPermission("volleyball.court.help")) tabList.add("help");
                        if (player.hasPermission("volleyball.court.list")) tabList.add("list");
                        if (player.hasPermission("volleyball.court.create")) tabList.add("create");
                        if (player.hasPermission("volleyball.court.remove")) tabList.add("remove");
                        if (player.hasPermission("volleyball.court.select")) tabList.add("select");
                        if (player.hasPermission("volleyball.court.info")) tabList.add("info");
                        
                    } else if (args[1].startsWith("h") && player.hasPermission("volleyball.court.help"))
                        tabList.add("help");
                    else if (args[1].startsWith("l") && player.hasPermission("volleyball.court.list"))
                        tabList.add("list");
                    else if (args[1].startsWith("c") && player.hasPermission("volleyball.court.create"))
                        tabList.add("create");
                    else if (args[1].startsWith("r") && player.hasPermission("volleyball.court.remove"))
                        tabList.add("remove");
                    else if (args[1].startsWith("s") && player.hasPermission("volleyball.court.select"))
                        tabList.add("select");
                    else if (args[1].startsWith("i") && player.hasPermission("volleyball.court.info"))
                        tabList.add("info");
                    
                    if (player.hasPermission("volleyball.court.create") ||
                            player.hasPermission("volleyball.court.remove") ||
                            player.hasPermission("volleyball.court.select") ||
                            player.hasPermission("volleyball.court.info"))
                        for (String court : courts) {
                            
                            if (court.startsWith(args[1])) {
                                
                                tabList.add(court);
                                
                            }
                            
                        }
                    
                }
                
            } else if (args.length == 3) {
                
                if (courts.contains(args[1])) {
                    
                    tabList.add("set");
                    
                } else if ((args[1].equalsIgnoreCase("remove") && player.hasPermission("volleyball.court.remove")) ||
                        (args[1].equalsIgnoreCase("select") && player.hasPermission("volleyball.court.select")) ||
                        (args[1].equalsIgnoreCase("info") && player.hasPermission("volleyball.court.info"))) {
                    
                    for (String court : courts) {
                        
                        if (court.startsWith(args[2])) {
                            
                            tabList.add(court);
                            
                        }
                        
                    }
                }
                
            } else if (args.length == 4) {
                
                if (args[2].equalsIgnoreCase("set")) {
                    
                    if (args[3].equalsIgnoreCase("")) {
                        
                        if (player.hasPermission("volleyball.court.set.animations")) tabList.add("animations");
                        if (player.hasPermission("volleyball.court.set.speed")) tabList.add("speed");
                        if (player.hasPermission("volleyball.court.set.texture")) tabList.add("texture");
                        if (player.hasPermission("volleyball.court.set.bounds")) tabList.add("bounds");
                        if (player.hasPermission("volleyball.court.set.enabled")) tabList.add("enabled");
                        if (player.hasPermission("volleyball.court.set.net")) tabList.add("net");
                        if (player.hasPermission("volleyball.court.set.name")) tabList.add("name");
                        if (player.hasPermission("volleyball.court.set.restrictions")) tabList.add("restrictions");
                        
                    } else if (args[3].startsWith("a") && player.hasPermission("volleyball.court.set.animations")) {
                        
                        tabList.add("animations");
                        
                    } else if (args[3].startsWith("s") && player.hasPermission("volleyball.court.set.speed")) {
                        
                        tabList.add("speed");
                        
                    } else if (args[3].startsWith("t") && player.hasPermission("volleyball.court.set.texture")) {
                        
                        tabList.add("texture");
                        
                    } else if (args[3].startsWith("b") && player.hasPermission("volleyball.court.set.bounds")) {
                        
                        tabList.add("bounds");
                        
                    } else if (args[3].startsWith("e") && player.hasPermission("volleyball.court.set.enabled")) {
                        
                        tabList.add("enabled");
                        
                    } else if (args[3].startsWith("n")) {
                        
                        if (player.hasPermission("volleyball.court.set.net")) tabList.add("net");
                        if (player.hasPermission("volleyball.court.set.name")) tabList.add("name");
                        
                        if (args[3].startsWith("ne") && player.hasPermission("volleyball.court.set.name"))
                            tabList.remove("name");
                        if (args[3].startsWith("na") && player.hasPermission("volleyball.court.set.net"))
                            tabList.remove("net");
                        
                    } else if (args[3].startsWith("r") && player.hasPermission("volleyball.court.set.restrictions")) {
                        
                        tabList.add("restrictions");
                        
                    }
                    
                }
                
            } else if (args.length == 5) {
                
                if ((args[3].equalsIgnoreCase("animations") && player.hasPermission("volleyball.court.set.animations")) ||
                        (args[3].equalsIgnoreCase("enabled") && player.hasPermission("volleyball.court.set.enabled")) ||
                        (args[3].equalsIgnoreCase("restrictions") && player.hasPermission("volleyball.court.set.restrictions"))) {
                    
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
            
        }
        
        return null;
        
    }
    
}
