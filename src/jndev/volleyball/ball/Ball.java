package jndev.volleyball.ball;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import jndev.volleyball.court.Court;
import jndev.volleyball.court.Courts;
import jndev.volleyball.VolleyBall;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Ball creation class
 */
public class Ball {
    
    /**
     * entity responsible for wearing the player head and making the ball hitbox larger
     */
    private Zombie ballTexture;
    
    /**
     * entity used for the ball physics
     */
    private Slime ballPhysics;
    
    /**
     * whether the ball removal has been started or not
     */
    private boolean end = false;
    
    /**
     * player serving this ball
     */
    private Player player;
    
    /**
     * whether the ball has gone over the net or not
     */
    private boolean volleyed = false;
    
    /**
     * number of times the ball has gone over the net
     */
    private int volleys = 0;
    
    /**
     * court this ball is on
     */
    private Court court;
    
    /**
     * Creates a new ball
     *
     * @param player player to create the ball at
     */
    public Ball(Player player) {
        this.player = player;
        
        court = Courts.getCourt(player);
        
        ballPhysics = player.getLocation().getWorld()
                .spawn(player.getEyeLocation().add(player.getLocation().getDirection()).subtract(0, 0.25, 0), Slime.class);
        ballPhysics.setSize(1);
        Location loc = ballPhysics.getLocation();
        loc.setYaw(0);
        loc.setPitch(0);
        ballPhysics.teleport(loc);
        
        ballTexture = ballPhysics.getWorld().spawn(ballPhysics.getLocation().subtract(0, 1.5, 0), Zombie.class);
        setTexture(court.getTexture());
        ballTexture.setGravity(false);
        ballTexture.setCustomName(ChatColor.BLACK + "BALL");
        ballTexture.setCustomNameVisible(false);
        ballTexture.setSilent(true);
        ballTexture.setAI(false);
        ballTexture.setCollidable(false);
        ballTexture.setSilent(true);
        ballTexture.setInvulnerable(true);
        ballTexture.getEquipment().setItemInMainHand(new ItemStack(Material.AIR, 1));
        ballTexture.getEquipment().setItemInOffHand(new ItemStack(Material.AIR, 1));
        ballTexture.getEquipment().setChestplate(new ItemStack(Material.AIR, 1));
        ballTexture.getEquipment().setLeggings(new ItemStack(Material.AIR, 1));
        ballTexture.getEquipment().setBoots(new ItemStack(Material.AIR, 1));
    
    
        ballTexture.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1000000, true, true));
        
        ballPhysics.setCollidable(false);
        ballPhysics.setCustomName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL");
        ballPhysics.setVelocity(player.getLocation().getDirection().multiply(0.1).add(new Vector(0, 1, 0)));
        ballPhysics.getWorld().playSound(ballPhysics.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, 0);
        ballPhysics.setCustomNameVisible(false);
        ballPhysics.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1, true, false));
        ballPhysics.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 255, true, false));
        ballPhysics.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 255, true, false));
        ballPhysics.setSilent(true);
        ballPhysics.setInvulnerable(true);
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
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        ballTexture.getEquipment().setHelmet(head);
    }
    
    /**
     * @return times ball has volleyed
     */
    public int getVolleys() {
        return volleys;
    }
    
    /**
     * serves the volleyball
     */
    public void serve() {
        boolean animated = court.hasAnimations();
        if (animated) {
            Location loc = player.getLocation();
            double radius = 0.5;
            for (double y = 0; y <= 10; y += 0.2) {
                double finalY = y;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double x = (radius + 0.05 * finalY) * Math.cos(finalY);
                        double z = (radius + 0.05 * finalY) * Math.sin(finalY);
                        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,
                                (float) (loc.getX() + x), (float) (loc.getY() + finalY * 0.1),
                                (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                    }
                }.runTaskLater(VolleyBall.getInstance(), (long) y);
            }
        }
        
        new BukkitRunnable() {
            @Override
            public void run() {
                
                if (court.isAboveNet(ballPhysics.getLocation()) && !volleyed) {
                    volleyed = true;
                    volleys++;
                    for (Player players : court.getPlayersOnCourt()) {
                        players.sendTitle("", ChatColor.WHITE + Integer.toString(volleys), 0, 10, 10);
                    }
                } else if (!court.isAboveNet(ballPhysics.getLocation())) {
                    volleyed = false;
                }
                
                ballPhysics.setFallDistance(0);
                if (animated)
                    ballPhysics.getWorld().spawnParticle(Particle.END_ROD, ballPhysics.getLocation(), 0, 0, 0, 0, 1);
                ballPhysics.setTarget(null);
                
                if (ballPhysics.isDead()) {
                    ballTexture.remove();
                    this.cancel();
                }
                ballTexture.setFallDistance(0);
                if (!end) {
                    Location loc = ballPhysics.getLocation();
                    loc.setPitch(0);
                    loc.setYaw(0);
                    ballPhysics.teleport(loc);
                    ballTexture.teleport(ballPhysics.getLocation().subtract(0, 1.5, 0));
                }
    
                if (ballPhysics.isOnGround() || ballPhysics.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {
                    
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (ballPhysics.isOnGround() || ballPhysics.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {
                                if (!end) remove();
                            }
                        }
                    }.runTaskLater(VolleyBall.getInstance(), 3);
                }
            }
        }.runTaskTimer(VolleyBall.getInstance(), 0, 1);
    }
    
    /**
     * removes the volleyball with or without animations
     */
    public void remove() {
        boolean animated = court.hasAnimations();
        if (animated) {
            end = true;
            double radius = 1;
            Location loc = ballPhysics.getLocation();
            for (double y = 0; y <= 6.28; y += 0.2) {
                double finalY = y;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double x = (radius - 0.14 * finalY) * Math.cos(finalY);
                        double z = (radius - 0.14 * finalY) * Math.sin(finalY);
                        ballPhysics.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                (float) (loc.getX() + x), (float) (loc.getY() + 0.2),
                                (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                        Location loc1 = ballTexture.getLocation();
                        loc1.setYaw((float) finalY * 20);
                        loc1.setY(loc1.subtract(0, 0.1 * finalY, 0).getY());
                        ballTexture.teleport(loc1);
                    }
                }.runTaskLater(VolleyBall.getInstance(), (long) y);
            }
            for (double y = 0; y <= 6.28; y += 0.2) {
                double finalY = y;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double x = (radius - 0.14 * finalY) * Math.cos(finalY + 3.14159);
                        double z = (radius - 0.14 * finalY) * Math.sin(finalY + 3.14159);
                        ballPhysics.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                (float) (loc.getX() + x), (float) (loc.getY() + 0.2),
                                (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                    }
                }.runTaskLater(VolleyBall.getInstance(), (long) y);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    ballPhysics.remove();
                    ballTexture.remove();
                }
            }.runTaskLater(VolleyBall.getInstance(), (long) 6.28);
            
            ballPhysics.getWorld().playSound(ballPhysics.getLocation(), Sound.BLOCK_SAND_PLACE, 2, 1);
            ballPhysics.getWorld().playSound(ballPhysics.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 2, 1);
        } else {
            ballPhysics.remove();
            ballTexture.remove();
        }
    }
}
