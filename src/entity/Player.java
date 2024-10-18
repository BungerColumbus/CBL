package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends GameObject {
    
    private BufferedImage[] image = new BufferedImage[4];
    GamePanel gp;
    KeyHandler keyH;

    private int animationSpeed;
    public Vector2D vector2d = new Vector2D(0, 0);
    public Vector2D hitBoxLocationVector2d = new Vector2D(0, 0);
    public OnTriggerCircleCollision meleeHitBox;

    public int life = 2;
    public int maxLife = 3;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(8, 16, 32, 26);
        meleeHitBox = new OnTriggerCircleCollision(gp, 30, hitBoxLocationVector2d);

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

    public void hitBox() {
        hitBoxLocationVector2d = new Vector2D(keyH.mousePosition().getX() - gp.screenWidth/2 - gp.location.getX(), keyH.mousePosition().getY() - gp.screenHeight/2 - gp.location.getY());
        hitBoxLocationVector2d.normalize();
        hitBoxLocationVector2d.multiply(50);
        if(keyH.clickedLeftButton) {
            meleeHitBox = new OnTriggerCircleCollision(gp, 30, hitBoxLocationVector2d);
            meleeHitBox.active = true;
        } else if (!keyH.clickedLeftButton) {
            meleeHitBox.active = false;
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
        hitBox();
        if (!collisionOn) {
            vector2d.normalize();
            vector2d.multiply(speed);
            worldX += vector2d.getX();
            worldY += vector2d.getY();
        }
    }

    public void draw(Graphics2D g2) {

        if (keyH.mousePosition().getX() - gp.location.getX() < gp.screenWidth2 / 2) {
            updateAnimation(2, 4, animationSpeed);
        } else if (keyH.mousePosition().getX() - gp.location.getX() > gp.screenWidth2 / 2) {
            updateAnimation(0, 2, animationSpeed);
        }
        
        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gp.tileSize, gp.tileSize, null);
        if(meleeHitBox.active)
            g2.fillOval((int) Math.round(screenX + hitBoxLocationVector2d.getX() + gp.tileSize/4), (int) Math.round(screenY + hitBoxLocationVector2d.getY() + gp.tileSize/4),
                     30, 30);

    }
}
