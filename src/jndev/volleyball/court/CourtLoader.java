package jndev.volleyball.court;

import jndev.volleyball.config.ConfigManager;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.config.Configs;

/**
 * class dedicated to loading a court from file
 */
public class CourtLoader {
    
    /**
     * court config instance
     */
    private static ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
    
    
    /**
     * load all courts in file
     */
    public static void loadAll() {
        for (String name : courtConfig.getConfig().getKeys(false)) {
            loadCourt(name);
        }
    }
    
    /**
     * load one court by name
     *
     * @param name name of court to load
     */
    private static void loadCourt(String name) {
        Court court = new Court(name);
            court.setAnimations(courtConfig.getConfig().getBoolean(name + ".animations"));
            court.setEnabled(courtConfig.getConfig().getBoolean(name + ".enabled"));
            court.setSpeed(courtConfig.getConfig().getDouble(name + ".speed"));
            court.setTexture(courtConfig.getConfig().getString(name + ".texture"));
        if (courtConfig.getConfig().get(name + ".bounds") != null) {
            double[][] bounds = new double[2][3];
            bounds[0][0] = courtConfig.getConfig().getDouble(name + ".court.min.x");
            bounds[0][1] = courtConfig.getConfig().getDouble(name + ".court.min.y");
            bounds[0][2] = courtConfig.getConfig().getDouble(name + ".court.min.z");
            bounds[1][0] = courtConfig.getConfig().getDouble(name + ".court.max.x");
            bounds[1][1] = courtConfig.getConfig().getDouble(name + ".court.max.y");
            bounds[1][2] = courtConfig.getConfig().getDouble(name + ".court.max.z");
            court.setBounds(bounds);
        }
        if (courtConfig.getConfig().get(name + ".net") != null) {
            double[][] net = new double[2][3];
            net[0][0] = courtConfig.getConfig().getDouble(name + ".net.min.x");
            net[0][1] = courtConfig.getConfig().getDouble(name + ".net.min.y");
            net[0][2] = courtConfig.getConfig().getDouble(name + ".net.min.z");
            net[1][0] = courtConfig.getConfig().getDouble(name + ".net.max.x");
            net[1][1] = courtConfig.getConfig().getDouble(name + ".net.max.y");
            net[1][2] = courtConfig.getConfig().getDouble(name + ".net.max.z");
            court.setNet(net);
        }
    }
}
