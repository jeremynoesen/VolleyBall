package me.Jeremaster101.Volleyball.Court;

import me.Jeremaster101.Volleyball.Volleyball;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Class used to manage the court config file
 */
public class CourtConfig {
    private static File courtConfigFile = null;
    private static YamlConfiguration config = null;
    
    /**
     * Reloads the court file
     */
    public static void reloadConfig() {
        if (courtConfigFile == null) {
            courtConfigFile = new File(Volleyball.plugin.getDataFolder(), "courts.yml");
        }
        
        config = YamlConfiguration.loadConfiguration(courtConfigFile);
        
        Reader defConfigStream = new InputStreamReader(Volleyball.plugin.getResource("courts.yml"),
                StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        config.setDefaults(defConfig);
        config.options().copyDefaults(true);
        saveConfig();
    }
    
    /**
     * Getter for the court config file
     *
     * @return court config
     */
    public static YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }
    
    /**
     * Saves the court config file
     */
    public static void saveConfig() {
        if (config == null || courtConfigFile == null) {
            return;
        }
        try {
            getConfig().save(courtConfigFile);
        } catch (IOException ex) {
            Volleyball.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + courtConfigFile, ex);
        }
    }
    
    /**
     * Saves default court config when the server loads
     */
    public static void saveDefaultConfig() {
        if (courtConfigFile == null) {
            courtConfigFile = new File(Volleyball.plugin.getDataFolder(), "courts.yml");
        }
        if (!courtConfigFile.exists()) {
            Volleyball.plugin.saveResource("courts.yml", false);
            config = YamlConfiguration.loadConfiguration(courtConfigFile);
        }
    }
}
