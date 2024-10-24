package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TitleScreenPanel extends JPanel{
    
    private BufferedImage[] image = new BufferedImage[2];

    public TitleScreenPanel(int width, int height) {
        this.setSize(width, height);
        this.setBackground(new Color(51, 51, 51));
        this.setOpaque(true);
        this.setVisible(true);
        getImages();
    }

    private void getImages() {

        try {

            image[0] = ImageIO.read(getClass()
                        .getResourceAsStream("/res/titleScreen/TitleScreen.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage currentImage = image[0];
        g2.drawImage(currentImage, 0, 0, getWidth(), (getWidth() / 256) * 72, null);

    }
}
