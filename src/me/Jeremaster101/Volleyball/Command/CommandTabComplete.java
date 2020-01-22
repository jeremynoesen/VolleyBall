package me.Jeremaster101.Volleyball.Command;

import me.Jeremaster101.Volleyball.Config.ConfigManager;
import me.Jeremaster101.Volleyball.Config.ConfigType;
import me.Jeremaster101.Volleyball.Config.Configs;
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
            
            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].toLowerCase();
            }
            
            if (args.length == 0 || args.length == 1) {
                tabList.add("court");
            }
            
            if (args.length == 3) {
                if (args[2].equalsIgnoreCase("")) {
                    tabList.add("create");
                    tabList.add("set");
                } else if (args[2].startsWith("c")) {
                    tabList.add("create");
                } else if (args[2].startsWith("s")) {
                    tabList.add("set");
                }
            }
            
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("")) {
                    ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
                    tabList.addAll(courtConfig.getConfig().getKeys(false));
                    for (String courts : courtConfig.getConfig().getKeys(false)) {
                        if (courts.startsWith(args[1])) {
                            tabList.add(courts);
                        }
                    }
                }
            }
            
            if (args.length == 4) {
                if (args[3].equalsIgnoreCase("")) {
                    tabList.add("animations");
                    tabList.add("bounds");
                    tabList.add("enabled");
                    tabList.add("net");
                    tabList.add("speed");
                    tabList.add("texture");
                } else if (args[3].startsWith("a")) {
                    tabList.add("animations");
                } else if (args[3].startsWith("b")) {
                    tabList.add("bounds");
                } else if (args[3].startsWith("e")) {
                    tabList.add("enabled");
                } else if (args[3].startsWith("n")) {
                    tabList.add("net");
                } else if (args[3].startsWith("s")) {
                    tabList.add("speed");
                } else if (args[3].startsWith("t")) {
                    tabList.add("texture");
                }
            }
            
            if (args.length == 5) {
                
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
            
            
        }
        
        return tabList;
        
    }
}
