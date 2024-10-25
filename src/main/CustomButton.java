package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CustomButton extends JButton {

    public CustomButton(int width, int height, Color color, String text) {

        Font jerseyFont = createNewFont();
        jerseyFont = jerseyFont.deriveFont(50f);
        this.setFont(jerseyFont);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setSize(width, height);
        this.setBackground(color);
        this.setText(text);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setVisible(true);
    }

    private Font createNewFont() {
        Font jersey;
        try {
            InputStream inputStream = getClass()
                                .getResourceAsStream("/res/font/Jersey_15/Jersey15-Regular.ttf");
            jersey = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(jersey);
            return jersey;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            
        }
        return null;
    }
}
