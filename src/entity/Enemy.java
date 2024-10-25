package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Enemy extends GameObject {
    private BufferedImage[] image = new BufferedImage[9];
    GamePanel gp;
    Player player;

    private int animationSpeed = 15;
    protected Vector2D vector2d = new Vector2D(0, 0);
    public OnTriggerCircleCollision enemyHitBox;

    private boolean canAttack = false;
    private boolean canTakeDamage = true;
    public int life = 10000;
    public int maxLife = 10000; 

    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        setInitialPosition((Math.random() * 1344) + 432, (Math.random() * 1344) + 288, 2);
        enemyHitBox = new OnTriggerCircleCollision(gp, 15, new Vector2D(worldX, worldY));
        getImages();
    }

    public void getImages() {
        try {
            for(int i = 0; i <= 3; i++) {
                image[i] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime_spawning" + i + ".png"));
            }
            for(int i = 5; i <= 8; i++) {
                image[i] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime" + (i-5) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        vector2d = new Vector2D(player.worldX - worldX, player.worldY - worldY);
        vector2d.normalize();
        vector2d.multiply(speed);
        if (animationIndex > 3) {
            if (canTakeDamage) {
                worldX += vector2d.getX();
                worldY += vector2d.getY();
            } else {
                worldX -= vector2d.getX() * 2;
                worldY -= vector2d.getY() * 2;
            }
        }
        screenPostionRelativeToPlayer(gp.player);
        //System.out.println(enemyHitBox.checkCollisionBetween2Objects(player, this, enemyHitBox, player.playerHitBox));
        CoolDownAttack(15);
        CoolDownHP(10);
        damageEnemy();
        enemyAttack();
    }

    public void draw(Graphics2D g2) {
        if (animationIndex < 3) {
            updateAnimation(0, 4, animationSpeed*5);
        }
        else if (player.worldX < worldX) {
            updateAnimation(7, 9, animationSpeed);
        } else if (player.worldX > worldX) {
            updateAnimation(5, 7, animationSpeed);
        }

        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gp.tileSize, gp.tileSize, null);
        //g2.fillOval((int) Math.round(screenX-15), (int) Math.round(screenY-15),
        //             30, 30);
    }

    public void enemyAttack() {
        if (canAttack && enemyHitBox.checkCollisionBetween2Objects(player.worldX, worldX, player.worldY, worldY, enemyHitBox, player.playerHitBox) && animationIndex > 3) {
            player.damagePlayer();
            canAttack = false;
        }
    }

    public void damageEnemy() {
        if (canTakeDamage && enemyHitBox.checkCollisionBetween2Objects(player.hitBoxLocationVector2d.getX(), worldX, player.hitBoxLocationVector2d.getY(), worldY, enemyHitBox, player.meleeHitBox)) {
            canTakeDamage = false;
            life--;
            System.out.println("Distance between enemy and melee Hitbox: " + new Vector2D(player.hitBoxLocationVector2d.getX() - worldX, player.hitBoxLocationVector2d.getY() - worldY).length());
            System.out.println("Radius of hitboxes summed up: " + (player.meleeHitBox.radius + enemyHitBox.radius));
            System.out.println(life);
        }
    }

    public void CoolDownAttack(int frames) {
        if(frameTick[1] > frames) {
            canAttack = true;
            frameTick[1] = 0;
        }
        else if(!canAttack) {
            frameTick[1]++;
        }
    }

    public void CoolDownHP(int frames) {
        if(frameTick[2] > frames) {
            canTakeDamage = true;
            frameTick[2] = 0;
        }
        else if(!canTakeDamage) {
            frameTick[2]++;
        }
    }
}
