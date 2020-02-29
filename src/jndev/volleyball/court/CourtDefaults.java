package jndev.volleyball.court;

import jndev.volleyball.Message;
import jndev.volleyball.VolleyBall;
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
        return VolleyBall.getInstance().getConfig().getDouble("default-speed");
    }
    
    /**
     * set how fast the ball gets launched on hit
     *
     * @param speed double speed
     */
    public void setSpeed(double speed) {
        VolleyBall.getInstance().getConfig().set("default-speed", speed);
        VolleyBall.getInstance().saveConfig();
        player.sendMessage(Message.SUCCESS_SET_COURT_SPEED.replace("$COURT$", "defaults").replace("$SPEED$", Double.toString(speed)));
    }
    
    /**
     * @return url of head texture
     */
    public String getTexture() {
        return VolleyBall.getInstance().getConfig().getString("default-texture");
    }
    
    /**
     * @param url url of head texture
     */
    public void setTexture(String url) {
        VolleyBall.getInstance().getConfig().set("default-texture", url);
        VolleyBall.getInstance().saveConfig();
        player.sendMessage(Message.SUCCESS_SET_COURT_TEXTURE.replace("$COURT$", "defaults"));
    }
    
    public boolean getAnimations() {
        return VolleyBall.getInstance().getConfig().getBoolean("default-animations");
    }
    
    public void setAnimations(boolean animations) {
        VolleyBall.getInstance().getConfig().set("default-animations", animations);
        VolleyBall.getInstance().saveConfig();
        player.sendMessage(Message.SUCCESS_SET_COURT_ANIMATIONS.replace("$COURT$", "defaults").replace("$BOOL$", Boolean.toString(animations)));
    }
    
}
