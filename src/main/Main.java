package main;

import javax.swing.JFrame;

public class Main {
    
    public static JFrame window;
    
    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Jellies in Hellmond");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        gamePanel.setupGame();
        window.setVisible(true);
        gamePanel.openTitleScreen();

    }
}
