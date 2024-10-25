package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameSettings;

public class Heart extends GameObject {
    private BufferedImage[] image = new BufferedImage[2];
    GamePanel gp;
    Player player;
    private GameSettings gameSettings = new GameSettings();

    public Heart(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        screenX = gameSettings.getTileSize();
        screenY = 0;

        getImages();
    }

    public void getImages() {

        try {

            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/hearts/Hearts1.png"));
            image[1] = ImageIO.read(getClass().getResourceAsStream("/res/hearts/Hearts2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g2) {

        BufferedImage[] heart = new BufferedImage[player.maxLife];
        int index;
        for (index = 0; index < player.maxLife; index++) {
            if (index <= player.life - 1) {
                heart[index] = image[0];
                g2.drawImage(heart[index], (int) Math.round(screenX * index),
                            (int) Math.round(screenY), gameSettings.getTileSize(), gameSettings.getTileSize(), null);
            } else if (index > player.life - 1) {
                heart[index] = image[1];
                g2.drawImage(heart[index], (int) Math.round(screenX * index),
                            (int) Math.round(screenY), gameSettings.getTileSize(), gameSettings.getTileSize(), null);
            }
        }
        
    }
}
