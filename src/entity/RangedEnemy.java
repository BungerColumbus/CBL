package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameSettings;

public class RangedEnemy extends GameObject {
    private BufferedImage[] image = new BufferedImage[9];
    GamePanel gp;
    Player player;

    private int animationSpeed = 15;
    protected Vector2D vector2d = new Vector2D(0, 0);
    private GameSettings gameSettings = new GameSettings();
    public OnTriggerCircleCollision enemyHitBox;

    private boolean canAttack = false;
    private boolean canTakeDamage = true;
    public int life = 2;
    public int maxLife = 2; 

    // The constructor of the ranged enemy class
    public RangedEnemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        
        setInitialPosition((Math.random() * 1344) + 432, (Math.random() * 1344) + 288, 2);
        enemyHitBox = new OnTriggerCircleCollision(gp, 15, new Vector2D(worldX, worldY));
        getImages();
    }

    // Gets the images through a try-catch method
    public void getImages() {
        try {
            for (int i = 0; i <= 3; i++) {
                image[i] = ImageIO.read(getClass().getResourceAsStream(
                    "/res/enemy/enemy_slime_spawning" + i + ".png"));
            }
            for (int i = 5; i <= 8; i++) {
                image[i] = ImageIO.read(getClass().getResourceAsStream(
                    "/res/enemy/enemy_slime" + (i - 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This is played 60 times per second by the gamepanel
    public void update() {

        // Setting up the vector direction vector in order for the enemy to move towards the player
        vector2d = new Vector2D(player.worldX - worldX, player.worldY - worldY);
        // Keeping track of the distance between the enemy and the player
        double distance = vector2d.length();
        vector2d.normalize();
        vector2d.multiply(speed);
       
        // Same as in the class enemy except this time, if the enemy is too close it won't move
        // towards the player anymore. It's not a ranged enemy in order to fight in close combat
        if (animationIndex > 3) {
            if (canTakeDamage && distance > 240) {
                worldX += vector2d.getX();
                worldY += vector2d.getY();
            } 
            if (!canTakeDamage) {
                worldX -= vector2d.getX() * 2;
                worldY -= vector2d.getY() * 2;
            }
        }
        screenPostionRelativeToPlayer(gp.player);
        // Compared to the normal enemy, the ranged one has a bigger cooldown for ranged attacks
        coolDownAttack(120 + (int) (Math.random() * 40));
        coolDownHP(10);
        damageEnemy();
        enemyAttack();
    }

    // Same as in enemy class
    public void draw(Graphics2D g2) {
        if (animationIndex < 3) {
            updateAnimation(0, 4, animationSpeed);
        } else if (player.worldX < worldX) {
            updateAnimation(7, 9, animationSpeed);
        } else if (player.worldX > worldX) {
            updateAnimation(5, 7, animationSpeed);
        }

        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gameSettings.getTileSize(), gameSettings.getTileSize(), null);
    }

    // The attack of this enemy spawns a bullet using the enemyManager from the gamepanel
    public void enemyAttack() {
        if (enemyHitBox.checkCollisionBetween2Objects(
                player.worldX, worldX, player.worldY, worldY, enemyHitBox, player.playerHitBox) 
                && animationIndex > 3) {
            player.damagePlayer();
        }
        if (canAttack) {
            canAttack = false;
            gp.enemyManager.spawnBullet(gp.getGraphics2d(), worldX, worldY);
        }
    }

    // Same as in enemy class
    public void damageEnemy() {
        if (canTakeDamage && enemyHitBox.checkCollisionBetween2Objects(
                player.hitBoxLocationVector2d.getX(), worldX, player.hitBoxLocationVector2d.getY(),
                worldY, enemyHitBox, player.meleeHitBox)) {
            canTakeDamage = false;
            life--;
        }
    }

    // Had to introduce a cooldown for the attack in order to make the enemy spawna bullet
    // at a consistent rate
    public void coolDownAttack(int frames) {
        if (frameTick[1] > frames) {
            canAttack = true;
            frameTick[1] = 0;
        } else if (!canAttack) {
            frameTick[1]++;
        }
    }

    // Same as in enemy class
    public void coolDownHP(int frames) {
        if (frameTick[2] > frames) {
            canTakeDamage = true;
            frameTick[2] = 0;
        } else if (!canTakeDamage) {
            frameTick[2]++;
        }
    }
}
