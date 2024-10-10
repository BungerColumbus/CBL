package entity;

import core.Vector2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import main.GamePanel;
import main.KeyHandler;

public class Player extends GameObject {
    
    private BufferedImage[] image = new BufferedImage[4];
    GamePanel gp;
    KeyHandler keyH;
    protected int speed = 4;
    protected Vector2D vector2d = new Vector2D(0, 0);

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
    }

    public void getPlayerImage() {

        try {

            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime0.png"));
            image[1] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime1.png"));
            image[2] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime2.png"));
            image[3] = ImageIO.read(getClass().getResourceAsStream("/res/player/player_slime3.png"));

        } catch(IOException e) {
            e.printStackTrace();
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
        vector2d.normalize();
        vector2d.multiply(speed);
        x += vector2d.getX();
        y += vector2d.getY();

        System.out.println(keyH.mousePosition().getX() - gp.location.getX() - x - gp.tileSize/2);
        
    }

    public void draw(Graphics2D g2) {



        BufferedImage currentImage = null;
        if(keyH.mousePosition().getX() - gp.location.getX() - x - gp.tileSize/2 < 0) {
            currentImage = image[2];
        } else if(keyH.mousePosition().getX() - gp.location.getX() - x - gp.tileSize/2 > 0) {
            currentImage = image[0];
        }

        g2.drawImage(currentImage, (int) Math.round(x), (int) Math.round(y), gp.tileSize, gp.tileSize, null);
    }
}
