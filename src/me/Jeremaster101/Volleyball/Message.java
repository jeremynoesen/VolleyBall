package me.Jeremaster101.Volleyball;

import org.bukkit.ChatColor;

/**
 * All messages used within the plugin
 */
public class Message {
    static String PREFIX = format(MessageConfig.getConfig().getString("PREFIX"));
    
    static String ERROR_BALL_OUT = PREFIX + format(MessageConfig.getConfig().getString("ERROR_BALL_OUT"));
    static String ERROR_NOT_ON_COURT = PREFIX + format(MessageConfig.getConfig().getString("ERROR_NOT_ON_COURT"));
    public String STARTUP = "\n\n" +
            ChatColor.DARK_GRAY + "███╗" + ChatColor.YELLOW + "██╗   ██╗" + ChatColor.GOLD + "██████╗ " + ChatColor.DARK_GRAY + "███╗" + ChatColor.WHITE + "  Volleyball version " + Volleyball.plugin.getDescription().getVersion() + " " + "has " + "been enabled!\n" +
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
            ChatColor.GRAY + "/volleyball | /vb <message>" + ChatColor.WHITE + ": Serve a volleyball",
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