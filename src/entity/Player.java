package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    private Vector2D hitBoxDirectionVector2d = new Vector2D(0, 0);
    public Vector2D hitBoxLocationVector2d = new Vector2D(0, 0);
    public OnTriggerCircleCollision meleeHitBox;
    public OnTriggerCircleCollision playerHitBox;
    private boolean canTakeDamage = true;
    private boolean canAttack = true;

    public int life = 3;
    public int maxLife = 3;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(8, 16, 32, 26);
        meleeHitBox = new OnTriggerCircleCollision(gp, 30, new Vector2D(worldX + hitBoxDirectionVector2d.getX(), worldY + hitBoxDirectionVector2d.getY()));
        playerHitBox = new OnTriggerCircleCollision(gp, 5, new Vector2D(worldX, worldY));
        System.out.println(playerHitBox.active);
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

    public void meleeHitBox() {
        hitBoxDirectionVector2d = new Vector2D(keyH.mousePosition().getX() - gp.screenWidth / 2
                                - gp.location.getX() - gp.tileSize / 2, keyH.mousePosition().getY()
                                - gp.screenHeight / 2 - gp.location.getY() - gp.tileSize / 2); 
        hitBoxDirectionVector2d.normalize();
        hitBoxDirectionVector2d.multiply(50);
        hitBoxLocationVector2d = new Vector2D(worldX + hitBoxDirectionVector2d.getX(),
                                 worldY + hitBoxDirectionVector2d.getY());
        if (keyH.clickedLeftButton && canAttack) {
            System.out.println("attacked");
            canAttack = false;
            meleeHitBox = new OnTriggerCircleCollision(gp, 20,
                          new Vector2D(worldX + hitBoxDirectionVector2d.getX(),
                                       worldY + hitBoxDirectionVector2d.getY()));
            meleeHitBox.active = true;
            System.out.println(worldX + hitBoxDirectionVector2d.getX() + " " + (worldY + hitBoxDirectionVector2d.getY()));
        } else if (!canAttack) {
            meleeHitBox.active = false;
        }
        coolDownAttack(20);
    }

    public void coolDownAttack(int frames) {
        
        if (frameTick[2] > frames) {
            canAttack = true;
            frameTick[2] = 0;
        }
        else if (!canAttack) {
            frameTick[2]++;
        }
    }

    public void damagePlayer() {
        if (canTakeDamage) {
            canTakeDamage = false;
            life--;
        }
    }

    public void coolDownHP(int frames) {
        
        if (frameTick[1] > frames) {
            canTakeDamage = true;
            frameTick[1] = 0;
        }
        else if (!canTakeDamage) {            
            frameTick[1]++;
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
        meleeHitBox();
        coolDownHP(60);
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
        if (frameTick[1] % 10 < 2 || frameTick[1] == 0) {
            g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gp.tileSize, gp.tileSize, null);
        }
        if (meleeHitBox.active) {
            g2.fillOval((int) Math.round(
                screenX + hitBoxDirectionVector2d.getX() - gp.tileSize / 4), 
                (int) Math.round(screenY + hitBoxDirectionVector2d.getY() - gp.tileSize / 4),
                30, 30);
        }

    }
}
