package entity;

import java.awt.Rectangle;
import main.GamePanel;

public class GameObject {
    
    GamePanel gp;

    public double worldX;
    public double worldY;

    protected int animationTick; 
    protected int animationIndex = 0;

    public int screenX;
    public int screenY;

    public void setInitialPosition(int defaultX, int defaultY) {
        worldX = defaultX;
        worldY = defaultY;
    }
    
    public Rectangle solidArea;
    protected boolean collisonOn = false;

    public void updateAnimation(int frame, int length, int animationSpeed) {
        if (animationIndex < frame || animationIndex > length) {
            animationIndex = frame;
        }
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= length) {
                animationIndex = frame;
            }
        }
    }

    public void screenPostionRelativeToPlayer(Player player) {
        screenX = (int) Math.round(worldX - player.worldX + player.screenX);
        screenY = (int) Math.round(worldY - player.worldY + player.screenY);
    }

}
