package jeremynoesen.volleyball;

import org.bukkit.ChatColor;

/**
 * All messages used within the plugin
 *
 * @author Jeremy Noesen
 */
public class Message {
    
    private static final Config messageConfig = Config.getMessageConfig();
    
    public static String PREFIX;
    public static String ERROR_BALL_OUT;
    public static String ERROR_NOT_ON_COURT;
    public static String ERROR_NULL_BOUNDS;
    public static String ERROR_UNKNOWN_COURT;
    public static String SUCCESS_COURT_REMOVED;
    public static String SUCCESS_SET_BOUNDS;
    public static String SUCCESS_COURT_CREATED;
    public static String SUCCESS_SET_COURT_ENABLED;
    public static String ERROR_CANT_ENABLE;
    public static String SUCCESS_SET_COURT_SPEED;
    public static String SUCCESS_SET_COURT_TEXTURE;
    public static String SUCCESS_COURT_SELECTED;
    public static String SUCCESS_SET_COURT_ANIMATIONS;
    public static String SUCCESS_SET_COURT_NAME;
    public static String SUCCESS_SET_COURT_RESTRICTIONS;
    public static String ERROR_NO_PERMS;
    public static String ERROR_UNKNOWN_ARGS;
    public static String ERROR_COURT_EXISTS;
    public static String SUCCESS_RELOADED;
    public static String COURT_INFO;
    public static String COURT_LIST;
    public static String STARTUP = PREFIX + ChatColor.WHITE + "VolleyBall version " + VolleyBall.getInstance().getDescription().getVersion() + " has been enabled!";
    
    /**
     * reload all messages from file
     */
    public static void reloadMessages() {
        PREFIX = format(messageConfig.getConfig().getString("PREFIX"));
        ERROR_BALL_OUT = PREFIX + format(messageConfig.getConfig().getString("ERROR_BALL_OUT"));
        ERROR_NOT_ON_COURT = PREFIX + format(messageConfig.getConfig().getString("ERROR_NOT_ON_COURT"));
        ERROR_NULL_BOUNDS = PREFIX + format(messageConfig.getConfig().getString("ERROR_NULL_BOUNDS"));
        ERROR_UNKNOWN_COURT = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_COURT"));
        SUCCESS_COURT_REMOVED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_COURT_REMOVED"));
        SUCCESS_SET_BOUNDS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_BOUNDS"));
        SUCCESS_COURT_CREATED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_COURT_CREATED"));
        SUCCESS_SET_COURT_ENABLED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_ENABLED"));
        ERROR_CANT_ENABLE = PREFIX + format(messageConfig.getConfig().getString("ERROR_CANT_ENABLE"));
        SUCCESS_SET_COURT_SPEED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_SPEED"));
        SUCCESS_SET_COURT_TEXTURE = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_TEXTURE"));
        SUCCESS_COURT_SELECTED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_COURT_SELECTED"));
        SUCCESS_SET_COURT_ANIMATIONS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_ANIMATIONS"));
        SUCCESS_SET_COURT_NAME = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_NAME"));
        SUCCESS_SET_COURT_RESTRICTIONS = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_SET_COURT_RESTRICTIONS"));
        ERROR_NO_PERMS = PREFIX + format(messageConfig.getConfig().getString("ERROR_NO_PERMS"));
        ERROR_UNKNOWN_ARGS = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_ARGS"));
        ERROR_COURT_EXISTS = PREFIX + format(messageConfig.getConfig().getString("ERROR_COURT_EXISTS"));
        SUCCESS_RELOADED = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_RELOADED"));
        COURT_INFO = PREFIX + format(messageConfig.getConfig().getString("COURT_INFO"));
        COURT_LIST = PREFIX + format(messageConfig.getConfig().getString("COURT_LIST"));
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
    
    public static String[] HELP_MAIN = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------[" + ChatColor.YELLOW +
                    "" + ChatColor.BOLD + "Volley" + ChatColor.GOLD +
                    "" + ChatColor.BOLD + "Ball " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-------------",
            ChatColor.GRAY + "/volleyball court help" + ChatColor.WHITE + ": View court commands",
            ChatColor.GRAY + "/volleyball reload" + ChatColor.WHITE + ": Reload plugin and configs",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };
    
    public static String[] HELP_COURT = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------[" + ChatColor.YELLOW +
                    "" + ChatColor.BOLD + "Volley" + ChatColor.GOLD +
                    "" + ChatColor.BOLD + "Ball " + ChatColor.GRAY +
                    "" + ChatColor.BOLD + "Court Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]----------",
            ChatColor.GRAY + "/volleyball court list" + ChatColor.WHITE + ": List all courts",
            ChatColor.GRAY + "/volleyball court create <name>" + ChatColor.WHITE + ": Create a new court",
            ChatColor.GRAY + "/volleyball court remove <name>" + ChatColor.WHITE + ": Remove a court",
            ChatColor.GRAY + "/volleyball court info <name>" + ChatColor.WHITE + ": Get court info",
            ChatColor.GRAY + "/volleyball court select <name>" + ChatColor.WHITE + ": Select court with worldedit",
            ChatColor.GRAY + "/volleyball court <name> set net" + ChatColor.WHITE + ": Set the net region",
            ChatColor.GRAY + "/volleyball court <name> set bounds" + ChatColor.WHITE + ": Change the court bounds",
            ChatColor.GRAY + "/volleyball court <name> set animations <true/false>" + ChatColor.WHITE + ": Enable ball animations",
            ChatColor.GRAY + "/volleyball court <name> set restrictions <true/false>" + ChatColor.WHITE + ": Enable ball restrictions",
            ChatColor.GRAY + "/volleyball court <name> set speed <number>" + ChatColor.WHITE + ": Set ball speed",
            ChatColor.GRAY + "/volleyball court <name> set texture <url>" + ChatColor.WHITE + ": Set ball texture",
            ChatColor.GRAY + "/volleyball court <name> set name <newname>" + ChatColor.WHITE + ": Change court name",
            ChatColor.GRAY + "/volleyball court <name> set enabled <true/false>" + ChatColor.WHITE + ": Enable a court",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };
    
}