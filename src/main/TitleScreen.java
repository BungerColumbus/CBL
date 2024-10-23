package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;

public class TitleScreen{
    private BufferedImage[] image = new BufferedImage[2];
    GamePanel gp;
    KeyHandler keyH;
    Font arial_40;

    public TitleScreen(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        arial_40 = new Font("Arial", Font.BOLD, 40);

        getImages();
    }

    public void getImages() {

        try {

            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/titleScreen/TitleScreen.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyH.startButton && gp.gameState == 0) {
            gp.gameState = 1;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage currentImage = image[0];
        g2.drawImage(currentImage, 0, 0,
            gp.screenWidth, (gp.screenWidth / 256) * 72, null);

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawString("Press Q to START the game", gp.screenWidth / 2 - 256, gp.screenHeight / 2);
    }
}
