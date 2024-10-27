package entity;

import core.FontManager;
import java.awt.Font;
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
    public String remainingTime;
    private int min;
    private int sec;

    /**
     * Sets the default values for the fields.
     */
    public Time() {
        gameTime = 0;
        gameSettings = new GameSettings();
        baseText = "Remaining time: ";
        remainingTime = "1:00";
        min = 0;
        sec = 0;
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

        //Determining the right size of the font based on tile size
        g2.setFont(FontManager.getCustomFont(25f));
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textHeight = fontMetrics.getHeight();
        //float resize = gameSettings.getTileSize() / textHeight;
        //textHeight *= resize;
        //g2.setFont(FontManager.getCustomFont(resize));

        //Calculating the remaining seconds
        if (gameTime / 60 < 1) {
            min = 1;
            sec = 0;
        } else {
            min = 0;
            sec = 60 - (int) (gameTime / 60);
        }
        remainingTime = String.valueOf(min) + ":";
        if (sec <= 9) {
            remainingTime += "0";
        } 
        remainingTime += String.valueOf(sec);

        //Determing the x-coordinate of the text such that it
        //will appear in the top right corner
        // FontMetrics actualFontMetrics = g2.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(baseText + remainingTime);

        //Drawing the string
        g2.drawString(baseText + remainingTime, gameSettings.getScreenWidth() - textWidth - 5,
                     textHeight);
    }
}
