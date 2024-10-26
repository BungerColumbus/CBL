package main;

import core.FontManager;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class CustomButton extends JButton {

    public CustomButton(int width, int height, Color color, String text) {

        this.setFont(FontManager.getCustomFont(50f));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(color);
        this.setText(text);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setVisible(true);
    }
}
