package xyz.jeremynoesen.volleyball.court;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.jeremynoesen.volleyball.Message;
import xyz.jeremynoesen.volleyball.VolleyBall;
import xyz.jeremynoesen.volleyball.ball.Ball;

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
    private static final HashMap<String, Court> courts = new HashMap<>();

    /**
     * ball used in court
     */
    private Ball ball;

    /**
     * scores per team
     */
    private final int[] teamScores;

    /**
     * whether the court has animation enabled
     */
    private boolean animations;

    /**
     * court bounds
     */
    private double[][] bounds;

    /**
     * whether the court is enabled
     */
    private boolean enabled;

    /**
     * whether hint messages show up or not
     */
    private boolean hints;

    /**
     * radius around player to check for ball when hitting
     */
    private double hitRadius;

    /**
     * name of the court
     */
    private String name;

    /**
     * net bounds
     */
    private double[][] net;

    /**
     * whether the court has particles visible
     */
    private boolean particles;

    /**
     * whether the court will keep the ball within the bounds
     */
    private boolean restrictions;

    /**
     * whether to enable scoring
     */
    private boolean scoring;

    /**
     * whether the court has sounds enabled
     */
    private boolean sounds;

    /**
     * speed modifier of ball
     */
    private double speed;

    /**
     * whether team scoring is enabled
     */
    private boolean teams;

    /**
     * whether blocks can be placed or broken in the court
     */
    private boolean editable;

    /**
     * player head texture for the ball
     */
    private String texture;

    /**
     * world the court is in
     */
    private World world;

    /**
     * selected points for setting bounds
     */
    private final HashMap<Player, int[][]> boundsSelections;

    /**
     * selected points for setting net
     */
    private final HashMap<Player, int[][]> netSelections;

    /**
     * create a court with default values with the specified name
     *
     * @param name court name
     */
    public Court(String name) {
        this.name = name;
        animations = true;
        enabled = false;
        hints = true;
        hitRadius = 1;
        particles = true;
        restrictions = true;
        scoring = true;
        sounds = true;
        speed = 1;
        teams = false;
        editable = false;
        texture = "http://textures.minecraft.net/texture/1e6df1c2bf5b918f6c8879af4c4ecc1dabbe37229284e1f8f6bccdb1ea3d0ac";
        world = VolleyBall.getInstance().getServer().getWorlds().get(0);

        boundsSelections = new HashMap<>();
        netSelections = new HashMap<>();

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

        teamScores = new int[2];
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
     * add +1 to the score for a team
     *
     * @param team team 1 or 2
     */
    public void addScore(int team) {
        teamScores[team - 1]++;
    }

    /**
     * get the score for a team
     *
     * @param team team 1 or 2
     * @return score of team
     */
    public int getScore(int team) {
        return teamScores[team - 1];
    }

    /**
     * reset scores of all teams to 0
     */
    public void clearScores() {
        teamScores[0] = 0;
        teamScores[1] = 0;
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
     * get the court bounds
     *
     * @return 2D array bounds
     */
    public double[][] getBounds() {
        return bounds;
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
     * set the court bounds based on the selected positions from a player
     *
     * @param player player setting the bounds
     */
    public void setBounds(Player player) {
        setCuboid(player, boundsSelections, bounds);
    }

    /**
     * select the court bounds based on the player location
     *
     * @param player player selecting bounds
     * @param pos    position to select (1 or 2)
     */
    public void selectBounds(Player player, int pos) {
        selectCuboid(player, pos, boundsSelections);
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
     * check if the court has hints enabled
     *
     * @return true if court has hints
     */
    public boolean hasHints() {
        return hints;
    }

    /**
     * enable or disable hints
     *
     * @param hints true to enable
     */
    public void setHints(boolean hints) {
        this.hints = hints;
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
     * set the net bounds based on the selected positions from a player
     *
     * @param player player setting the bounds
     */
    public void setNet(Player player) {
        setCuboid(player, netSelections, net);
    }

    /**
     * select the net bounds based on the player location
     *
     * @param player player selecting bounds
     * @param pos    position to select (1 or 2)
     */
    public void selectNet(Player player, int pos) {
        selectCuboid(player, pos, netSelections);
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
     * check if the court has scoring enabled
     *
     * @return true if court has scoring
     */
    public boolean hasScoring() {
        return scoring;
    }

    /**
     * enable or disable scoring
     *
     * @param scoring true to enable
     */
    public void setScoring(boolean scoring) {
        this.scoring = scoring;
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
     * check if a court has teams enabled
     *
     * @return true if teams are enabled
     */
    public boolean hasTeams() {
        return teams;
    }

    /**
     * enable or disable team scoring
     *
     * @param teams true to enable
     */
    public void setTeams(boolean teams) {
        this.teams = teams;
    }

    /**
     * check if a court is editable
     *
     * @return true if court is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * enable or disable court editing
     *
     * @param editable true to enable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
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
     * set bounds or net based on player selections
     *
     * @param player     player setting bounds or net
     * @param selections map of currently selected positions
     * @param cuboid     region to set positions for
     */
    private void setCuboid(Player player, HashMap<Player, int[][]> selections, double[][] cuboid) {
        int[][] selection = selections.get(player);
        cuboid[0][0] = Math.min(selection[0][0], selection[1][0]);
        cuboid[0][1] = Math.min(selection[0][1], selection[1][1]);
        cuboid[0][2] = Math.min(selection[0][2], selection[1][2]);
        cuboid[1][0] = Math.max(selection[0][0], selection[1][0]);
        cuboid[1][1] = Math.max(selection[0][1], selection[1][1]);
        cuboid[1][2] = Math.max(selection[0][2], selection[1][2]);
    }

    /**
     * select a position to be used for bounds or net setting
     *
     * @param player     player selecting a position
     * @param pos        position being selected
     * @param selections map to place selected positions in
     */
    private void selectCuboid(Player player, int pos, HashMap<Player, int[][]> selections) {
        int[][] cuboid;

        if (selections.get(player) == null) {
            cuboid = new int[2][3];
        } else {
            cuboid = selections.get(player);
        }

        if (pos == 1 || pos == 2) {
            cuboid[pos - 1][0] = player.getLocation().getBlock().getX();
            cuboid[pos - 1][1] = player.getLocation().getBlock().getY();
            cuboid[pos - 1][2] = player.getLocation().getBlock().getZ();
        }

        selections.put(player, cuboid);
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
            double maxy = net[1][1];
            double minz = net[0][2];
            double tox = l.getBlock().getLocation().getX();
            double toy = l.getBlock().getLocation().getY();
            double toz = l.getBlock().getLocation().getZ();

            return (l.getWorld().equals(world) && (tox <= maxx) && (tox >= minx) &&
                    (toy >= maxy) && (toz <= maxz) && (toz >= minz));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * get the team based on the location on the court
     *
     * @param loc location to check
     * @return team 1 or 2, or 0 if neither
     */
    public int getSide(Location loc) {
        if (contains(loc) && !isAboveNet(loc)) {
            if (net[1][0] - net[0][0] > net[1][2] - net[0][2]) {
                if (loc.getZ() > net[1][2]) {
                    return 1;
                } else if (loc.getZ() < net[0][2]) {
                    return 2;
                }
            } else {
                if (loc.getX() > net[1][0]) {
                    return 1;
                } else if (loc.getX() < net[0][0]) {
                    return 2;
                }
            }
        }
        return 0;
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

    /**
     * get the court in a decorated string format for chat
     *
     * @return court as a colored string
     */
    @Override
    public String toString() {
        return Message.COURT_INFO
                .replace("$ANIMATIONS$", Boolean.toString(hasAnimations()))
                .replace("$EDITABLE$", Boolean.toString(isEditable()))
                .replace("$ENABLED$", Boolean.toString(isEnabled()))
                .replace("$HINTS$", Boolean.toString(hasHints()))
                .replace("$RADIUS$", Double.toString(getHitRadius()))
                .replace("$NAME$", name)
                .replace("$PARTICLES$", Boolean.toString(hasParticles()))
                .replace("$RESTRICTIONS$", Boolean.toString(hasRestrictions()))
                .replace("$SCORING$", Boolean.toString(hasScoring()))
                .replace("$SOUNDS$", Boolean.toString(hasSounds()))
                .replace("$SPEED$", Double.toString(getSpeed()))
                .replace("$TEAMS$", Boolean.toString(hasTeams()))
                .replace("$TEXTURE$", getTexture())
                .replace("$WORLD$", world.getName())
                .replace("$BOUNDS$",
                        "(" + bounds[0][0] + "," + bounds[0][1] + "," + bounds[0][2] + ") to (" +
                                bounds[1][0] + "," + bounds[1][1] + "," + bounds[1][2] + ")")
                .replace("$NET$",
                        "(" + net[0][0] + "," + net[0][1] + "," + net[0][2] + ") to (" +
                                net[1][0] + "," + net[1][1] + "," + net[1][2] + ")");

    }
}
