package main;

import entity.Enemy;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    //Settings for the screen ()

    //the default size for sprites, tiles etc
    final int originalTileSize = 16;

    //since today a computer has a bigger resolution
    //than older ones we must scale the game so that
    //the sprites won't be extremly small
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 18;
    final int maxScreenRow = 12;
    final int screenHeight = tileSize * maxScreenRow;
    final int screenWidth = tileSize * maxScreenCol;
    static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Point location;
    int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH);
    Enemy enemy = new Enemy(this, player);
    Thread gameThread;

    //Set player's default position
    int playerX = 0;
    int playerY = 0;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void run() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update() {

        location = this.getLocationOnScreen();
        player.update();
        enemy.update();
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);
        enemy.draw(g2);

        g2.dispose();
    }

}
