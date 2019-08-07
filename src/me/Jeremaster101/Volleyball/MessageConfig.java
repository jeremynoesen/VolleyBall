package me.Jeremaster101.Volleyball;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Class used to manage the message config file
 */
public class MessageConfig {
    private static File messageConfigFile = null;
    private static YamlConfiguration config = null;
    
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
        
        Message.PREFIX = Message.format(config.getString("PREFIX"));
        Message.ERROR_BALL_OUT = Message.PREFIX + Message.format(config.getString("ERROR_BALL_OUT"));
        Message.ERROR_NOT_ON_COURT = Message.PREFIX + Message.format(config.getString("ERROR_NOT_ON_COURT"));
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
