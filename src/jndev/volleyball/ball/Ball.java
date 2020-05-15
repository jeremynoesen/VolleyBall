package jndev.volleyball.ball;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import jndev.volleyball.court.Court;
import jndev.volleyball.court.CourtHandler;
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
    
    private Villager stand;
    private Slime slime;
    private boolean end = false;
    private int stop = 0;
    private Player player;
    public boolean volleyed = false;
    public int volleys = 0;
    
    /**
     * Creates a new ball
     *
     * @param player player to create the ball at
     */
    public Ball(Player player) {
        this.player = player;
        
        CourtHandler ch = new CourtHandler();
        
        Court court = Court.getCourt(player, ch.getCourt(player));
        
        slime = player.getLocation().getWorld()
                .spawn(player.getEyeLocation().add(player.getLocation().getDirection()).subtract(0, 0.25, 0), Slime.class);
        slime.setSize(1);
        Location loc = slime.getLocation();
        loc.setYaw(0);
        loc.setPitch(0);
        slime.teleport(loc);
        
        stand = slime.getWorld().spawn(slime.getLocation().subtract(0, 1.5, 0), Villager.class);
        setTexture(court.getTexture());
        stand.setGravity(false);
        stand.setCustomName("BALLSTAND");
        stand.setCustomNameVisible(false);
        stand.setSilent(true);
        stand.setAI(false);
        stand.setCollidable(false);
        stand.setSilent(true);
        stand.setInvulnerable(false);
        stand.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1000000, true, true));
        
        slime.setCollidable(false);
        slime.setCustomName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL");
        slime.setVelocity(player.getLocation().getDirection().multiply(0.1).add(new Vector(0, 1, 0)));
        slime.getWorld().playSound(slime.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, 0);
        slime.setCustomNameVisible(false);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1, true, false));
        slime.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 255, true, false));
        slime.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 255, true, false));
        slime.setSilent(true);
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
        stand.getEquipment().setHelmet(head);
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
    @SuppressWarnings("deprecation")
    public void serve(Court court) {
        boolean animated = court.getAnimations();
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
        
        CourtHandler ch = new CourtHandler();
        
        new BukkitRunnable() {
            @Override
            public void run() {
                
                if (ch.isAboveNet(slime.getLocation(), court) && !volleyed) {
                    volleyed = true;
                    volleys++;
                    for (Player players : ch.getPlayersOnCourt(court)) {
                        players.sendTitle("", ChatColor.WHITE + Integer.toString(volleys), 0, 10, 10);
                    }
                } else if (!ch.isAboveNet(slime.getLocation(), court)) {
                    volleyed = false;
                }
                
                slime.setFallDistance(0);
                if (animated) slime.getWorld().spawnParticle(Particle.END_ROD, slime.getLocation(), 0, 0, 0, 0, 1);
                slime.setTarget(null);
                
                if (slime.isDead()) {
                    stand.remove();
                    this.cancel();
                }
                stand.setFallDistance(0);
                if (!end) {
                    Location loc = slime.getLocation();
                    loc.setPitch(0);
                    loc.setYaw(0);
                    slime.teleport(loc);
                    stand.teleport(slime.getLocation().subtract(0, 1.5, 0));
                }
                
                if (slime.isOnGround() || slime.getLocation().getBlock().getType() != Material.AIR) {
                    
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (slime.isOnGround() || slime.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {
                                if (stop == 0) {
                                    remove(court);
                                }
                                stop++;
                            }
                        }
                    }.runTaskLater(VolleyBall.getInstance(), 0);
                }
            }
        }.runTaskTimer(VolleyBall.getInstance(), 0, 1);
    }
    
    /**
     * removes the volleyball with or without animations
     */
    public void remove(Court court) {
        boolean animated = court.getAnimations();
        if (animated) {
            end = true;
            double radius = 1;
            Location loc = slime.getLocation();
            for (double y = 0; y <= 6.28; y += 0.2) {
                double finalY = y;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double x = (radius - 0.14 * finalY) * Math.cos(finalY);
                        double z = (radius - 0.14 * finalY) * Math.sin(finalY);
                        slime.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                (float) (loc.getX() + x), (float) (loc.getY() + 0.2),
                                (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                        Location loc1 = stand.getLocation();
                        loc1.setYaw((float) finalY * 20);
                        loc1.setY(loc1.subtract(0, 0.1 * finalY, 0).getY());
                        stand.teleport(loc1);
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
                        slime.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                (float) (loc.getX() + x), (float) (loc.getY() + 0.2),
                                (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                    }
                }.runTaskLater(VolleyBall.getInstance(), (long) y);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    slime.remove();
                    stand.remove();
                }
            }.runTaskLater(VolleyBall.getInstance(), (long) 6.28);
            
            slime.getWorld().playSound(slime.getLocation(), Sound.BLOCK_SAND_PLACE, 2, 1);
            slime.getWorld().playSound(slime.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 2, 1);
        } else {
            slime.remove();
            stand.remove();
        }
    }
}
