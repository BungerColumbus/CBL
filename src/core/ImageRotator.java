package core;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageRotator {

    public BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Create a new image to hold the rotated version
        BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        // Set the rotation point to the center of the image
        AffineTransform transform = 
            AffineTransform.getRotateInstance(Math.toRadians(degrees), width / 2, height / 2);
        
        // Apply the transformation
        g2d.setTransform(transform);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}
