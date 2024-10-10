package entity;

import core.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Enemy extends GameObject {
    private BufferedImage[] image = new BufferedImage[4];
    GamePanel gp;
    Player player;
    protected int speed = 2;
    protected Vector2D vector2d = new Vector2D(0, 0);

    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        setDefaultValues();
        getEnemyImage();
    }

    public void setDefaultValues() {
        x = 500;
        y = 500;
    }

    public void getEnemyImage() {

        try {

            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime0.png"));
            image[1] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime1.png"));
            image[2] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime2.png"));
            image[3] = ImageIO.read(getClass().getResourceAsStream("/res/enemy/enemy_slime3.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        vector2d = new Vector2D(player.x - x, player.y - y);
        vector2d.normalize();
        vector2d.multiply(speed);
        x += vector2d.getX();
        y += vector2d.getY();
    }

    public void draw(Graphics2D g2) {

        BufferedImage currentImage = null;
        if(player.x < x) {
            currentImage = image[2];
        } else if(player.x > x) {
            currentImage = image[0];
        }

        g2.drawImage(currentImage, (int) Math.round(x), (int) Math.round(y), gp.tileSize, gp.tileSize, null);
    }
}
