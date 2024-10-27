package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameSettings;
import main.KeyHandler;

public class Player extends GameObject {
    
    private BufferedImage[] image = new BufferedImage[9];
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
    private GameSettings gameSettings = new GameSettings();
    public OnTriggerCircleCollision meleeHitBox;
    // The circle trigger collision for the player
    public OnTriggerCircleCollision playerHitBox;
    
    // These 2 booleans give the player the permission to take/deal dmg  
    private boolean canTakeDamage = true;
    private boolean canAttack = true;
    private boolean canDash = true;
    private boolean dashing = false;
    private int attackAnimation = 0;

    public int life = 3;
    public int maxLife = 3;

    //The player constructor which is being run in the gamepanel.
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // In order to make the players screen position right in the center
        screenX = gameSettings.getScreenWidth() / 2 - gameSettings.getTileSize() / 2;
        screenY = gameSettings.getScreenHeight() / 2 - gameSettings.getTileSize() / 2;

        // The collision box of the player
        solidArea = new Rectangle(8, 16, 32, 20);
        // The circle trigger colliders. We set a radius for the circle, and a position vector
        meleeHitBox = new OnTriggerCircleCollision(gp, 20, new Vector2D(worldX + hitBoxDirectionVector2d.getX(), worldY + hitBoxDirectionVector2d.getY()));
        meleeHitBox.active = false;
        playerHitBox = new OnTriggerCircleCollision(gp, 15, new Vector2D(worldX, worldY));
        // Initial position of the player
        setInitialPosition(696, 696, 4);
        getImages();
    }

    public void getImages() {
        // Getting the images from the res folder used in the animations by the player using try and catch
        try {
            for (int i = 0; i <= 3; i++) {
                image[i] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime" + i + ".png"));
            }
            for (int i = 4; i <= 6; i++) {
                image[i] = ImageIO.read(getClass()
                .getResourceAsStream("/res/attack/attack" + (i-4) + ".png"));
            }

                image[7] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime_dashing1.png"));
                image[8] = ImageIO.read(getClass()
                    .getResourceAsStream("/res/player/player_slime_dashing0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void meleeHitBox() {
        hitBoxDirectionVector2d = new Vector2D(keyH.mousePosition().getX() - gameSettings.getScreenWidth2() / 2
                                - gp.location.getX(), keyH.mousePosition().getY()
                                - gameSettings.getScreenHeight2() / 2 - gp.location.getY()); 
        hitBoxDirectionVector2d.normalize();
        hitBoxDirectionVector2d.multiply(50);

        // Setting up the location of the melee hitBox by adding the direction vector to the world location of the player
        hitBoxLocationVector2d = new Vector2D(worldX + hitBoxDirectionVector2d.getX(), worldY + hitBoxDirectionVector2d.getY());

        /** On mouse click canAttack instantly becomes false (so that the whole sequence of events happens only once) 
         * after that the melee hitBox is called once and it's activated only to be deactivated right afterwards.
         * The Cooldown begins and the player can attack again after x amoutn of frames.
        */
        if(keyH.clickedLeftButton && canAttack) {
            canAttack = false;
            meleeHitBox = new OnTriggerCircleCollision(gp, 20,
                          new Vector2D(hitBoxLocationVector2d.getX(),
                                       hitBoxLocationVector2d.getY()));
            meleeHitBox.active = true;
        } else if (!canAttack) {
            meleeHitBox.active = false;
        }
        coolDownAttack(20);
    }

    /** The cooldown for the attack. It uses a different type of frameTicks because if it used the same
    * as the other cooldowns they would overlap and it wouldn't work properly.
    */
    public void coolDownAttack(int frames) {
        
        if (frameTick[2] > frames) {
            canAttack = true;
            frameTick[2] = 0;
        } else if (!canAttack) {
            frameTick[2]++;
        }
        if(frameTick[2] == 0 || frameTick[2] >= 15) {
            attackAnimation = 0;
        } else {
            attackAnimation = frameTick[2]/5;
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
    public void coolDownHP(int frames) {
        
        if (frameTick[1] > frames) {
            canTakeDamage = true;
            frameTick[1] = 0;
        } else if (!canTakeDamage) {            
            frameTick[1]++;
        }
    }

    public void coolDownDash(int frames) {
        
        if (frameTick[3] > frames) {
            System.out.println("canDash");
            canDash = true;
            frameTick[3] = 0;
        } else if (!canDash) {            
            frameTick[3]++;
        }
    }

    public void Dash(int initialSpeed, int dashingSpeed, int dashingFrames) {
        if (frameTick[3] > 0 && frameTick[3] < dashingFrames) {
            System.out.println("dashing");
            playerHitBox.active = false;
            dashing = true;
            speed = dashingSpeed;
        } else {
            playerHitBox.active = true;
            dashing = false;
            speed = initialSpeed;
        }
    }

    // This is called 60 times per second
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
        if (keyH.dashed) {
            canDash = false;
        }

        // Depending on the length of the vector we called down the animations is either faster or slower
        if (!dashing) {
            vector2d = new Vector2D(deltaX, deltaY);
        }
        if (vector2d.length() > 0) {
            animationSpeed = 15;
        } else {
            animationSpeed = 30;
        }

        Dash(4, 16, 12);


        // Collision parameters, check CollisonCheck class
        collisionHorizontal = false;
        collisionVertical = false;
        gp.collisionCheck.checkTile(this);
        // The melee hitBox
        meleeHitBox();
        // The cooldown for the hp (1 second length)
        coolDownHP(60);
        // The cooldown for the dash (2 second length)
        coolDownDash(120);
        /** Depending on the collisionOn boolean, the vector gets normalized,
        * multiplied by speed and added to the player's world position.
        * This way the player moves at the same speed no matter the direction.
        */
        
            vector2d.normalize();
            vector2d.multiply(speed);
            if(!collisionHorizontal) {
                worldX += vector2d.getX();
            }

            if(!collisionVertical) {
                worldY += vector2d.getY();
            }
        }

    // Draws the player on the temp screen
    public void draw(Graphics2D g2) {
    // The current image
    BufferedImage currentImage = image[animationIndex];

        // Changes animation if the mouse is either on the left side or right side of the screen
        if (keyH.mousePosition().getX() - gp.location.getX() < gameSettings.getScreenWidth2() / 2) {
            updateAnimation(2, 4, animationSpeed);
            if(dashing) {
                currentImage = image[7];
            }
        } else if (keyH.mousePosition().getX() - gp.location.getX()
                    > gameSettings.getScreenWidth2() / 2) {
            updateAnimation(0, 2, animationSpeed);
            if(dashing) {
                currentImage = image[8];
            }
        }
        

        if (frameTick[1] % 10 < 2 || frameTick[1] == 0) {
            g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gameSettings.getTileSize(), gameSettings.getTileSize(), null);
        }
        if (meleeHitBox.active || attackAnimation != 0) {
            g2.drawImage(rotateImage(image[4 + attackAnimation], hitBoxDirectionVector2d.angleVectorAndHorizontalAxis(), g2), 
            (int) Math.round(hitBoxLocationVector2d.getX() - worldX + screenX), 
            (int) Math.round(hitBoxLocationVector2d.getY() - worldY + screenY),
            gameSettings.getTileSize(), gameSettings.getTileSize(), null);
        }

    }
}
