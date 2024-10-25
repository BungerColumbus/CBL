package main;


import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class TitleScreenFrame extends JFrame {

    private TitleScreenPanel titleScreenPanel;
    private GameSettings gameSettings;
    
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
            gameSettings = new GameSettings();
            this.dispose();
            new GameFrame(gameSettings.getScreenWidth2(), gameSettings.getScreenHeight2());
        });

    }

}   
