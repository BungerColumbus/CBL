package entity;

import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setInitialPosition(696, 696, 2);
        solidArea = new Rectangle(8, 16, 32, 26);
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

        vector2d = new Vector2D(player.worldX - worldX, player.worldY - worldY);
        vector2d.normalize();
        vector2d.multiply(speed);
        worldX += vector2d.getX();
        worldY += vector2d.getY();
        screenPostionRelativeToPlayer(gp.player);
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
}
