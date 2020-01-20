package me.Jeremaster101.Volleyball.Config;

import me.Jeremaster101.Volleyball.Volleyball;

import java.io.File;
import java.io.InputStream;

/**
 * enum to make getting separate configs easier
 */
public enum ConfigType {
    COURT("courts.yml"), MESSAGE("messages.yml");
    
    public String fileName;
    
    ConfigType(String file) {
        this.fileName = file;
    }
    
    /**
     * @return config file of the matching file name
     */
    public File getFile() {
        return new File(Volleyball.getInstance().getDataFolder(), fileName);
    }
    
    /**
     * @return input stream of resource from inside plugin jar
     */
    public InputStream getResource() {
        return Volleyball.getInstance().getResource(fileName);
    }
    
    /**
     * save resource from plugin jar to plugin folder
     */
    public void saveResource() {
        Volleyball.getInstance().saveResource(fileName, false);
    }
}
