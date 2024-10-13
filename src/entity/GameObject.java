package entity;

import java.awt.Rectangle;
import main.GamePanel;

public class GameObject {
    
    GamePanel gp;

    public double x;
    public double y;
    public int speed;

    protected int animationTick; 
    protected int animationIndex = 0;

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

}
