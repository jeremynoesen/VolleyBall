package me.Jeremaster101.Volleyball.Court;

import me.Jeremaster101.Volleyball.Message;
import me.Jeremaster101.Volleyball.Volleyball;
import org.bukkit.entity.Player;

public class CourtDefaults {
    
    private Player player;
    
    CourtDefaults(Player player) {
        this.player = player;
    }
    
    /**
     * @return ball speed type double
     */
    public double getSpeed() {
        return Volleyball.getInstance().getConfig().getDouble("default-speed");
    }
    
    /**
     * set how fast the ball gets launched on hit
     *
     * @param speed double speed
     */
    public void setSpeed(double speed) {
        Volleyball.getInstance().getConfig().set("default-speed", speed);
        Volleyball.getInstance().saveConfig();
        player.sendMessage(Message.SUCCESS_SET_COURT_SPEED.replace("$COURT$", "defaults").replace("$SPEED$", Double.toString(speed)));
    }
    
    /**
     * @return url of head texture
     */
    public String getTexture() {
        return Volleyball.getInstance().getConfig().getString("default-texture");
    }
    
    /**
     * @param url url of head texture
     */
    public void setTexture(String url) {
        Volleyball.getInstance().getConfig().set("default-texture", url);
        Volleyball.getInstance().saveConfig();
        player.sendMessage(Message.SUCCESS_SET_COURT_TEXTURE.replace("$COURT$", "defaults"));
    }
    
    public boolean getAnimations() {
        return Volleyball.getInstance().getConfig().getBoolean("default-animations");
    }
    
    public void setAnimations(boolean animations) {
        Volleyball.getInstance().getConfig().set("default-animations", animations);
        Volleyball.getInstance().saveConfig();
        player.sendMessage(Message.SUCCESS_SET_COURT_ANIMATIONS.replace("$COURT$", "defaults").replace("$BOOL$", Boolean.toString(animations)));
    }
    
}
