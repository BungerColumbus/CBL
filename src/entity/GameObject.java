package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class GameObject {
    
    GamePanel gp;

    // The world position
    public double worldX;
    public double worldY;
    
    public int speed;

    // Used for animations and images
    protected int animationIndex = 0;
    protected int animationSpeed;
    protected BufferedImage[] image;
    
    // Used to keep track of how many frames have passed
    protected int[] frameTick = new int[4];

    // Screen position
    public int screenX;
    public int screenY;

    // Setting the initial position and speed of the GameObject
    public void setInitialPosition(double defaultX, double defaultY, int entitySpeed) {
        worldX = defaultX;
        worldY = defaultY;
        speed = entitySpeed;
    }
    
    // Solid area around the GameObject (used for player)
    public Rectangle solidArea;

    // The 2 types of collision possible for the player, used in order to make collision smoother
    public boolean collisionVertical = false;
    public boolean collisionHorizontal = false;

    // Updates the animation of a GameObject  by using the frameTick
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
        AffineTransform transform = 
            AffineTransform.getRotateInstance(Math.toRadians(degrees), width / 2, height / 2);
        
        // Apply the transformation
        g2.setTransform(transform);
        g2.drawImage(originalImage, 0, 0, null);
        g2.dispose();

        return rotatedImage;
    }

    // In order to make the other objects (enemies) appear right on camera/screen
    public void screenPostionRelativeToPlayer(Player player) {
        screenX = (int) Math.round(worldX - player.worldX + player.screenX);
        screenY = (int) Math.round(worldY - player.worldY + player.screenY);
    }

}
