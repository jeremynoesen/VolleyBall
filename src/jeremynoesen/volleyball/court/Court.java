package jeremynoesen.volleyball.court;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import jeremynoesen.volleyball.Message;
import jeremynoesen.volleyball.VolleyBall;
import jeremynoesen.volleyball.ball.Ball;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * volleyball court with customizable ball stats and court size
 *
 * @author Jeremy Noesen
 */
public class Court {
    
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
        restrictions = true;
        speed = 1;
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
        Courts.add(this, name);
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
        Courts.remove(this);
        this.name = name;
        Courts.add(this, name);
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
     * get the court bounds
     *
     * @return 2D array bounds
     */
    public double[][] getBounds() {
        return bounds;
    }
    
    /**
     * set bounds using worldedit
     *
     * @param player worldedit player
     */
    private void setBounds(Player player, double[][] bounds) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        
        com.sk89q.worldedit.regions.Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
            return;
        }
        
        if (selection != null) {
            bounds[0][0] = selection.getMinimumPoint().getX();
            bounds[0][1] = selection.getMinimumPoint().getY();
            bounds[0][2] = selection.getMinimumPoint().getZ();
            bounds[1][0] = selection.getMaximumPoint().getX();
            bounds[1][1] = selection.getMaximumPoint().getY();
            bounds[1][2] = selection.getMaximumPoint().getZ();
            world = BukkitAdapter.adapt(selection.getWorld());
            
            
            player.sendMessage(Message.SUCCESS_SET_BOUNDS.replace("$COURT$", name));
            
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
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
     * set court bounds using worldedit
     *
     * @param player worldedit player
     */
    public void setBounds(Player player) {
        setBounds(player, bounds);
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
     * set the net bounds with worldedit
     *
     * @param player worldedit player
     */
    public void setNet(Player player) {
        setBounds(player, net);
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
     * select a court with worldedit
     *
     * @param player player to do the selecting
     */
    public void select(Player player) {
        
        try {
            BlockVector3 min = BlockVector3.at(bounds[0][0], bounds[0][1], bounds[0][2]);
            BlockVector3 max = BlockVector3.at(bounds[1][0], bounds[1][1], bounds[1][2]);
            
            com.sk89q.worldedit.entity.Player weplayer = BukkitAdapter.adapt(player);
            LocalSession ls = WorldEdit.getInstance().getSessionManager().get(weplayer);
            ls.getRegionSelector(BukkitAdapter.adapt(world)).selectPrimary(max, null);
            ls.getRegionSelector(BukkitAdapter.adapt(world)).selectSecondary(min, null);
            
            player.sendMessage(Message.SUCCESS_COURT_SELECTED.replace("$COURT$", name));
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_UNKNOWN_COURT.replace("$COURT$", name));
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
                .replace("$SPEED$", Double.toString(getSpeed()))
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
}
