package jeremynoesen.volleyball.config;

/**
 * initialize all configs
 *
 * @author Jeremy Noesen
 */
public class Configs {
    
    /**
     * court config instance
     */
    private static final Config court = new Config(ConfigType.COURT);
    
    /**
     * message config instance
     */
    private static final Config message = new Config(ConfigType.MESSAGE);
    
    /**
     * @param type config type
     * @return config manager for the selected type
     */
    public static Config getConfig(ConfigType type) {
        
        switch (type) {
            case MESSAGE:
                return message;
            
            case COURT:
                return court;
            
        }
        
        return null;
    }
    
}
