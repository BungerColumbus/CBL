package entity;

import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import main.GamePanel;
import main.KeyHandler;

public class Player extends GameObject {
    
    private BufferedImage[] image = new BufferedImage[4];
    GamePanel gp;
    KeyHandler keyH;
    protected int speed = 4;
    private int animationSpeed;
    protected Vector2D vector2d = new Vector2D(0, 0);

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        solidArea = new Rectangle(1, 3, 14, 11);
        setDefaultValues();
        getImages();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
    }

    public void getImages() {

        try {

            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime0.png"));
            image[1] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime1.png"));
            image[2] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime2.png"));
            image[3] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        int deltaX = 0;
        int deltaY = 0;

        if (keyH.upPressed) {
            deltaY--;
        }
        if (keyH.downPressed) {
            deltaY++;
        }
        if (keyH.leftPressed) {
            deltaX--;
        }
        if (keyH.rightPressed) {
            deltaX++;
        }

        vector2d = new Vector2D(deltaX, deltaY);
        if (vector2d.length() > 0) {
            animationSpeed = 15;
        } else {
            animationSpeed = 30;
        }
        vector2d.normalize();
        vector2d.multiply(speed);
        x += vector2d.getX();
        y += vector2d.getY();

        //System.out.println(keyH.mousePosition().getX() - gp.location.getX() - x - gp.tileSize/2);
    }

    public void draw(Graphics2D g2) {

        if (keyH.mousePosition().getX() - gp.location.getX() - x - gp.tileSize / 2 < 0) {
            updateAnimation(2, 4, animationSpeed);
        } else if (keyH.mousePosition().getX() - gp.location.getX() - x - gp.tileSize / 2 > 0) {
            updateAnimation(0, 2, animationSpeed);
        }
        
        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(x), (int) Math.round(y),
                     gp.tileSize, gp.tileSize, null);

    }
}
