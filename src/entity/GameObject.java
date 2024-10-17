package entity;

import java.awt.Rectangle;
import main.GamePanel;
public class GameObject {
    
    GamePanel gp;

    public double worldX;
    public double worldY;
    public int speed;

    protected int animationTick; 
    protected int animationIndex = 0;

    public int screenX;
    public int screenY;

    public void setInitialPosition(double defaultX, double defaultY, int entity_speed) {
        worldX = defaultX;
        worldY = defaultY;
        speed = entity_speed;
    }
    
    public Rectangle solidArea;
    public boolean collisionOn = false;

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
