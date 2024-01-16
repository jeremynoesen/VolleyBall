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
    public static String SUCCESS_EDITABLE;
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
    public static String ERROR_UNKNOWN_COURT;
    public static String ERROR_NO_PERMS;
    public static String ERROR_UNKNOWN_ARGS;
    public static String ERROR_COURT_EXISTS;
    public static String COURT_INFO_ANIMATIONS;
    public static String COURT_INFO_BOUNDS;
    public static String COURT_INFO_EDITABLE;
    public static String COURT_INFO_ENABLED;
    public static String COURT_INFO_HINTS;
    public static String COURT_INFO_HITRADIUS;
    public static String COURT_INFO_NAME;
    public static String COURT_INFO_NET;
    public static String COURT_INFO_PARTICLES;
    public static String COURT_INFO_RESTRICTIONS;
    public static String COURT_INFO_SCORING;
    public static String COURT_INFO_SOUNDS;
    public static String COURT_INFO_SPEED;
    public static String COURT_INFO_TEAMS;
    public static String COURT_INFO_TEXTURE;
    public static String COURT_INFO_WORLD;
    public static String COURT_LIST;
    public static String ENTER_COURT;
    public static String EXIT_COURT;
    public static String SCORE_TITLE;
    public static String TEAM_SCORE_TITLE;
    public static String BALL_OUT;
    public static String HELP_HEADER;
    public static String HELP_HELP;
    public static String HELP_RELOAD;
    public static String HELP_LIST;
    public static String HELP_CREATE;
    public static String HELP_INFO;
    public static String HELP_REMOVE;
    public static String HELP_ANIMATIONS;
    public static String HELP_BOUNDS;
    public static String HELP_EDITABLE;
    public static String HELP_ENABLED;
    public static String HELP_HINTS;
    public static String HELP_HITRADIUS;
    public static String HELP_NAME;
    public static String HELP_NET;
    public static String HELP_PARTICLES;
    public static String HELP_RESTRICTIONS;
    public static String HELP_SCORING;
    public static String HELP_SOUNDS;
    public static String HELP_SPEED;
    public static String HELP_TEAMS;
    public static String HELP_TEXTURE;
    public static String HELP_FOOTER;


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
        SUCCESS_EDITABLE = PREFIX + format(messageConfig.getConfig().getString("SUCCESS_EDITABLE"));
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
        ERROR_UNKNOWN_COURT = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_COURT"));
        ERROR_NO_PERMS = PREFIX + format(messageConfig.getConfig().getString("ERROR_NO_PERMS"));
        ERROR_UNKNOWN_ARGS = PREFIX + format(messageConfig.getConfig().getString("ERROR_UNKNOWN_ARGS"));
        ERROR_COURT_EXISTS = PREFIX + format(messageConfig.getConfig().getString("ERROR_COURT_EXISTS"));
        COURT_INFO_ANIMATIONS = format(messageConfig.getConfig().getString("COURT_INFO_ANIMATIONS"));
        COURT_INFO_BOUNDS = format(messageConfig.getConfig().getString("COURT_INFO_BOUNDS"));
        COURT_INFO_EDITABLE = format(messageConfig.getConfig().getString("COURT_INFO_EDITABLE"));
        COURT_INFO_ENABLED = format(messageConfig.getConfig().getString("COURT_INFO_ENABLED"));
        COURT_INFO_HINTS = format(messageConfig.getConfig().getString("COURT_INFO_HINTS"));
        COURT_INFO_HITRADIUS = format(messageConfig.getConfig().getString("COURT_INFO_HITRADIUS"));
        COURT_INFO_NAME = format(messageConfig.getConfig().getString("COURT_INFO_NAME"));
        COURT_INFO_NET = format(messageConfig.getConfig().getString("COURT_INFO_NET"));
        COURT_INFO_PARTICLES = format(messageConfig.getConfig().getString("COURT_INFO_PARTICLES"));
        COURT_INFO_RESTRICTIONS = format(messageConfig.getConfig().getString("COURT_INFO_RESTRICTIONS"));
        COURT_INFO_SCORING = format(messageConfig.getConfig().getString("COURT_INFO_SCORING"));
        COURT_INFO_SOUNDS = format(messageConfig.getConfig().getString("COURT_INFO_SOUNDS"));
        COURT_INFO_SPEED = format(messageConfig.getConfig().getString("COURT_INFO_SPEED"));
        COURT_INFO_TEAMS = format(messageConfig.getConfig().getString("COURT_INFO_TEAMS"));
        COURT_INFO_TEXTURE = format(messageConfig.getConfig().getString("COURT_INFO_TEXTURE"));
        COURT_INFO_WORLD = format(messageConfig.getConfig().getString("COURT_INFO_WORLD"));
        COURT_LIST = PREFIX + format(messageConfig.getConfig().getString("COURT_LIST"));
        ENTER_COURT = PREFIX + format(messageConfig.getConfig().getString("ENTER_COURT"));
        EXIT_COURT = PREFIX + format(messageConfig.getConfig().getString("EXIT_COURT"));
        SCORE_TITLE = format(messageConfig.getConfig().getString("SCORE_TITLE"));
        TEAM_SCORE_TITLE = format(messageConfig.getConfig().getString("TEAM_SCORE_TITLE"));
        BALL_OUT = PREFIX + format(messageConfig.getConfig().getString("BALL_OUT"));
        HELP_HEADER = format(messageConfig.getConfig().getString("HELP_HEADER"));
        HELP_HELP = format(messageConfig.getConfig().getString("HELP_HELP"));
        HELP_RELOAD = format(messageConfig.getConfig().getString("HELP_RELOAD"));
        HELP_LIST = format(messageConfig.getConfig().getString("HELP_LIST"));
        HELP_CREATE = format(messageConfig.getConfig().getString("HELP_CREATE"));
        HELP_INFO = format(messageConfig.getConfig().getString("HELP_INFO"));
        HELP_REMOVE = format(messageConfig.getConfig().getString("HELP_REMOVE"));
        HELP_ANIMATIONS = format(messageConfig.getConfig().getString("HELP_ANIMATIONS"));
        HELP_BOUNDS = format(messageConfig.getConfig().getString("HELP_BOUNDS"));
        HELP_EDITABLE = format(messageConfig.getConfig().getString("HELP_EDITABLE"));
        HELP_ENABLED = format(messageConfig.getConfig().getString("HELP_ENABLED"));
        HELP_HINTS = format(messageConfig.getConfig().getString("HELP_HINTS"));
        HELP_HITRADIUS = format(messageConfig.getConfig().getString("HELP_HITRADIUS"));
        HELP_NAME = format(messageConfig.getConfig().getString("HELP_NAME"));
        HELP_NET = format(messageConfig.getConfig().getString("HELP_NET"));
        HELP_PARTICLES = format(messageConfig.getConfig().getString("HELP_PARTICLES"));
        HELP_RESTRICTIONS = format(messageConfig.getConfig().getString("HELP_RESTRICTIONS"));
        HELP_SCORING = format(messageConfig.getConfig().getString("HELP_SCORING"));
        HELP_SOUNDS = format(messageConfig.getConfig().getString("HELP_SOUNDS"));
        HELP_SPEED = format(messageConfig.getConfig().getString("HELP_SPEED"));
        HELP_TEAMS = format(messageConfig.getConfig().getString("HELP_TEAMS"));
        HELP_TEXTURE = format(messageConfig.getConfig().getString("HELP_TEXTURE"));
        HELP_FOOTER = format(messageConfig.getConfig().getString("HELP_FOOTER"));
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
        help.add(HELP_HEADER);

        if (player.hasPermission("volleyball.help")) help.add(HELP_HELP);
        if (player.hasPermission("volleyball.reload")) help.add(HELP_RELOAD);
        if (player.hasPermission("volleyball.list")) help.add(HELP_LIST);
        if (player.hasPermission("volleyball.create")) help.add(HELP_CREATE);
        if (player.hasPermission("volleyball.info")) help.add(HELP_INFO);
        if (player.hasPermission("volleyball.remove")) help.add(HELP_REMOVE);
        if (player.hasPermission("volleyball.animations")) help.add(HELP_ANIMATIONS);
        if (player.hasPermission("volleyball.bounds")) help.add(HELP_BOUNDS);
        if (player.hasPermission("volleyball.editable")) help.add(HELP_EDITABLE);
        if (player.hasPermission("volleyball.enabled")) help.add(HELP_ENABLED);
        if (player.hasPermission("volleyball.hints")) help.add(HELP_HINTS);
        if (player.hasPermission("volleyball.hitradius")) help.add(HELP_HITRADIUS);
        if (player.hasPermission("volleyball.name")) help.add(HELP_NAME);
        if (player.hasPermission("volleyball.net")) help.add(HELP_NET);
        if (player.hasPermission("volleyball.particles")) help.add(HELP_PARTICLES);
        if (player.hasPermission("volleyball.restrictions")) help.add(HELP_RESTRICTIONS);
        if (player.hasPermission("volleyball.scoring")) help.add(HELP_SCORING);
        if (player.hasPermission("volleyball.sounds")) help.add(HELP_SOUNDS);
        if (player.hasPermission("volleyball.speed")) help.add(HELP_SPEED);
        if (player.hasPermission("volleyball.teams")) help.add(HELP_TEAMS);
        if (player.hasPermission("volleyball.texture")) help.add(HELP_TEXTURE);

        help.add(HELP_FOOTER);
        help.add("");

        String[] out = new String[help.size()];
        return help.toArray(out);
    }
}