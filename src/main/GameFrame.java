package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    static GamePanel gamePanel;
    
    public GameFrame(int width, int height) {
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setTitle("Jellies in Hellmond");

        gamePanel = new GamePanel(width, height);
        this.add(gamePanel);
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        gamePanel.startGameThread();
    }
}
