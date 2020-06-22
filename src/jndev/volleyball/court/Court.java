package jndev.volleyball.court;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import jndev.volleyball.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Court {
    
    private static HashMap<String, Court> courts = new HashMap<>();
    
    private String court;
    
    private boolean enabled;
    
    private boolean animations;
    
    private String texture;
    
    private World world;
    
    private double speed;
    
    private double[][] bounds;
    
    private double[][] net;
    
    public Court(String court) {
        this.court = court;
        enabled = false;
        animations = true;
        speed = 1;
        texture = "http://textures.minecraft.net/texture/9b2513c8d08c60ad3785d3a9a651b7329c5f26937aca2fc8dfaf3441c9bd9da2";
        world = null;
        bounds = new double[2][3];
        net = new double[2][3];
        courts.put(court, this);
    }
    
    public static Court getCourt(String name) {
        if (courts.containsKey(name))
            return courts.get(name);
        return null;
    }
    
    public static HashMap<String, Court> getCourts() {
        return courts;
    }
    
    public String getName() {
        return court;
    }
    
    public void setName(String name) {
        courts.remove(court);
        court = name;
        courts.put(name, this);
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean hasAnimations() {
        return animations;
    }
    
    public void setAnimations(boolean animations) {
        this.animations = animations;
    }
    
    public String getTexture() {
        return texture;
    }
    
    public void setTexture(String texture) {
        this.texture = texture;
    }
    
    public World getWorld() {
        return world;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public double[][] getBounds() {
        return bounds;
    }
    
    public void setBounds(double[][] bounds) {
        this.bounds = bounds;
    }
    
    public void setBounds(Player player) {
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
            
            
            player.sendMessage(Message.SUCCESS_SET_COURT_BOUNDS.replace("$COURT$", court));
            
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
        }
    }
    
    public double[][] getNet() {
        return net;
    }
    
    public void setNet(double[][] net) {
        this.net = net;
    }
    
    public void setNet(Player player) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        
        com.sk89q.worldedit.regions.Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
            return;
        }
        
        if (selection != null) {
            net[0][0] = selection.getMinimumPoint().getX();
            net[0][1] = selection.getMinimumPoint().getY();
            net[0][2] = selection.getMinimumPoint().getZ();
            net[1][0] = selection.getMaximumPoint().getX();
            net[1][1] = selection.getMaximumPoint().getY();
            net[1][2] = selection.getMaximumPoint().getZ();
            
            
            player.sendMessage(Message.SUCCESS_SET_NET_BOUNDS.replace("$COURT$", court));
            
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
        }
    }
    
    public void remove() {
        courts.remove(court);
    }
    
    @Override
    public String toString() {
        return Message.COURT_INFO
                .replace("$NAME$", court)
                .replace("$ENABLED$", Boolean.toString(isEnabled()))
                .replace("$ANIMATIONS$", Boolean.toString(hasAnimations()))
                .replace("$SPEED$", Double.toString(getSpeed()))
                .replace("$TEXTURE$", getTexture())
                .replace("$BOUNDS$",
                        "(" + bounds[0][0] + "," + bounds[0][1] + "," + bounds[0][2] + ") to (" +
                                bounds[1][0] + "," + bounds[1][1] + "," + bounds[1][2] + ")")
                .replace("$NET$",
                        "(" + net[0][0] + "," + net[0][1] + "," + net[0][2] + ") to (" +
                                net[1][0] + "," + net[1][1] + "," + net[1][2] + ")")
                .replace("$WORLD$", world.getName());
        
    }
}
