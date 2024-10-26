package main;

import core.FontManager;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class InstructionsPanel extends JPanel{
    

    private String[] text;

    public InstructionsPanel() {
        this.setBackground(new Color(51, 51, 51));
        this.setOpaque(true);
        this.setVisible(true);
        text = new String[] 
            {"What is my goal?",
            "To survive until the end by fighting off all the demons from Hellmond.",

            "How to move?",
            "Press 'A' for left, 'W' for up, 'S' for down, 'D' for right.",
            "Press 'space' for a momentary boost of speed to dodge enemies.",
            
            "How to attack?",
            "Left-click in the direction of the enemy relative to your character.",
            "We recommend using a mouse for the best experience."
            };
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(FontManager.getCustomFont(27f));
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textWidth;
        int textHeight;
        int x;
        int y = 20;

        for (int i = 0; i < text.length; i++) {
            // Calculate the coordinates to center the text
            textWidth = fontMetrics.stringWidth(text[i]);
            textHeight = fontMetrics.getHeight();
            x = (getWidth() - textWidth) / 2;

            if (i == 0 || i == 2 || i == 5) {
                g2.setColor(Color.ORANGE);
                y += 40 + textHeight;
            } else {
                g2.setColor(Color.WHITE);
                y += 15 + textHeight;
            }
            g2.drawString(text[i], x, y);
        }
        
    }
}
