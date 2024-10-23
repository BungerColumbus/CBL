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

    // The direction of the movement (obtained from the WASD keys)
    public Vector2D vector2d = new Vector2D(0, 0);
    // The direction of the melee attack hitBox
    private Vector2D hitBoxDirectionVector2d = new Vector2D(0, 0);
    // The position of the melee attack hitBox
    public Vector2D hitBoxLocationVector2d = new Vector2D(0, 0);
    // The circle trigger collision for the player attack
    public OnTriggerCircleCollision meleeHitBox;
    // The circle trigger collision for the player
    public OnTriggerCircleCollision playerHitBox;
    
    // These 2 booleans give the player the permission to take/deal dmg  
    private boolean canTakeDamage = true;
    private boolean canAttack = true;

    public int life = 3;
    public int maxLife = 3;

    //The player constructor which is being run in the gamepanel.
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // In order to make the players screen position right in the center
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // The collision box of the player
        solidArea = new Rectangle(8, 16, 32, 26);
        // The circle trigger colliders. We set a radius for the circle, and a position vector
        meleeHitBox = new OnTriggerCircleCollision(gp, 30, new Vector2D(worldX + hitBoxDirectionVector2d.getX(), worldY + hitBoxDirectionVector2d.getY()));
        playerHitBox = new OnTriggerCircleCollision(gp, 5, new Vector2D(worldX, worldY));
        // Initial position of the player
        setInitialPosition(696, 696, 4);
        getImages();
    }

    public void getImages() {

        // Getting the images from the res folder used in the animations by the player using try and catch
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
        // Setting up the direction vector to look in the direction of the mouse
        hitBoxDirectionVector2d = new Vector2D(keyH.mousePosition().getX() - gp.screenWidth/2 - gp.location.getX() - gp.tileSize/2, keyH.mousePosition().getY() - gp.screenHeight/2 - gp.location.getY() - gp.tileSize/2); 
        
        // Setting up the specific distance at which the player can attack from
        hitBoxDirectionVector2d.normalize();
        hitBoxDirectionVector2d.multiply(50);

        // Setting up the location of the melee hitBox by adding the direction vector to the world location of the player
        hitBoxLocationVector2d = new Vector2D(worldX + hitBoxDirectionVector2d.getX(), worldY + hitBoxDirectionVector2d.getY());

        /** On mouse click canAttack instantly becomes false (so that the whole sequence of events happens only once) 
         * after that the melee hitBox is called once and it's activated only to be deactivated right afterwards.
         * The Cooldown begins and the player can attack again after x amoutn of frames.
        */
        if(keyH.clickedLeftButton && canAttack) {
            System.out.println("attacked");
            canAttack = false;
            meleeHitBox = new OnTriggerCircleCollision(gp, 20, new Vector2D(worldX + hitBoxDirectionVector2d.getX(), worldY + hitBoxDirectionVector2d.getY()));
            meleeHitBox.active = true;
            System.out.println(worldX + hitBoxDirectionVector2d.getX() + " " + (worldY + hitBoxDirectionVector2d.getY()));
        } else if (!canAttack) {
            meleeHitBox.active = false;
        }
        CoolDownAttack(20);
    }

    /** The cooldown for the attack. It uses a different type of frameTicks because if it used the same
    * as the other cooldowns they would overlap and it wouldn't work properly.
    */
    public void CoolDownAttack(int frames) {
        
        if(frameTick[2] > frames) {
            canAttack = true;
            frameTick[2] = 0;
        }
        else if(!canAttack) {
            frameTick[2]++;
        }
    }
    
    // If the player can take damage. It takes damage and sets the variable to false
    public void damagePlayer() {
        if (canTakeDamage) {
            canTakeDamage = false;
            life--;
        }
    }

    // CoolDown for the canTakeDamage boolean
    public void CoolDownHP(int frames) {
        
        if(frameTick[1] > frames) {
            canTakeDamage = true;
            frameTick[1] = 0;
        }
        else if(!canTakeDamage) {            
            frameTick[1]++;
        }
    }

    // This cooldown is called 60 times per second
    public void update() {
        // These ints are added to the vector
        int deltaX = 0;
        int deltaY = 0;

        // These are the inputs which change deltaX and deltaY accordingly
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

        // Depending on the length of the vector we called down the animations is either faster or slower
        vector2d = new Vector2D(deltaX, deltaY);
        if (vector2d.length() > 0) {
            animationSpeed = 15;
        } else {
            animationSpeed = 30;
        }

        // Collision parameter, check CollisonCheck class
        collisionOn = false;
        gp.collisionCheck.checkTile(this);
        // The melee hitBox
        meleeHitBox();
        // The cooldown for the hp (1 second length)
        CoolDownHP(60);

        /** Depedning on the collisionOn boolean, the vector gets normalized,
        * multiplied by speed and added to the player's world position.
        * This way the player moves at the same speed no matter the direction
        */
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
        if(frameTick[1]%10 < 2 || frameTick[1] == 0)
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gp.tileSize, gp.tileSize, null);
        if(meleeHitBox.active)
            g2.fillOval((int) Math.round(screenX + hitBoxDirectionVector2d.getX() - gp.tileSize/4), (int) Math.round(screenY + hitBoxDirectionVector2d.getY() - gp.tileSize/4),
                     30, 30);

    }
}
