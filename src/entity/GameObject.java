package entity;

import java.awt.Rectangle;
import main.GamePanel;

public class GameObject {
    
    GamePanel gp;

    public double worldX;
    public double worldY;
    public int speed;

    protected int animationIndex = 0;

    protected int[] frameTick = new int[3];

    public int screenX;
    public int screenY;

    public void setInitialPosition(double defaultX, double defaultY, int entitySpeed) {
        worldX = defaultX;
        worldY = defaultY;
        speed = entitySpeed;
    }
    
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public void updateAnimation(int frame, int length, int animationSpeed) {
        if (animationIndex < frame || animationIndex > length) {
            animationIndex = frame;
        }
        frameTick[0]++;
        if (frameTick[0] >= animationSpeed) {
            frameTick[0] = 0;
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
