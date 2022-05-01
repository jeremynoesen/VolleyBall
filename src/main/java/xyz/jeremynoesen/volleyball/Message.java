package xyz.jeremynoesen.volleyball;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * All messages used within the plugin
 *
 * @author Jeremy Noesen
 */
public class Message {
    
    private static final Config messageConfig = Config.getMessageConfig();
    
    public static String PREFIX;
    public static String SUCCESS_REMOVED;
    public static String SUCCESS_CREATED;
    public static String SUCCESS_RELOADED;
    public static String SUCCESS_ANIMATIONS;
    public static String SUCCESS_BOUNDS_SELECTED;
    public static String SUCCESS_BOUNDS_SET;
    public static String SUCCESS_ENABLED;
    public static String SUCCESS_HINTS;
    public static String SUCCESS_HITRADIUS;
    public static String SUCCESS_NAME;
    public static String SUCCESS_NET_SELECTED;
    public static String SUCCESS_NET_SET;
    public static String SUCCESS_PARTICLES;
    public static String SUCCESS_RESTRICTIONS;
    public static String SUCCESS_SCORING;
    public static String SUCCESS_SOUNDS;
    public static String SUCCESS_SPEED;
    public static String SUCCESS_TEAMS;
    public static String SUCCESS_TEXTURE;
    public static String ERROR_BALL_OUT;
    public static String ERROR_UNKNOWN_COURT;
    public static String ERROR_NO_PERMS;
    public static String ERROR_UNKNOWN_ARGS;
    public static String ERROR_COURT_EXISTS;
    public static String COURT_INFO;
    public static String COURT_LIST;
    public static String ENTER_COURT;
    public static String EXIT_COURT;
    public static String SCORE_TITLE;
    public static String TEAM_SCORE_TITLE;

    /**
     * reload all messages from file
     */
    public static void reloadMessages() {
        PREFIX = format(messageConfig.getConfig().getString("PREFIX"));
        SUCCESS_REMOVED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_REMOVED"));
        SUCCESS_CREATED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_CREATED"));
        SUCCESS_RELOADED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_RELOADED"));
        SUCCESS_ANIMATIONS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_ANIMATIONS"));
        SUCCESS_BOUNDS_SELECTED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_BOUNDS_SELECTED"));
        SUCCESS_BOUNDS_SET = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_BOUNDS_SET"));
        SUCCESS_ENABLED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_ENABLED"));
        SUCCESS_HINTS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_HINTS"));
        SUCCESS_HITRADIUS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_HITRADIUS"));
        SUCCESS_NAME = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_NAME"));
        SUCCESS_NET_SELECTED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_NET_SELECTED"));
        SUCCESS_NET_SET = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_NET_SET"));
        SUCCESS_PARTICLES = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_PARTICLES"));
        SUCCESS_RESTRICTIONS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_RESTRICTIONS"));
        SUCCESS_SCORING = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SCORING"));
        SUCCESS_SOUNDS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SOUNDS"));
        SUCCESS_SPEED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SPEED"));
        SUCCESS_TEAMS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_TEAMS"));
        SUCCESS_TEXTURE = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_TEXTURE"));
        ERROR_BALL_OUT = PREFIX + format(messageConfig.getConfig().getString("ERROR_BALL_OUT"));
        ERROR_UNKNOWN_COURT = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_COURT"));
        ERROR_NO_PERMS = PREFIX + format(messageConfig.getConfig().getString("ERROR_NO_PERMS"));
        ERROR_UNKNOWN_ARGS = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_ARGS"));
        ERROR_COURT_EXISTS = PREFIX + format(messageConfig.getConfig().getString("ERROR_COURT_EXISTS"));
        COURT_INFO = PREFIX + format(messageConfig.getConfig().getString("COURT_INFO"));
        COURT_LIST = PREFIX + format(messageConfig.getConfig().getString("COURT_LIST"));
        ENTER_COURT = PREFIX + format(messageConfig.getConfig().getString("ENTER_COURT"));
        EXIT_COURT = PREFIX + format(messageConfig.getConfig().getString("EXIT_COURT"));
        SCORE_TITLE = format(messageConfig.getConfig().getString("SCORE_TITLE"));
        TEAM_SCORE_TITLE = format(messageConfig.getConfig().getString("TEAM_SCORE_TITLE"));
    }
    
    /**
     * Apply color codes and line breaks to a message
     *
     * @param msg message to format with color codes and line breaks
     * @return formatted message
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * get the help message to send to a player, only showing what they are allowed to run
     *
     * @param player player viewing help message
     * @return help message
     */
    public static String[] getHelpMessage(Player player) {
        ArrayList<String> help = new ArrayList<>();

        help.add("");
        help.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------[" + ChatColor.YELLOW +
                        "" + ChatColor.BOLD + "Volley" + ChatColor.GOLD +
                        "" + ChatColor.BOLD + "Ball " + ChatColor.GRAY +
                        "" + ChatColor.BOLD + "Help"
                        + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-------------");

        if (player.hasPermission("volleyball.help"))
            help.add(ChatColor.GRAY + "/volleyball help" + ChatColor.WHITE + ": Show plugin help");
        if (player.hasPermission("volleyball.reload"))
            help.add(ChatColor.GRAY + "/volleyball reload" + ChatColor.WHITE + ": Reload plugin and configs");
        if (player.hasPermission("volleyball.list"))
            help.add(ChatColor.GRAY + "/volleyball list" + ChatColor.WHITE + ": List all courts");
        if (player.hasPermission("volleyball.create"))
            help.add(ChatColor.GRAY + "/volleyball create <name>" + ChatColor.WHITE + ": Create a new court");
        if (player.hasPermission("volleyball.info"))
            help.add(ChatColor.GRAY + "/volleyball info <name>" + ChatColor.WHITE + ": Get court info");
        if (player.hasPermission("volleyball.remove"))
            help.add(ChatColor.GRAY + "/volleyball remove <name>" + ChatColor.WHITE + ": Remove a court");
        if (player.hasPermission("volleyball.animations"))
            help.add(ChatColor.GRAY + "/volleyball animations <name> <true/false>" + ChatColor.WHITE + ": Enable ball animations");
        if (player.hasPermission("volleyball.bounds"))
            help.add(ChatColor.GRAY + "/volleyball bounds <name> <pos1/pos2/set>" + ChatColor.WHITE + ": Set the court bounds");
        if (player.hasPermission("volleyball.enabled"))
            help.add(ChatColor.GRAY + "/volleyball enabled <name> <true/false>" + ChatColor.WHITE + ": Enable a court");
        if (player.hasPermission("volleyball.hints"))
            help.add(ChatColor.GRAY + "/volleyball hints <name> <pos1/pos2>" + ChatColor.WHITE + ": Enable court hints");
        if (player.hasPermission("volleyball.hitradius"))
            help.add(ChatColor.GRAY + "/volleyball hitradius <name> <number>" + ChatColor.WHITE + ": Set ball hit radius");
        if (player.hasPermission("volleyball.name"))
            help.add(ChatColor.GRAY + "/volleyball name <name> <newname>" + ChatColor.WHITE + ": Change court name");
        if (player.hasPermission("volleyball.net"))
            help.add(ChatColor.GRAY + "/volleyball net <name> <pos1/pos2/set>" + ChatColor.WHITE + ": Set the net region");
        if (player.hasPermission("volleyball.particles"))
            help.add(ChatColor.GRAY + "/volleyball particles <name> <true/false>" + ChatColor.WHITE + ": Enable ball particles");
        if (player.hasPermission("volleyball.restrictions"))
            help.add(ChatColor.GRAY + "/volleyball restrictions <name> <true/false>" + ChatColor.WHITE + ": Enable ball restrictions");
        if (player.hasPermission("volleyball.scoring"))
            help.add(ChatColor.GRAY + "/volleyball scoring <name> <true/false>" + ChatColor.WHITE + ": Enable scoring");
        if (player.hasPermission("volleyball.sounds"))
            help.add(ChatColor.GRAY + "/volleyball sounds <name> <true/false>" + ChatColor.WHITE + ": Enable ball sounds");
        if (player.hasPermission("volleyball.speed"))
            help.add(ChatColor.GRAY + "/volleyball speed <name> <number>" + ChatColor.WHITE + ": Set ball speed");
        if (player.hasPermission("volleyball.teams"))
            help.add(ChatColor.GRAY + "/volleyball teams <name> <true/false>" + ChatColor.WHITE + ": Enable team scoring");
        if (player.hasPermission("volleyball.texture"))
            help.add(ChatColor.GRAY + "/volleyball texture <name> <url>" + ChatColor.WHITE + ": Set ball texture");

        help.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------------------------------");
        help.add("");

        String[] out = new String[help.size()];
        return help.toArray(out);
    }
}