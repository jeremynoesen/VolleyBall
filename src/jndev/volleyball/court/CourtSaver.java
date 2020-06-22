package jndev.volleyball.court;

import jndev.volleyball.config.ConfigManager;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.config.Configs;

/**
 * class dedicated to saving courts to file
 */
public class CourtSaver {
    
    /**
     * court config instance
     */
    private static ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
    
    /**
     * save all courts to file
     */
    public static void saveAll() {
        for(Court court : Court.getCourts().values()) {
            saveCourt(court);
        }
    }
    
    /**
     * save a court to file
     *
     * @param court court to save
     */
    private static void saveCourt(Court court) {
        String name = court.getName();
    
        courtConfig.getConfig().set(name + ".animations", court.hasAnimations());
        courtConfig.getConfig().set(name + ".enabled", court.isEnabled());
        courtConfig.getConfig().set(name + ".speed", court.getSpeed());
        courtConfig.getConfig().set(name + ".texture", court.getTexture());
        if (court.getBounds() != null) {
            double[][] bounds = court.getBounds();
            courtConfig.getConfig().set(name + ".court.min.x", bounds[0][0]);
            courtConfig.getConfig().set(name + ".court.min.y", bounds[0][1]);
            courtConfig.getConfig().set(name + ".court.min.z", bounds[0][2]);
            courtConfig.getConfig().set(name + ".court.max.x", bounds[1][0]);
            courtConfig.getConfig().set(name + ".court.max.y", bounds[1][1]);
            courtConfig.getConfig().set(name + ".court.max.z", bounds[1][2]);
        }
        if (court.getNet() != null) {
            double[][] net = court.getNet();
            courtConfig.getConfig().set(name + ".net.min.x", net[0][0]);
            courtConfig.getConfig().set(name + ".net.min.y", net[0][1]);
            courtConfig.getConfig().set(name + ".net.min.z", net[0][2]);
            courtConfig.getConfig().set(name + ".net.max.x", net[1][0]);
            courtConfig.getConfig().set(name + ".net.max.y", net[1][1]);
            courtConfig.getConfig().set(name + ".net.max.z", net[1][2]);
        }
        
        courtConfig.saveConfig();
        
    }
}
