package entity;

import core.OnTriggerCircleCollision;
import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameSettings;

public class Bullet extends GameObject{
    
    private BufferedImage[] image = new BufferedImage[1];
    GamePanel gp;
    Player player;

    protected Vector2D vector2d = new Vector2D(0, 0);
    private GameSettings gameSettings = new GameSettings();
    public OnTriggerCircleCollision bulletHitBox;

    public int life = 120;

    public Bullet(GamePanel gp, Player player, Graphics2D g2, double x, double y) {
        this.gp = gp;
        this.player = player;
        setInitialPosition(x, y, 10);
        SetupVector();
        bulletHitBox = new OnTriggerCircleCollision(gp, 10, new Vector2D(worldX, worldY));
        bulletHitBox.active = true;
        getImages();
        image[0] = rotateImage(image[0], vector2d.angleVectorAndHorizontalAxis(), g2);
    }

    public void SetupVector() {
        vector2d = new Vector2D(player.worldX - worldX, player.worldY - worldY);
        vector2d.normalize();
        vector2d.multiply(speed);
    }

    public void getImages() {
        try {
                image[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_spike.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        
        screenPostionRelativeToPlayer(gp.player);
        life--;
        enemyAttack();
        
        worldX += vector2d.getX();
        worldY += vector2d.getY();
    }

    public void draw(Graphics2D g2) {

        BufferedImage currentImage = image[0];
        g2.drawImage(currentImage, (int) Math.round(screenX), (int) Math.round(screenY),
                     gameSettings.getTileSize(), gameSettings.getTileSize(), null);
    }

    public void enemyAttack() {
        if (bulletHitBox.checkCollisionBetween2Objects(player.worldX, worldX, player.worldY, worldY, bulletHitBox, player.playerHitBox)) {
            player.damagePlayer();
        }
    }
}
