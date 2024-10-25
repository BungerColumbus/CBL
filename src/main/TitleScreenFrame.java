package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
//import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class TitleScreenFrame extends JFrame {

    static TitleScreenPanel titleScreenPanel;
    static int resize;
    
    public TitleScreenFrame(int width, int height) {
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Jellies in Hellmond");

        titleScreenPanel = new TitleScreenPanel(width, height);
        this.add(titleScreenPanel);
        this.setLocationRelativeTo(null);

        CustomButton playButton = new CustomButton(200, 100, Color.ORANGE, "Play");
        titleScreenPanel.setLayout(new BoxLayout(titleScreenPanel, BoxLayout.Y_AXIS));
        titleScreenPanel.add(Box.createVerticalGlue()); // Add flexible space above the button
        playButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        titleScreenPanel.add(playButton);
        titleScreenPanel.add(Box.createVerticalGlue()); // Add flexible space below the button


        this.setVisible(true);

        playButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            setUpGame();
            new GameFrame(screenWidth2, screenHeight2);
        });

    }

    private void setUpGame() {
        final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        final int DEFAULT_WIDTH = 1536;
        final int DEFAULT_HEIGHT = 864;
        final int MONITOR_WIDTH = (int) (SIZE.getWidth());
        final int MONITOR_HEIGHT = (int) (SIZE.getHeight());
        resize = Math.round((float) MONITOR_WIDTH / DEFAULT_WIDTH);
       

        // int xCenter = (int) ((MONITOR_WIDTH - Main.window.getWidth()) / 2);
        // int yCenter = (int) ((MONITOR_HEIGHT - Main.window.getHeight()) / 2);
        // Main.window.setLocation(xCenter, yCenter);
    }

}   
