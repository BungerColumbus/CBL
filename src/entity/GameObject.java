package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class GameObject {
    
    GamePanel gp;

    public double worldX;
    public double worldY;
    public int speed;

    protected int animationIndex = 0;

    protected BufferedImage[] image;
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

    // Used in order to rotate an image around its center
    public BufferedImage rotateImage(BufferedImage originalImage, double degrees, Graphics2D g2) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Create a new image to hold the rotated version
        BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());
        g2 = rotatedImage.createGraphics();

        // Set the rotation point to the center of the image
        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(degrees), width / 2, height / 2);
        
        // Apply the transformation
        g2.setTransform(transform);
        g2.drawImage(originalImage, 0, 0, null);
        g2.dispose();

        return rotatedImage;
    }


    public void screenPostionRelativeToPlayer(Player player) {
        screenX = (int) Math.round(worldX - player.worldX + player.screenX);
        screenY = (int) Math.round(worldY - player.worldY + player.screenY);
    }

}
