package main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import core.FontManager;

public class EndScreenPanel extends JPanel{
 
    private String text;

    public EndScreenPanel(int width, int height) {
        this.setSize(width, height);
        this.setBackground(new Color(51, 51, 51));
        this.setOpaque(true);
        this.setVisible(true);

        text = "GAME OVER";
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(FontManager.getCustomFont(100f));
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        g2.setColor(Color.WHITE);
        g2.drawString(text, (getWidth() - textWidth) / 2, 100);

    }
}
