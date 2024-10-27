package main;

import core.FontManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * EndFrameScreen has a main panel composed of 3 other panels stacked
 * one over the other and displays the result of the game and buttons.
 */
public class EndScreenFrame extends JFrame {
    
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JLabel gameOverJLabel;
    private JLabel timeSurvivedJLabel;
    private JLabel scoreJLabel;
    private JLabel actualTime;
    private JLabel actualScore;

    private CustomButton retryButton;
    private CustomButton titleScreenButton;
    private CustomButton exitButton;

    private Color backgroundColor;

    private GameSettings gameSettings;

    private boolean outcome;
    private int timeSurvived;

    /**
     * EndScreenFrame is the window displayed when the player's life runs out
     * providing options like Retry, Go to Start Screen or Quit.
     * @param width of the window
     * @param height of the window
     */
    public EndScreenFrame(int width, int height, boolean outcome, int timeSurvived) {
        this.outcome = outcome;
        this.timeSurvived = timeSurvived;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        setCustomTitle();
        this.setLocationRelativeTo(null);

        backgroundColor = new Color(51, 51, 51);
        gameSettings = new GameSettings();

        mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(150));

        //"GAME OVER" at the top of the window
        upperPanel = new JPanel();
        upperPanel.setBorder(new EmptyBorder(0, 0, 0, 0)); 
        upperPanel.setBackground(backgroundColor);
        gameOverJLabel = new JLabel();
        gameOverJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverJLabel.setFont(FontManager.getCustomFont(90f));
        gameOverJLabel.setBackground(backgroundColor);
        gameOverJLabel.setForeground(Color.WHITE);
        setCustomText();
        upperPanel.add(gameOverJLabel);

        //Arranges panels on the mainPanel
        mainPanel.add(upperPanel);
        //mainPanel.add(Box.createVerticalStrut(5));

        //Results of the round panel
        middlePanel = new JPanel();
        middlePanel.setBorder(new EmptyBorder(0, 0, 0, 0)); 
        middlePanel.setBackground(backgroundColor);
        middlePanel.setLayout(new GridLayout(2, 2, 0, -25));

        //Displays the text "Time survived:"
        timeSurvivedJLabel = new JLabel();
        timeSurvivedJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeSurvivedJLabel.setFont(FontManager.getCustomFont(50f));
        timeSurvivedJLabel.setBackground(backgroundColor);
        timeSurvivedJLabel.setForeground(Color.ORANGE);
        timeSurvivedJLabel.setText("Time survived:");
        middlePanel.add(timeSurvivedJLabel);

        //Displays the text "Score:"
        scoreJLabel = new JLabel();
        scoreJLabel.setFont(FontManager.getCustomFont(50f));
        scoreJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreJLabel.setBackground(backgroundColor);
        scoreJLabel.setForeground(Color.ORANGE);
        scoreJLabel.setText("Score:");
        middlePanel.add(scoreJLabel);

        //Displays the time
        actualTime = new JLabel();
        actualTime.setFont(FontManager.getCustomFont(60f));
        actualTime.setHorizontalAlignment(SwingConstants.CENTER);
        actualTime.setBackground(backgroundColor);
        actualTime.setForeground(Color.WHITE);
        actualTime.setText(getTimeString());
        middlePanel.add(actualTime);

        //Displays the score
        actualScore = new JLabel();
        actualScore.setFont(FontManager.getCustomFont(60f));
        actualScore.setHorizontalAlignment(SwingConstants.CENTER);
        actualScore.setBackground(backgroundColor);
        actualScore.setForeground(Color.WHITE);
        actualScore.setText("245");
        middlePanel.add(actualScore);

        //Arranges panels on the mainPanel
        mainPanel.add(middlePanel);
        mainPanel.add(Box.createVerticalStrut(10));


        //Buttons panel
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
        lowerPanel.setBackground(backgroundColor);

        //Allows the player to start the game again directly
        retryButton = new CustomButton(300, 70, Color.GREEN, "Try again");
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Arranges buttons on top of each other
        lowerPanel.add(retryButton);
        lowerPanel.add(Box.createVerticalStrut(10));
        //Allows player to see the title screen again
        titleScreenButton = new CustomButton(300, 70, Color.ORANGE, "Start Screen");
        titleScreenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Arranges buttons on top of each other
        lowerPanel.add(titleScreenButton);
        lowerPanel.add(Box.createVerticalStrut(10));
        //Gives another method to exit
        exitButton = new CustomButton(300, 70, Color.RED, "Quit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        lowerPanel.add(exitButton);

        //Arranges panels on the mainPanel
        mainPanel.add(lowerPanel);

        this.add(mainPanel);
        this.setVisible(true);

        retryButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            new GameFrame(gameSettings.getScreenWidth2(), gameSettings.getScreenHeight2());
        });

        titleScreenButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            new TitleScreenFrame(1000, 750);
        });

        exitButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            System.exit(0);
        });
    }

    private void setCustomTitle() {
        if (!outcome) {
            this.setTitle("Jellies in Hellmond - Game Over");
        } else {
            this.setTitle("Jellies in Hellmond - End of game");
        }
    }

    private void setCustomText() {
        if (!outcome) {
            gameOverJLabel.setText("GAME OVER");
        } else {
            gameOverJLabel.setText("YOU WON!");
        }
    }

    private String getTimeString() {
        int min = timeSurvived / 60;
        int sec = timeSurvived % 60;
        String timeString = String.valueOf(min) + ":";
        if (sec <= 9) {
            timeString += "0";
        }
        timeString += String.valueOf(sec);
        return timeString;
    }
}
