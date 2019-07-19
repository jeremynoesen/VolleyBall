package me.Jeremaster101.Volleyball;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.UUID;

public class BallCreation {

    /**
     * @param url link to the player skin to get the skull from
     * @return player head with the selected skin
     */
    public static ItemStack getSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        if (url.isEmpty())
            return head;
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
        return head;
    }

    /**
     * creates the volleyball entity
     *
     * @param p player to launch the ball
     */
    public void launchVolleyball(Player p) {
        Slime s = p.getLocation().getWorld()
                .spawn(p.getEyeLocation().add(p.getLocation().getDirection()).subtract(0, 0.25, 0), Slime.class);
        s.setSize(1);
        Location loc = s.getLocation();
        loc.setYaw(0);
        loc.setPitch(0);
        s.teleport(loc);

        ArmorStand stand = s.getWorld().spawn(s.getLocation().subtract(0, 1.5, 0), ArmorStand.class);
        stand.setVisible(false);
        stand.setHelmet(getSkull("http://textures.minecraft.net/texture/9b2513c8d08c60ad3785d3a9a651b7329c5f26937aca2fc8dfaf3441c9bd9da2"));
        stand.setGravity(false);
        stand.setCustomName("BALLSTAND");
        stand.setCustomNameVisible(false);
        stand.setSilent(true);

        s.setCollidable(true);
        s.setCustomName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL");
        s.setVelocity(p.getLocation().getDirection().multiply(0.1).add(new Vector(0, 1, 0)));
        s.getWorld().playSound(s.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, 0);
        s.setCustomNameVisible(false);
        s.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1, true, false));
        s.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 255, true, false));
        s.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 255, true, false));
        s.setSilent(true);

        new BukkitRunnable() {
            boolean end = false;

            @Override
            public void run() {
                s.setFallDistance(0);
                s.getWorld().spawnParticle(Particle.END_ROD, s.getLocation(), 0, 0, 0, 0, 1);
                s.setTarget(null);

                if (s.isDead()) stand.remove();
                stand.setFallDistance(0);
                if (!end) {
                    Location loc = s.getLocation();
                    loc.setPitch(0);
                    loc.setYaw(0);
                    s.teleport(loc);
                    stand.teleport(s.getLocation().subtract(0, 1.5, 0));
                }

                if (s.isOnGround() || s.getLocation().getBlock().getType() != Material.AIR) {

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (s.isOnGround() || s.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {
                                end = true;
                                this.cancel();
                            }
                        }
                    }.runTaskLater(Volleyball.plugin, 5);
                }

                if (end) {
                    double radius = 1;
                    Location loc = s.getLocation();
                    for (double y = 0; y <= 6.28; y += 0.2) {
                        double finalY = y;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                double x = (radius - 0.14 * finalY) * Math.cos(finalY);
                                double z = (radius - 0.14 * finalY) * Math.sin(finalY);
                                s.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                        (float) (loc.getX() + x), (float) (loc.getY() + 0.2),
                                        (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                                Location loc1 = stand.getLocation();
                                loc1.setYaw((float) finalY * 20);
                                loc1.setY(loc1.subtract(0, 0.1 * finalY, 0).getY());
                                stand.teleport(loc1);
                            }
                        }.runTaskLater(Volleyball.plugin, (long) y);
                    }
                    for (double y = 0; y <= 6.28; y += 0.2) {
                        double finalY = y;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                double x = (radius - 0.14 * finalY) * Math.cos(finalY + 3.14159);
                                double z = (radius - 0.14 * finalY) * Math.sin(finalY + 3.14159);
                                s.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                        (float) (loc.getX() + x), (float) (loc.getY() + 0.2),
                                        (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                            }
                        }.runTaskLater(Volleyball.plugin, (long) y);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            s.remove();
                            stand.remove();
                        }
                    }.runTaskLater(Volleyball.plugin, (long) 6.28);

                    s.getWorld().playSound(s.getLocation(), Sound.BLOCK_SAND_PLACE, 2, 1);
                    s.getWorld().playSound(s.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 2, 1);
                    this.cancel();
                }
            }
        }.runTaskTimer(Volleyball.plugin, 0, 1);
    }
}
