package me.Jeremaster101.Volleyball;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * All messages used within the plugin
 */
public class Message {
    private static File messageConfigFile = null;
    private static YamlConfiguration config = null;

    static String PREFIX = format(getConfig().getString("PREFIX"));
    static String ERROR_NOT_ON_COURT = PREFIX + format(getConfig().getString("ERROR_NOT_ON_COURT"));
    static String ERROR_BALL_OUT = PREFIX + format(getConfig().getString("ERROR_BALL_OUT"));
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

    /**
     * Reloads the message file and reassigns the messages to the updated values
     */
    public static void reloadConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(Volleyball.plugin.getDataFolder(), "messages.yml");
        }

        config = YamlConfiguration.loadConfiguration(messageConfigFile);

        Reader defConfigStream = new InputStreamReader(Volleyball.plugin.getResource("messages.yml"),
                StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        config.setDefaults(defConfig);
        config.options().copyDefaults(true);
        saveConfig();

        PREFIX = format(config.getString("PREFIX"));
        ERROR_BALL_OUT = PREFIX + format(config.getString("ERROR_BALL_OUT"));
        ERROR_NOT_ON_COURT = PREFIX + format(config.getString("ERROR_NOT_ON_COURT"));
    }

    /**
     * Getter for the message config file
     *
     * @return message config
     */
    public static YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    /**
     * Saves the message config file
     */
    public static void saveConfig() {
        if (config == null || messageConfigFile == null) {
            return;
        }
        try {
            getConfig().save(messageConfigFile);
        } catch (IOException ex) {
            Volleyball.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + messageConfigFile, ex);
        }
    }

    /**
     * Saves default message config when the server loads
     */
    public static void saveDefaultConfig() {
        if (messageConfigFile == null) {
            messageConfigFile = new File(Volleyball.plugin.getDataFolder(), "messages.yml");
        }
        if (!messageConfigFile.exists()) {
            Volleyball.plugin.saveResource("messages.yml", false);
            config = YamlConfiguration.loadConfiguration(messageConfigFile);
        }
    }

}