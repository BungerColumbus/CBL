package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;


public class TitleScreen {
    private BufferedImage[] image = new BufferedImage[2];
    GamePanel gp;
    Font jersey;
    
    public TitleScreen(GamePanel gp) {
        this.gp = gp;
        getImages();
        getFont();
    }

    private void getImages() {

        try {

            image[0] = ImageIO.read(getClass()
                        .getResourceAsStream("/res/titleScreen/TitleScreen.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFont() {
        try {
            jersey = Font.createFont(Font.TRUETYPE_FONT, new File("Jersey10-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(jersey);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

    }


    public void draw(Graphics2D g2) {

        BufferedImage currentImage = image[0];
        g2.drawImage(currentImage, 0, 0,
            gp.screenWidth2, (gp.screenWidth2 / 256) * 72, null);

        /*JButton playButton = new JButton("Start");
        jersey = g.getFont().deriveFont(20f);
        playButton.setFont(jersey);
        playButton.setBackground(Color.ORANGE);
        Main.window.setLayout(new BorderLayout());
        Main.window.add(playButton, BorderLayout.CENTER);

        playButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                gp.gameState = gp.playState;
                gp.startGameThread();
            }  
        }); */
    }

}   
