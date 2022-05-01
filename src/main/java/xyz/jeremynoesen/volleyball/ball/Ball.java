package xyz.jeremynoesen.volleyball.ball;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import xyz.jeremynoesen.volleyball.Message;
import xyz.jeremynoesen.volleyball.VolleyBall;
import xyz.jeremynoesen.volleyball.court.Court;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Ball creation class
 *
 * @author Jeremy Noesen
 */
public class Ball {

    /**
     * list of all alive ball entities
     */
    private static Set<Entity> balls = new HashSet<>();

    /**
     * armorstand to make ball physics and wear the head
     */
    private final ArmorStand ball;

    /**
     * whether the ball removal has been started or not
     */
    private boolean end;

    /**
     * player serving this ball
     */
    private final Player player;

    /**
     * whether the ball has gone over the net or not
     */
    private boolean volleyed;

    /**
     * number of times the ball has gone over the net
     */
    private int volleys;

    /**
     * team that last hit the ball
     */
    private int lastHit;

    /**
     * number of times the ball was hit once per side and per volley
     */
    private int hits;

    /**
     * court this ball is on
     */
    private final Court court;

    /**
     * Creates a new ball
     *
     * @param player player to create the ball at
     */
    public Ball(Player player) {
        this.player = player;
        this.end = false;
        this.volleyed = false;
        this.volleys = 0;
        this.lastHit = 0;
        this.hits = 0;
        this.court = Court.get(player.getLocation());

        Location loc = player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.75).setY(-0.5));
        if (!court.hasAnimations()) {
            loc.setYaw(0);
            loc.setPitch(0);
        }

        this.ball = player.getLocation().getWorld().spawn(loc, ArmorStand.class);
        ball.setSmall(true);
        ball.setSilent(true);
        ball.setInvulnerable(true);
        ball.setVisible(false);
        ball.setBasePlate(false);
        setTexture(court.getTexture());

        balls.add(ball);
    }

    /**
     * @param url link to the player skin to get the skull from
     */
    public void setTexture(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        if (url.isEmpty())
            return;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        ball.getEquipment().setHelmet(head);
    }

    /**
     * serves the volleyball and starts all loops
     */
    public void serve() {
        court.setBall(this);
        boolean animations = court.hasAnimations();
        boolean particles = court.hasParticles();
        boolean sounds = court.hasSounds();
        boolean restricted = court.hasRestrictions();
        boolean scoring = court.hasScoring();
        boolean teams = court.hasTeams();

        if (sounds)
            ball.getWorld().playSound(ball.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 0);

        ball.setVelocity(player.getLocation().getDirection().multiply(0.05).add(new Vector(0, 0.5 + (0.05 * court.getSpeed()), 0)));

        if (particles) {
            Location loc = player.getLocation();
            double radius = 0.5;
            for (double y = 0; y <= Math.PI * 2; y += 0.175) {
                double x = radius * Math.cos(y);
                double z = radius * Math.sin((Math.PI * 2) - y);
                player.getWorld().spawnParticle(Particle.TOTEM,
                        loc.getX() + x, loc.getY() + 2, loc.getZ() + z, 1, 0, 0, 0, 0);
            }
        }

        double[] rot = {0, 0, 0};

        new BukkitRunnable() {
            @Override
            public void run() {

                if (!end) {

                    ball.setFallDistance(0);

                    if (restricted) {
                        Vector vec = ball.getVelocity();
                        Location loc = ball.getLocation();
                        if (loc.getBlock().getX() < court.getBounds()[0][0] || loc.getBlock().getX() > court.getBounds()[1][0]) {
                            ball.setVelocity(vec.setX(-vec.getX() * 1.25));
                            if (sounds) ball.getWorld().playSound(ball.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        }
                        if (loc.getBlock().getY() < court.getBounds()[0][1] || loc.getBlock().getY() > court.getBounds()[1][1]) {
                            ball.setVelocity(vec.setY(-vec.getY() * 1.25));
                            if (sounds) ball.getWorld().playSound(ball.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        }
                        if (loc.getBlock().getZ() < court.getBounds()[0][2] || loc.getBlock().getZ() > court.getBounds()[1][2]) {
                            ball.setVelocity(vec.setZ(-vec.getZ() * 1.25));
                            if (sounds) ball.getWorld().playSound(ball.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        }
                    }

                    if (particles) {
                        ball.getWorld().spawnParticle(Particle.CRIT,
                                ball.getLocation().add(new Vector(0, 0.75, 0)), 1, 0, 0, 0, 0);
                    }

                    if (animations) {
                        ball.setHeadPose(new EulerAngle(rot[0], rot[1], rot[2]));
                        rot[0] += (Math.abs(ball.getVelocity().getY()) + Math.abs(ball.getVelocity().getZ())) / (Math.PI * 2);
                        rot[1] += (Math.abs(ball.getVelocity().getX()) + Math.abs(ball.getVelocity().getZ())) / (Math.PI * 2);
                        rot[2] += (Math.abs(ball.getVelocity().getY()) + Math.abs(ball.getVelocity().getX())) / (Math.PI * 2);
                    }

                    if (scoring) {
                        if (court.isAboveNet(ball.getLocation()) && !volleyed) {
                            volleyed = true;
                        } else if (!court.isAboveNet(ball.getLocation()) && volleyed) {
                            volleyed = false;
                            volleys++;
                            if (!teams) {
                                for (Player players : court.getPlayersOnCourt()) {
                                    players.sendTitle(" ", Message.SCORE_TITLE.replace("$SCORE$", Integer.toString(volleys)), 0, 10, 10);
                                    if (sounds)
                                        players.playSound(players.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
                                }
                            }
                        }
                    }
                }

                if (ball.isDead()) {
                    end = true;
                    this.cancel();
                }

                if (ball.isOnGround() || ball.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (ball.isOnGround() || ball.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {
                                if (!end) {
                                    if (scoring && teams && lastHit != 0) {
                                        if (volleys == hits && court.contains(ball.getLocation())) {
                                            court.addScore(lastHit);
                                        } else {
                                            court.addScore(lastHit == 1 ? 2 : 1);
                                        }
                                        for (Player players : court.getPlayersOnCourt()) {
                                            players.sendTitle(" ", Message.TEAM_SCORE_TITLE
                                                            .replace("$SCORE1$", Integer.toString(court.getScore(1)))
                                                            .replace("$SCORE2$", Integer.toString(court.getScore(2))),
                                                    0, 10, 10);
                                            if (sounds)
                                                players.playSound(players.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
                                        }
                                    }
                                    remove();
                                }
                            }
                        }
                    }.runTaskLater(VolleyBall.getInstance(), 3);
                }
            }
        }.runTaskTimer(VolleyBall.getInstance(), 0, 1);
    }

    /**
     * make a player hit the nearest ball
     *
     * @param player player hitting
     */
    public void hit(Player player) {
        double hitRadius = court.getHitRadius();
        for (Entity s : player.getNearbyEntities(hitRadius, 1 + ((0.1 * hitRadius) - 0.1), hitRadius)) {
            if (s.equals(ball) && court.getBall().isOut()) {
                if (court.hasSounds())
                    ball.getWorld().playSound(ball.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 0);
                ball.setVelocity(player.getLocation().getDirection().setY(Math.abs(player.getLocation().getDirection().getY()))
                        .normalize().add(player.getVelocity().multiply(0.25)).multiply(court.getSpeed())
                        .add(new Vector(0, Math.max(0, player.getEyeHeight() - ball.getLocation().getY()), 0)));
                int hit = court.getSide(player.getLocation());
                if (hit != lastHit) hits++;
                lastHit = hit;
                break;
            }
        }
    }

    /**
     * removes the volleyball with or without animations
     */
    public void remove() {
        boolean animated = court.hasAnimations();
        boolean particles = court.hasParticles();
        end = true;
        double radius = 1;
        Location loc = ball.getLocation().clone();
        for (double y = 0; y <= 6.28; y += 1.04) {
            double finalY = y;
            new BukkitRunnable() {
                @Override
                public void run() {
                    double x = (radius - 0.14 * finalY) * Math.cos(finalY);
                    double z = (radius - 0.14 * finalY) * Math.sin(finalY);
                    if (particles)
                        ball.getWorld().spawnParticle(Particle.CLOUD,
                                loc.getX() + x, loc.getY() + 0.3, loc.getZ() + z, 1, 0, 0, 0, 0);
                    if (animated) {
                        loc.setYaw((float) finalY * 20);
                        loc.setY(loc.subtract(0, 0.1 * finalY, 0).getY());
                        ball.teleport(loc);
                    }
                }
            }.runTaskLater(VolleyBall.getInstance(), (long) y);
        }
        Location loc1 = ball.getLocation().clone();
        for (double y = 0; y <= 6.28; y += 0.2) {
            double finalY = y;
            new BukkitRunnable() {
                @Override
                public void run() {
                    double x = (radius - 0.14 * finalY) * Math.cos(finalY + 3.14159);
                    double z = (radius - 0.14 * finalY) * Math.sin(finalY + 3.14159);
                    if (particles)
                        ball.getWorld().spawnParticle(Particle.CLOUD,
                                loc1.getX() + x, loc1.getY() + 0.3, loc1.getZ() + z, 1, 0, 0, 0, 0);
                }
            }.runTaskLater(VolleyBall.getInstance(), (long) y);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                balls.remove(ball);
                ball.remove();
            }
        }.runTaskLater(VolleyBall.getInstance(), (long) 6.28);

        if (court.hasSounds()) {
            ball.getWorld().playSound(ball.getLocation(), Sound.BLOCK_SAND_PLACE, 1, 1);
            ball.getWorld().playSound(ball.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
        }
    }

    /**
     * check if the ball is out
     *
     * @return true if ball is out
     */
    public boolean isOut() {
        return !end;
    }

    /**
     * get the set of all alive ball entities
     *
     * @return set of alive ball entities
     */
    public static Set<Entity> getBalls() {
        return balls;
    }
}
