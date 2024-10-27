package entity;

import core.FontManager;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import main.GameSettings;

/**
 * Records the time passed since the start of the game
 * and displays the remaining time until the end in seconds.
 */
public class Time {
    
    private int gameTime;
    private GameSettings gameSettings;
    private String baseText;
    private String remainingTime;
    private String overTimeMessage;
    private int min;
    private int sec;
    private boolean overTime;

    /**
     * Sets the default values for the fields.
     */
    public Time() {
        gameTime = 0;
        gameSettings = new GameSettings();
        baseText = "Remaining time: ";
        remainingTime = "1:00";
        overTimeMessage = "Kill all remaining enemies to win!";
        min = 0;
        sec = 0;
        overTime = false;
    }

    public int getGameTime() {
        return gameTime;
    }

    //Happens 60 times per second
    public void update() {
        gameTime++;
    }

    /**
     * Draws the remaining time in the upper right corner of the screen while the game is running.
     * @param g2 is the graphical context, specifically the tempScreen
     */
    public void draw(Graphics2D g2) {

        g2.setFont(FontManager.getCustomFont(27f));
        g2.setColor(Color.WHITE);
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textHeight = fontMetrics.getHeight();

        //Calculating the remaining seconds
        if (gameTime / 60 < 1) {
            min = 1;
            sec = 0;
        } else if (gameTime / 60 <= 60) {
            min = 0;
            sec = 60 - (int) (gameTime / 60);
            overTime = true;
        } else {
            min = 0;
            sec = 0;
            overTime = true;
        }
        remainingTime = String.valueOf(min) + ":";
        if (sec <= 9) {
            remainingTime += "0";
        } 
        remainingTime += String.valueOf(sec);
        int textWidth = fontMetrics.stringWidth(baseText + remainingTime);

        //Drawing the string
        g2.drawString(baseText + remainingTime, gameSettings.getScreenWidth() - textWidth - 5,
                     textHeight);
        if (overTime) {
            textWidth = fontMetrics.stringWidth(overTimeMessage);
            g2.drawString(overTimeMessage, (gameSettings.getScreenWidth() - textWidth) / 2,
                            gameSettings.getScreenHeight() - 50); 
        }
    }
}
