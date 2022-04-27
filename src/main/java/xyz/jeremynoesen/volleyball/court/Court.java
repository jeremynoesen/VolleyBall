package xyz.jeremynoesen.volleyball.court;

import xyz.jeremynoesen.volleyball.Message;
import xyz.jeremynoesen.volleyball.VolleyBall;
import xyz.jeremynoesen.volleyball.ball.Ball;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * volleyball court with customizable ball stats and court size
 *
 * @author Jeremy Noesen
 */
public class Court {
    
    /**
     * all loaded courts
     */
    private static HashMap<String, Court> courts = new HashMap<>();
    
    /**
     * name of the court
     */
    private String name;
    
    /**
     * whether the court is enabled
     */
    private boolean enabled;
    
    /**
     * whether the court has animation enabled
     */
    private boolean animations;

    /**
     * whether the court has particles visible
     */
    private boolean particles;

    /**
     * whether the court has sounds enabled
     */
    private boolean sounds;
    
    /**
     * whether the court will keep the ball within the bounds
     */
    private boolean restrictions;
    
    /**
     * player head texture for the ball
     */
    private String texture;
    
    /**
     * world the court is in
     */
    private World world;
    
    /**
     * speed modifier of ball
     */
    private double speed;

    /**
     * radius around player to check for ball when hitting
     */
    private double hitRadius;
    
    /**
     * court bounds
     */
    private double[][] bounds;
    
    /**
     * net bounds
     */
    private double[][] net;
    
    /**
     * ball used in court
     */
    private Ball ball;
    
    /**
     * create a court with default values with the specified name
     *
     * @param name court name
     */
    public Court(String name) {
        this.name = name;
        enabled = false;
        animations = true;
        particles = true;
        sounds = true;
        restrictions = true;
        speed = 1;
        hitRadius = 1;
        texture = "http://textures.minecraft.net/texture/9b2513c8d08c60ad3785d3a9a651b7329c5f26937aca2fc8dfaf3441c9bd9da2";
        world = VolleyBall.getInstance().getServer().getWorlds().get(0);
        
        bounds = new double[2][3];
        bounds[0][0] = 0;
        bounds[0][1] = 0;
        bounds[0][2] = 0;
        bounds[1][0] = 0;
        bounds[1][1] = 0;
        bounds[1][2] = 0;
        
        net = new double[2][3];
        net[0][0] = 0;
        net[0][1] = 0;
        net[0][2] = 0;
        net[1][0] = 0;
        net[1][1] = 0;
        net[1][2] = 0;
        
        ball = null;
        courts.put(name, this);
    }
    
    /**
     * get all loaded courts
     *
     * @return hashmap of loaded courts
     */
    public static HashMap<String, Court> getCourts() {
        return courts;
    }
    
    /**
     * get the name of this court
     *
     * @return name of court
     */
    public String getName() {
        return name;
    }
    
    /**
     * set the name of this court
     *
     * @param name new name
     */
    public void setName(String name) {
        courts.remove(this.name);
        this.name = name;
        courts.put(name, this);
    }
    
    /**
     * check if a court is enabled
     *
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * enable or disable a court
     *
     * @param enabled true to enable
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * check if a court has animations enabled
     *
     * @return true if animations are enabled
     */
    public boolean hasAnimations() {
        return animations;
    }
    
    /**
     * enable or disable animations for a court
     *
     * @param animations true to enable
     */
    public void setAnimations(boolean animations) {
        this.animations = animations;
    }

    /**
     * check if a court has particles enabled
     *
     * @return true if particles are enabled
     */
    public boolean hasParticles() {
        return particles;
    }

    /**
     * enable or disable animations for a court
     *
     * @param particles true to enable
     */
    public void setParticles(boolean particles) {
        this.particles = particles;
    }

    /**
     * check if a court has sounds enabled
     *
     * @return true if sounds are enabled
     */
    public boolean hasSounds() {
        return sounds;
    }

    /**
     * enable or disable sounds for a court
     *
     * @param sounds true to enable
     */
    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }
    
    /**
     * get the url for the ball texture
     *
     * @return string url of ball texture
     */
    public String getTexture() {
        return texture;
    }
    
    /**
     * set the ball texture for this court
     *
     * @param texture string url of ball texture
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }
    
    /**
     * get the world the court is in
     *
     * @return world the court is in
     */
    public World getWorld() {
        return world;
    }
    
    /**
     * set the world the court is in
     *
     * @param world world
     */
    public void setWorld(World world) {
        this.world = world;
    }
    
    /**
     * get the speed modifier for the court's ball
     *
     * @return speed of ball
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * set the speed modifier for the court's ball
     *
     * @param speed speed modifier
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * get the hit radius for the court's ball
     *
     * @return hit radius of ball
     */
    public double getHitRadius() {
        return hitRadius;
    }

    /**
     * set the hit radius for the court's ball
     *
     * @param hitRadius hit radius
     */
    public void setHitRadius(double hitRadius) {
        this.hitRadius = hitRadius;
    }
    
    /**
     * get the court bounds
     *
     * @return 2D array bounds
     */
    public double[][] getBounds() {
        return bounds;
    }

    /**
     * set bounds based on the position of the player
     *
     * @param player player setting bounds
     * @param pos position to set (1 or 2)
     */
    private void setBounds(Player player, double[][] bounds, int pos) {
        if (pos == 1 || pos == 2) {
            bounds[pos - 1][0] = player.getLocation().getBlock().getX();
            bounds[pos - 1][1] = player.getLocation().getBlock().getY();
            bounds[pos - 1][2] = player.getLocation().getBlock().getZ();
            world = player.getWorld();

            if (bounds[0][0] > bounds[1][0]) {
                double temp = bounds[0][0];
                bounds[0][0] = bounds[1][0];
                bounds[1][0] = temp;
            }

            if (bounds[0][1] > bounds[1][1]) {
                double temp = bounds[0][1];
                bounds[0][1] = bounds[1][1];
                bounds[1][1] = temp;
            }

            if (bounds[0][2] > bounds[1][2]) {
                double temp = bounds[0][2];
                bounds[0][2] = bounds[1][2];
                bounds[1][2] = temp;
            }
        }
    }
    
    /**
     * set the court bounds
     *
     * @param bounds 2D array bounds
     */
    public void setBounds(double[][] bounds) {
        this.bounds = bounds;
    }
    
    /**
     * set the court bounds based on the player location
     *
     * @param player player setting bounds
     * @param pos position to set (1 or 2)
     */
    public void setBounds(Player player, int pos) {
        setBounds(player, bounds, pos);
    }
    
    /**
     * get net bounds for court
     *
     * @return 2D array net bounds
     */
    public double[][] getNet() {
        return net;
    }
    
    /**
     * set the court net bounds
     *
     * @param net 2D array net bounds
     */
    public void setNet(double[][] net) {
        this.net = net;
    }
    
    /**
     * set the net bounds based on the player location
     *
     * @param player player setting bounds
     * @param pos position to set (1 or 2)
     */
    public void setNet(Player player, int pos) {
        setBounds(player, net, pos);
    }
    
    /**
     * check if a location is on a court
     *
     * @param l location to check
     * @return true if location is in a court region
     */
    public boolean contains(Location l) {
        try {
            double minx = bounds[0][0];
            double miny = bounds[0][1];
            double minz = bounds[0][2];
            double maxx = bounds[1][0];
            double maxy = bounds[1][1];
            double maxz = bounds[1][2];
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().equals(world) && (tox <= maxx) && (tox >= minx) && (toy <= maxy) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * get a list of all players on the specified court
     *
     * @return list of players on court
     */
    public ArrayList<Player> getPlayersOnCourt() {
        ArrayList<Player> players = new ArrayList<>();
        
        if (world != null)
            for (Player worldPlayer : world.getPlayers()) {
                if (!worldPlayer.isOnline()) continue;
                if (contains(worldPlayer.getLocation())) {
                    players.add(worldPlayer);
                }
            }
        
        return players;
    }
    
    /**
     * check if a player is in a court
     *
     * @param player player to see if court contains
     * @return true if player is on court
     */
    public boolean contains(Player player) {
        return getPlayersOnCourt().contains(player);
    }
    
    /**
     * check if a location is above a net
     *
     * @param l location to check
     * @return true if location is above a net
     */
    public boolean isAboveNet(Location l) {
        
        try {
            double maxx = net[1][0];
            double maxz = net[1][2];
            double minx = net[0][0];
            double miny = net[0][1];
            double minz = net[0][2];
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();
            
            return (l.getWorld().equals(world) && (tox <= maxx) && (tox >= minx) &&
                    (toy >= miny) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * get the court in a decorated string format for chat
     *
     * @return court as a colored string
     */
    @Override
    public String toString() {
        return Message.COURT_INFO
                .replace("$NAME$", name)
                .replace("$ENABLED$", Boolean.toString(isEnabled()))
                .replace("$RESTRICTIONS$", Boolean.toString(hasRestrictions()))
                .replace("$ANIMATIONS$", Boolean.toString(hasAnimations()))
                .replace("$PARTICLES$", Boolean.toString(hasParticles()))
                .replace("$SOUNDS$", Boolean.toString(hasSounds()))
                .replace("$SPEED$", Double.toString(getSpeed()))
                .replace("$RADIUS$", Double.toString(getHitRadius()))
                .replace("$TEXTURE$", getTexture())
                .replace("$WORLD$", world.getName())
                .replace("$BOUNDS$",
                        "(" + bounds[0][0] + "," + bounds[0][1] + "," + bounds[0][2] + ") to (" +
                                bounds[1][0] + "," + bounds[1][1] + "," + bounds[1][2] + ")")
                .replace("$NET$",
                        "(" + net[0][0] + "," + net[0][1] + "," + net[0][2] + ") to (" +
                                net[1][0] + "," + net[1][1] + "," + net[1][2] + ")");
        
    }
    
    /**
     * get the ball on the court
     *
     * @return ball on court
     */
    public Ball getBall() {
        return ball;
    }
    
    /**
     * set the current ball
     *
     * @param ball ball
     */
    public void setBall(Ball ball) {
        this.ball = ball;
    }
    
    /**
     * check if the court has ball restrictions to keep the ball in bounds
     *
     * @return true if court has restrictions
     */
    public boolean hasRestrictions() {
        return restrictions;
    }
    
    /**
     * enable or disable court ball restrictions
     *
     * @param restrictions true to enable
     */
    public void setRestrictions(boolean restrictions) {
        this.restrictions = restrictions;
    }
    
    /**
     * get the court the player is on
     *
     * @param player player to check for courts
     * @return court player is on
     */
    public static Court get(Player player) {
        
        for (Court court : courts.values()) {
            if (court.contains(player)) {
                return court;
            }
        }
        return null;
    }
    
    /**
     * get the court a location is in
     *
     * @param loc location to check
     * @return court location is in
     */
    public static Court get(Location loc) {
        
        for (Court court : courts.values()) {
            if (court.contains(loc)) {
                return court;
            }
        }
        return null;
    }
}
