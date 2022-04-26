package xyz.jeremynoesen.volleyball.court;

import xyz.jeremynoesen.volleyball.VolleyBall;
import xyz.jeremynoesen.volleyball.Config;

/**
 * class dedicated to loading and saving courts from and to file
 *
 * @author Jeremy Noesen
 */
public class CourtIO {
    
    /**
     * court config instance
     */
    private static final Config courtConfig = Config.getCourtConfig();
    
    
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
        if (courtConfig.getConfig().get(name + ".animations") != null)
            court.setAnimations(courtConfig.getConfig().getBoolean(name + ".animations"));
        if (courtConfig.getConfig().get(name + ".enabled") != null)
            court.setEnabled(courtConfig.getConfig().getBoolean(name + ".enabled"));
        if (courtConfig.getConfig().get(name + ".speed") != null)
            court.setSpeed(courtConfig.getConfig().getDouble(name + ".speed"));
        if (courtConfig.getConfig().get(name + ".hitradius") != null)
            court.setHitRadius(courtConfig.getConfig().getDouble(name + ".hitradius"));
        if (courtConfig.getConfig().get(name + ".texture") != null)
            court.setTexture(courtConfig.getConfig().getString(name + ".texture"));
        if (courtConfig.getConfig().get(name + ".restrictions") != null)
            court.setRestrictions(courtConfig.getConfig().getBoolean(name + ".restrictions"));
        if (courtConfig.getConfig().get(name + ".court") != null) {
            double[][] bounds = new double[2][3];
            bounds[0][0] = courtConfig.getConfig().getDouble(name + ".court.min.x");
            bounds[0][1] = courtConfig.getConfig().getDouble(name + ".court.min.y");
            bounds[0][2] = courtConfig.getConfig().getDouble(name + ".court.min.z");
            bounds[1][0] = courtConfig.getConfig().getDouble(name + ".court.max.x");
            bounds[1][1] = courtConfig.getConfig().getDouble(name + ".court.max.y");
            bounds[1][2] = courtConfig.getConfig().getDouble(name + ".court.max.z");
            String world = courtConfig.getConfig().getString(name + ".world");
            court.setBounds(bounds);
            court.setWorld(VolleyBall.getInstance().getServer().getWorld(world));
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
    
    /**
     * save all courts to file
     */
    public static void saveAll() {
        for (String key : courtConfig.getConfig().getKeys(false)) {
            courtConfig.getConfig().set(key, null);
        }
        for (Court court : Court.getCourts().values()) {
            saveCourt(court);
        }
        courtConfig.saveConfig();
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
        courtConfig.getConfig().set(name + ".hitradius", court.getHitRadius());
        courtConfig.getConfig().set(name + ".texture", court.getTexture());
        courtConfig.getConfig().set(name + ".restrictions", court.hasRestrictions());
        if (court.getBounds() != null) {
            double[][] bounds = court.getBounds();
            courtConfig.getConfig().set(name + ".court.min.x", bounds[0][0]);
            courtConfig.getConfig().set(name + ".court.min.y", bounds[0][1]);
            courtConfig.getConfig().set(name + ".court.min.z", bounds[0][2]);
            courtConfig.getConfig().set(name + ".court.max.x", bounds[1][0]);
            courtConfig.getConfig().set(name + ".court.max.y", bounds[1][1]);
            courtConfig.getConfig().set(name + ".court.max.z", bounds[1][2]);
            courtConfig.getConfig().set(name + ".world", court.getWorld().getName());
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
    }
}
