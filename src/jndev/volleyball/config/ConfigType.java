package jndev.volleyball.config;

import jndev.volleyball.VolleyBall;

import java.io.File;
import java.io.InputStream;

/**
 * enum to make getting separate configs easier
 */
public enum ConfigType {
    COURT("courts.yml"), MESSAGE("messages.yml");
    
    /**
     * config type file name
     */
    public String fileName;
    
    /**
     * @param file file name associated with the type
     */
    ConfigType(String file) {
        this.fileName = file;
    }
    
    /**
     * @return config file of the matching file name
     */
    public File getFile() {
        return new File(VolleyBall.getInstance().getDataFolder(), fileName);
    }
    
    /**
     * @return input stream of resource from inside plugin jar
     */
    public InputStream getResource() {
        return VolleyBall.getInstance().getResource(fileName);
    }
    
    /**
     * save resource from plugin jar to plugin folder
     */
    public void saveResource() {
        VolleyBall.getInstance().saveResource(fileName, false);
    }
}
