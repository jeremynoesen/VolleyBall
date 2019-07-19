package me.Jeremaster101.Volleyball;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.UUID;

public class Volleyball extends JavaPlugin implements Listener {

    Volleyball plugin;

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

    public void onEnable() {
        plugin = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(plugin, plugin);
        getCommand("volleyball").setExecutor(plugin);
    }

    public void onDisable() {
        plugin = null;
    }

    private void launchVolleyball(Player p) {
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
                    }.runTaskLater(plugin, 5);
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
                        }.runTaskLater(plugin, (long) y);
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
                        }.runTaskLater(plugin, (long) y);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            s.remove();
                            stand.remove();
                        }
                    }.runTaskLater(plugin, (long) 6.28);

                    s.getWorld().playSound(s.getLocation(), Sound.BLOCK_SAND_PLACE, 2, 1);
                    s.getWorld().playSound(s.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 2, 1);
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (label.equalsIgnoreCase("volleyball") || label.equalsIgnoreCase("vb"))
                if (p.getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.LAPIS_BLOCK) ||
                        p.getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.LAPIS_BLOCK)) {
                    for (Entity all : p.getNearbyEntities(20, 30, 20)) {
                        if (all.getCustomName() != null && all.getCustomName().equals(ChatColor.DARK_GREEN +
                                "" + ChatColor.BOLD + "BALL")) {
                            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                                    ChatColor.GOLD + "" + ChatColor.BOLD + "" +
                                    ChatColor.MAGIC + "i" + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                                    ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" + ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                                    ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" + ChatColor.WHITE + "" + ChatColor.BOLD +
                                    " " +
                                    "There is" +
                                    " already a ball out! "
                                    + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                                    ChatColor.GOLD + "" + ChatColor.BOLD + "" +
                                    ChatColor.MAGIC + "i" + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                                    ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" + ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                                    ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i");
                            return true;
                        }
                    }
                    launchVolleyball(p);
                    Location loc = p.getLocation();
                    double radius = 0.5;
                    for (double y = 0; y <= 10; y += 0.2) {
                        double finalY = y;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                double x = (radius + 0.05 * finalY) * Math.cos(finalY);
                                double z = (radius + 0.05 * finalY) * Math.sin(finalY);
                                p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,
                                        (float) (loc.getX() + x), (float) (loc.getY() + finalY * 0.1),
                                        (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                            }
                        }.runTaskLater(plugin, (long) y);
                    }
                } else p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                        ChatColor.GOLD + "" + ChatColor.BOLD + "" +
                        ChatColor.MAGIC + "i" + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                        ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" + ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                        ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" + ChatColor.WHITE + "" + ChatColor.BOLD + " You must be on " +
                        "the court to serve! "
                        + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                        ChatColor.GOLD + "" + ChatColor.BOLD + "" +
                        ChatColor.MAGIC + "i" + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                        ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" + ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i" +
                        ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "i");
        }

        return true;
    }

    @EventHandler
    public void onBallHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity().getCustomName() != null && e.getEntity()
                .getCustomName().equals(ChatColor.DARK_GREEN +

                        "" + ChatColor.BOLD + "BALL")) {
            e.setCancelled(true);
            Entity s = e.getEntity();
            s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
            s.setVelocity(e.getDamager().getLocation().getDirection().multiply(0.9).add(new Vector(0, 0.6, 0))
                    .add(e.getDamager().getVelocity().setY(e.getDamager().getVelocity().multiply(2).getY())));
        }

        if (e.getDamager() instanceof Player && e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equals("BALLSTAND")) {
            for (Entity s : e.getEntity().getNearbyEntities(1, 1, 1)) {
                if (s.getCustomName() != null && s.getCustomName().equals(ChatColor.DARK_GREEN +
                        "" + ChatColor.BOLD + "BALL")) {
                    s.getWorld().playSound(s.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2, 0);
                    s.setVelocity(e.getDamager().getLocation().getDirection().multiply(0.9).add(new Vector(0, 0.6, 0))
                            .add(e.getDamager().getVelocity().setY(e.getDamager().getVelocity().multiply(2).getY())));
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
            if (p.getLocation().getBlock().getRelative(0, -2, 0).getType().equals(Material.LAPIS_BLOCK) ||
                    p.getLocation().getBlock().getRelative(0, -3, 0).getType().equals(Material.LAPIS_BLOCK))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        Entity s = e.getEntity();
        if (s.getCustomName() != null && s.getCustomName().equals(ChatColor.DARK_GREEN +
                "" + ChatColor.BOLD + "BALL") && e.getTarget() instanceof Player) e.setCancelled(true);
    }


}
