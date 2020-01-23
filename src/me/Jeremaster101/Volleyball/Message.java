package me.Jeremaster101.Volleyball;

import me.Jeremaster101.Volleyball.Config.ConfigManager;
import me.Jeremaster101.Volleyball.Config.ConfigType;
import me.Jeremaster101.Volleyball.Config.Configs;
import me.Jeremaster101.Volleyball.Volleyball;
import org.bukkit.ChatColor;

/**
 * All messages used within the plugin
 */
public class Message { //todo help message
    
    private static ConfigManager messageConfig = Configs.getConfig(ConfigType.MESSAGE);
    
    public static String PREFIX;
    public static String ERROR_BALL_OUT;
    public static String ERROR_NOT_ON_COURT;
    public static String ERROR_NULL_BOUNDS;
    public static String ERROR_UNKNOWN_COURT;
    public static String SUCCESS_COURT_REMOVED;
    public static String SUCCESS_SET_COURT_BOUNDS;
    public static String SUCCESS_COURT_CREATED;
    public static String SUCCESS_SET_COURT_ENABLED;
    public static String ERROR_CANT_ENABLE;
    public static String SUCCESS_SET_COURT_SPEED;
    public static String SUCCESS_SET_COURT_TEXTURE;
    public static String SUCCESS_COURT_SELECTED;
    public static String SUCCESS_SET_NET_BOUNDS;
    public static String SUCCESS_SET_COURT_ANIMATIONS;
    public static String ERROR_NO_PERMS;
    public static String ERROR_UNKNOWN_ARGS;
    public static String ERROR_DEFAULT;
    
    public static void reloadMessages() {
        PREFIX = format(messageConfig.getConfig().getString("PREFIX"));
        ERROR_BALL_OUT = PREFIX + format(messageConfig.getConfig().getString("ERROR_BALL_OUT"));
        ERROR_NOT_ON_COURT = PREFIX + format(messageConfig.getConfig().getString("ERROR_NOT_ON_COURT"));
        ERROR_NULL_BOUNDS = PREFIX + format(messageConfig.getConfig().getString("ERROR_NULL_BOUNDS"));
        ERROR_UNKNOWN_COURT = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_COURT"));
        SUCCESS_COURT_REMOVED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_COURT_REMOVED"));
        SUCCESS_SET_COURT_BOUNDS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_BOUNDS"));
        SUCCESS_COURT_CREATED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_COURT_CREATED"));
        SUCCESS_SET_COURT_ENABLED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_ENABLED"));
        ERROR_CANT_ENABLE = PREFIX + format(messageConfig.getConfig().getString("ERROR_CANT_ENABLE"));
        SUCCESS_SET_COURT_SPEED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_SPEED"));
        SUCCESS_SET_COURT_TEXTURE = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_TEXTURE"));
        SUCCESS_COURT_SELECTED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_COURT_SELECTED"));
        SUCCESS_SET_NET_BOUNDS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_NET_BOUNDS"));
        SUCCESS_SET_COURT_ANIMATIONS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_ANIMATIONS"));
        ERROR_NO_PERMS = PREFIX + format(messageConfig.getConfig().getString("ERROR_NO_PERMS"));
        ERROR_UNKNOWN_ARGS = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_ARGS"));
        ERROR_DEFAULT = PREFIX + format(messageConfig.getConfig().getString("ERROR_DEFAULT"));
    }
    
    public String STARTUP = "\n\n" +
            ChatColor.DARK_GRAY + "███╗" + ChatColor.YELLOW + "██╗   ██╗" + ChatColor.GOLD + "██████╗ " + ChatColor.DARK_GRAY + "███╗" + ChatColor.WHITE + "  Volleyball version " + Volleyball.getInstance().getDescription().getVersion() + " " + "has " + "been enabled!\n" +
            ChatColor.DARK_GRAY + "██╔╝" + ChatColor.YELLOW + "██║   ██║" + ChatColor.GOLD + "██╔══██╗" + ChatColor.DARK_GRAY + "╚██║" + ChatColor.WHITE + "  Volleyball is written by Jeremaster101 and\n" +
            ChatColor.DARK_GRAY + "██║ " + ChatColor.YELLOW + "██║   ██║" + ChatColor.GOLD + "██████╔╝" + ChatColor.DARK_GRAY + " ██║" + ChatColor.WHITE + "  may not be modified or redistributed without\n" +
            ChatColor.DARK_GRAY + "██║ " + ChatColor.YELLOW + "╚██╗ ██╔╝" + ChatColor.GOLD + "██╔══██╗" + ChatColor.DARK_GRAY + " ██║" + ChatColor.WHITE + "  his consent. For help and support, join the\n" +
            ChatColor.DARK_GRAY + "███╗" + ChatColor.YELLOW + " ╚████╔╝ " + ChatColor.GOLD + "██████╔╝" + ChatColor.DARK_GRAY + "███║" + ChatColor.WHITE + "  support discord group: https://discord.gg/WhmQYR\n" +
            ChatColor.DARK_GRAY + "╚══╝" + ChatColor.YELLOW + "  ╚═══╝  " + ChatColor.GOLD + "╚═════╝ " + ChatColor.DARK_GRAY + "╚══╝" + ChatColor.WHITE + "  Thank you for choosing Volleyball!\n";
    String CLEANING = PREFIX + ChatColor.GRAY + "Deleting leftover volleyball entities...";
    String DONE_CLEANING =
            PREFIX + ChatColor.GRAY + "Successfully deleted " + ChatColor.WHITE + "$COUNT$" + ChatColor.GRAY +
                    " volleyball entities!";
    String[] HELP = new String[]{
            "",
            format("\n&8&l---------[&e&lVollay&6&lball &7&lHelp&8&l]---------"),
            ChatColor.GRAY + "/volleyball | /vb" + ChatColor.WHITE + ": Serve a volleyball",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------------------------",
            ""
    };
    
    /**
     * Apply color codes and line breaks to a message
     *
     * @param msg message to format with color codes and line breaks
     * @return formatted message
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    
}