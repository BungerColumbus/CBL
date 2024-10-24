package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class TitleScreenFrame extends JFrame {

    static TitleScreenPanel titleScreenPanel;
    
    public TitleScreenFrame(int width, int height) {
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Jellies in Hellmond");

        titleScreenPanel = new TitleScreenPanel(width, height);
        this.add(titleScreenPanel);
        this.setLocationRelativeTo(null);

        CustomButton playButton = new CustomButton(100, 50, Color.ORANGE, "Play");
        titleScreenPanel.setLayout(new BoxLayout(titleScreenPanel, BoxLayout.Y_AXIS));
        titleScreenPanel.add(Box.createVerticalGlue()); // Add flexible space above the button
        playButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Center the button horizontally
        titleScreenPanel.add(playButton);
        titleScreenPanel.add(Box.createVerticalGlue()); // Add flexible space below the button


        this.setVisible(true);

        playButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            
        });
    }


}   
