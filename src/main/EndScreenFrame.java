package main;

import javax.swing.JFrame;

public class EndScreenFrame extends JFrame{
    
    EndScreenPanel endScreenPanel;

    public EndScreenFrame(int width, int height) {
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Jellies in Hellmond - Game Over");

        endScreenPanel = new EndScreenPanel(width, height);
        this.add(endScreenPanel);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}
