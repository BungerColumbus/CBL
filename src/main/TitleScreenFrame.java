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
        this.setTitle("Jellies in Hellmond - Start Screen");

        //Creating panel, setting location and layout
        titleScreenPanel = new TitleScreenPanel(width, height);
        this.add(titleScreenPanel);
        this.setLocationRelativeTo(null);
        titleScreenPanel.setLayout(new BoxLayout(titleScreenPanel, BoxLayout.Y_AXIS));

        //Play Button
        CustomButton playButton = new CustomButton(100, 70, Color.ORANGE, "Play");
        titleScreenPanel.add(Box.createVerticalGlue()); // Add flexible space above the button
        playButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        titleScreenPanel.add(playButton);

        //Adding space between buttons
        titleScreenPanel.add(Box.createVerticalStrut(10));

        //How to play? button
        CustomButton instructionsButton = new CustomButton(300, 70, Color.GREEN, "How to play?");
        instructionsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        titleScreenPanel.add(instructionsButton);
        titleScreenPanel.add(Box.createVerticalGlue());

        this.setVisible(true);

        playButton.addActionListener((ActionEvent e) -> {
            gameSettings = new GameSettings();
            this.dispose();
            new GameFrame(gameSettings.getScreenWidth2(), gameSettings.getScreenHeight2());
        });

        instructionsButton.addActionListener((ActionEvent e) -> {
            new InstructionsFrame((int) (width * 0.75), (int) (height * 0.75));
        });

    }

}   
