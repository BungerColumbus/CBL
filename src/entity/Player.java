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

    private int animationSpeed;
    public Vector2D vector2d = new Vector2D(0, 0);

    public int life = 2;
    public int maxLife = 3;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(8, 16, 32, 26);

        setInitialPosition(696, 696, 4);
        getImages();
    }

    public void getImages() {

        try {

            image[0] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime0.png"));
            image[1] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime1.png"));
            image[2] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime2.png"));
            image[3] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime3.png"));

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

        collisionOn = false;
        gp.collisionCheck.checkTile(this);

        if (!collisionOn) {
            vector2d.normalize();
            vector2d.multiply(speed);
            worldX += vector2d.getX();
            worldY += vector2d.getY();
        }
    }

    public void draw(Graphics2D g2) {

        if (keyH.mousePosition().getX() - gp.location.getX() < gp.screenWidth / 2) {
            updateAnimation(2, 4, animationSpeed);
        } else if (keyH.mousePosition().getX() - gp.location.getX() > gp.screenWidth / 2) {
            updateAnimation(0, 2, animationSpeed);
        }
        
        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gp.tileSize, gp.tileSize, null);

    }
}
