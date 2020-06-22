package jndev.volleyball.config;

/**
 * initialize all configs
 */
public class Configs {
    
    /**
     * court config instance
     */
    private static ConfigManager court = new ConfigManager(ConfigType.COURT);
    
    /**
     * message config instance
     */
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
