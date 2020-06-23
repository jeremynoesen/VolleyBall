package jndev.volleyball.command;

import jndev.volleyball.Message;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.config.Configs;
import jndev.volleyball.court.Court;
import jndev.volleyball.court.CourtManager;
import jndev.volleyball.court.Courts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;

/**
 * Command class, listens for volleyball command
 */
public class CommandExec implements CommandExecutor {
    
    /**
     * method to make the volleyball commands work
     *
     * @param sender  command sender
     * @param command command
     * @param label   part of command immediately followed by /
     * @param args    command arguments
     * @return true
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            if (label.equalsIgnoreCase("volleyball") && player.hasPermission("volleyball.admin") && args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "help":
                        player.sendMessage(Message.HELP_MAIN);
                        break;
                    case "reload":
                        Configs.getConfig(ConfigType.COURT).reloadConfig();
                        Configs.getConfig(ConfigType.MESSAGE).reloadConfig();
                        CourtManager.loadAll();
                        for (World world : Bukkit.getWorlds()) {
                            for (Entity entity : world.getEntities()) {
                                if (entity.getName() != null && ((entity instanceof Slime &&
                                        entity.getName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) ||
                                        (entity instanceof Zombie && entity.getName().equalsIgnoreCase(ChatColor.BLACK + "BALL")))) {
                                    entity.remove();
                                }
                            }
                        }
                        player.sendMessage(Message.SUCCESS_RELOADED);
                        break;
                    case "court":
                        if (args.length > 1) {
                            switch (args[1].toLowerCase()) {
                                case "help":
                                    player.sendMessage(Message.HELP_COURT);
                                    break;
                                case "list":
                                    player.sendMessage(Message.COURT_LIST.replace("$COURTS$",
                                            Configs.getConfig(ConfigType.COURT).getConfig().getKeys(false)
                                                    .toString().replace("[", "").replace("]", "")));
                                    break;
                                case "create":
                                    if (args.length > 2) {
                                        if (Courts.get(args[2]) != null && !args[2].equalsIgnoreCase("create")
                                        && !args[2].equalsIgnoreCase("help") && !args[2].equalsIgnoreCase("list")
                                        && !args[2].equalsIgnoreCase("remove")) {
                                            new Court(args[2]);
                                            player.sendMessage(Message.SUCCESS_COURT_CREATED.replace("$COURT$", args[2]));
                                        } else {
                                            player.sendMessage(Message.ERROR_COURT_EXISTS);
                                        }
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                    }
                                    break;
                                case "remove":
                                    if (args.length > 2) {
                                        if (Courts.get(args[2]) != null) {
                                            Courts.remove(args[2]);
                                            player.sendMessage(Message.SUCCESS_COURT_REMOVED.replace("$COURT$", args[2]));
                                        } else {
                                            player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                        }
                                    } else {
                                        player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                    }
                                default:
                                    if (args.length > 4) {
                                        Court court = Courts.get(args[1]);
                                        if(court != null && args[2].equalsIgnoreCase("set")) {
                                            switch (args[3].toLowerCase()) {
                                                case "net":
                                                    Courts.get(args[1]).setNet(player);
                                                    player.sendMessage(Message.SUCCESS_SET_NET_BOUNDS.replace("$COURT$", args[2]));
                                                    break;
                                                case "bounds":
                                                    Courts.get(args[1]).setBounds(player);
                                                    player.sendMessage(Message.SUCCESS_SET_COURT_BOUNDS.replace("$COURT$", args[2]));
                                                    break;
                                                case "speed":
                                                    Courts.get(args[1]).setSpeed(Double.parseDouble(args[4]));
                                                    player.sendMessage(Message.SUCCESS_SET_COURT_SPEED.replace("$COURT$", args[2]));
                                                    break;
                                                case "texture":
                                                    Courts.get(args[1]).setTexture(args[4]);
                                                    player.sendMessage(Message.SUCCESS_SET_COURT_TEXTURE.replace("$COURT$", args[2]));
                                                    break;
                                                case "animations":
                                                    Courts.get(args[1]).setAnimations(Boolean.parseBoolean(args[4]));
                                                    player.sendMessage(Message.SUCCESS_SET_COURT_ANIMATIONS.replace("$COURT$", args[2]));
                                                    break;
                                                case "enabled":
                                                    Courts.get(args[1]).setEnabled(Boolean.parseBoolean(args[4]));
                                                    player.sendMessage(Message.SUCCESS_SET_COURT_ENABLED.replace("$COURT$", args[2]));
                                                    break;
                                                case "name":
                                                    Courts.get(args[1]).setName(args[4]);
                                                    player.sendMessage(Message.SUCCESS_SET_COURT_NAME.replace("$COURT$", args[2]));
                                                    break;
                                            }
                                        } else {
                                            player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                        }
                                    }
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }
}
