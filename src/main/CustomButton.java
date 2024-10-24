package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CustomButton extends JButton {

    public CustomButton(int width, int height, Color color, String text) {

        Font jerseyFont = createNewFont();
        jerseyFont = jerseyFont.deriveFont(50f);
        this.setFont(jerseyFont);
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
            jersey = Font.createFont(Font.TRUETYPE_FONT, new File("src/res/font/Jersey_10/Jersey10-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(jersey);
            return jersey;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            
        }
        return null;
    }
}
