package me.Jeremaster101.Volleyball.Config;

import me.Jeremaster101.Volleyball.Message;
import me.Jeremaster101.Volleyball.Volleyball;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * class used to manage all config files in plugin
 */
public class ConfigManager {
    
    private static File configFile;
    private static YamlConfiguration YMLConfig;
    private static ConfigType configType;
    
    public ConfigManager(ConfigType type) {
        configType = type;
    }
    
    /**
     * reloads a configuration file, will load if the file is not loaded. Also saves defaults when they're missing
     */
    public static void reloadConfig() {
        if (configFile == null) {
            configFile = configType.getFile();
        }
        
        YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        
        Reader defConfigStream = new InputStreamReader(configType.getResource(), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        YMLConfig.setDefaults(defConfig);
        YMLConfig.options().copyDefaults(true);
        saveConfig();
        
        if(configType == ConfigType.MESSAGE) Message.reloadMessages();
    }
    
    /**
     * reloads config if YMLConfig is null
     * @return YMLConfig YamlConfiguration
     */
    public static YamlConfiguration getConfig() {
        if (YMLConfig == null) {
            reloadConfig();
        }
        return YMLConfig;
    }
    
    /**
     * saves a config file
     */
    public static void saveConfig() {
        if (YMLConfig == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            Volleyball.getInstance().getLogger().log(Level.SEVERE, "A config file failed to save!", ex);
        }
    }
    
    /**
     * saves the default config from the plugin jar if the file doesn't exist in the plugin folder
     */
    public static void saveDefaultConfig() {
        if (configFile == null) {
            configFile = configType.getFile();
        }
        if (!configFile.exists()) {
            configType.saveResource();
            YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        }
    }
}
