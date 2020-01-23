package me.Jeremaster101.VolleyBall.Config;

/**
 * initialize all configs
 */
public class Configs {
    
    private static ConfigManager court = new ConfigManager(ConfigType.COURT);
    private static ConfigManager message = new ConfigManager(ConfigType.MESSAGE);
    
    /**
     * @param type config type
     * @return config manager for the selected type
     */
    public static ConfigManager getConfig(ConfigType type) {
        
        switch (type) {
            case MESSAGE:
                return message;
            
            case COURT:
                return court;
            
        }
        
        return null;
    }
    
}
