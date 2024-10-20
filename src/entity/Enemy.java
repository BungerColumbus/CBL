package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Enemy extends GameObject {
    private BufferedImage[] image = new BufferedImage[4];
    GamePanel gp;
    Player player;

    private int animationSpeed = 15;
    protected Vector2D vector2d = new Vector2D(0, 0);
    public OnTriggerCircleCollision enemyHitBox;

    private boolean canAttack = false;
    private boolean canTakeDamage = true;
    public int life = 3;
    public int maxLife = 3; 

    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        setInitialPosition(800, 800, 2);
        enemyHitBox = new OnTriggerCircleCollision(gp, 30, new Vector2D(worldX, worldY));
        getImages();
    }

    public void getImages() {

        try {

            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime0.png"));
            image[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime1.png"));
            image[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime2.png"));
            image[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        //vector2d = new Vector2D(player.worldX - worldX, player.worldY - worldY);
        vector2d.normalize();
        vector2d.multiply(speed);
        worldX += vector2d.getX();
        worldY += vector2d.getY();
        screenPostionRelativeToPlayer(gp.player);
        //System.out.println(enemyHitBox.checkCollisionBetween2Objects(player, this, enemyHitBox, player.playerHitBox));
        CoolDownAttack(15);
        CoolDownHP(10);
        damageEnemy();
        enemyAttack();
    }

    public void draw(Graphics2D g2) {

        if (player.worldX < worldX) {
            updateAnimation(2, 4, animationSpeed);
        } else if (player.worldX > worldX) {
            updateAnimation(0, 2, animationSpeed);
        }

        BufferedImage currentImage = image[animationIndex];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gp.tileSize, gp.tileSize, null);
    }

    public void enemyAttack() {
        if (canAttack && enemyHitBox.checkCollisionBetween2Objects(player.worldX, worldX, player.worldY, worldY, enemyHitBox, player.playerHitBox)) {
            player.damagePlayer();
            canAttack = false;
        }
    }

    public void damageEnemy() {
        if (canTakeDamage && enemyHitBox.checkCollisionBetween2Objects(player.hitBoxLocationVector2d.getX(), worldX, player.hitBoxLocationVector2d.getY(), worldY, enemyHitBox, player.meleeHitBox)) {
            canTakeDamage = false;
            life--;
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
