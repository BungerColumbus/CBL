package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameSettings;

public class Enemy extends GameObject {

    private BufferedImage[] image = new BufferedImage[9];
    GamePanel gp;
    Player player;

    private int animationSpeed = 15;
    protected Vector2D vector2d = new Vector2D(0, 0);
    private GameSettings gameSettings = new GameSettings();
    public OnTriggerCircleCollision enemyHitBox;

    private boolean canTakeDamage = true;
    public int life = 3;
    public int maxLife = 3; 

    // The constructor of the enemy class
    public Enemy(GamePanel gp, Player player) {
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
                image[i] = 
                    ImageIO.read(getClass().getResourceAsStream(
                        "/res/enemy/enemy_slime_spawning" + i + ".png"));
            }
            for (int i = 5; i <= 8; i++) {
                image[i] = 
                    ImageIO.read(getClass().getResourceAsStream(
                        "/res/enemy/enemy_slime" + (i - 5) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This is played 60 times per second by the gamepanel
    public void update() {

        // Setting up the vector direction vector in order for the enemy to move towards the player
        vector2d = new Vector2D(player.worldX - worldX, player.worldY - worldY);
        vector2d.normalize();
        vector2d.multiply(speed);

        // Using the animationIndex in order to let the enemy move only after it finished the 
        // spawning sequence
        if (animationIndex > 3) {
            // Changing the world position depending on the vector. If the enemy received damage
            // from the enemy it moves in the opposite direction at twice the speed
            if (canTakeDamage) {
                worldX += vector2d.getX();
                worldY += vector2d.getY();
            } else {
                worldX -= vector2d.getX() * 2;
                worldY -= vector2d.getY() * 2;
            }
        }
        screenPostionRelativeToPlayer(gp.player);
        coolDownHP(10);
        damageEnemy();
        enemyAttack();
    }

    // Drawing the images in respect to the rules set by the updateAnimation void
    public void draw(Graphics2D g2) {
        if (animationIndex < 3) {
            updateAnimation(0, 4, animationSpeed);
        }
        else if (player.worldX < worldX) {
            updateAnimation(7, 9, animationSpeed);
        } else if (player.worldX > worldX) {
            updateAnimation(5, 7, animationSpeed);
        }

        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gameSettings.getTileSize(), gameSettings.getTileSize(), null);
    }

    // Using enemyHitBox, the enemy check the collision between itself and the playerHitBox
    public void enemyAttack() {
        if (enemyHitBox.checkCollisionBetween2Objects(
                player.worldX, worldX, player.worldY, worldY, enemyHitBox, player.playerHitBox) 
                && animationIndex > 3) {
            player.damagePlayer();
        }
    }

    // Using enemyHitBox, the enemy check the collision between itself
    // and the player's meeleHitBox
    public void damageEnemy() {
        if (canTakeDamage && enemyHitBox.checkCollisionBetween2Objects(
                player.hitBoxLocationVector2d.getX(), worldX, player.hitBoxLocationVector2d.getY(),
                worldY, enemyHitBox, player.meleeHitBox)) {
            canTakeDamage = false;
            life--;
        }
    }

    // Cooldown for the damageEnemy void
    public void coolDownHP(int frames) {
        if (frameTick[2] > frames) {
            canTakeDamage = true;
            frameTick[2] = 0;
        } else if (!canTakeDamage) {
            frameTick[2]++;
        }
    }
}
