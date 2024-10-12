package main;

import entity.Enemy;
import entity.Player;
import tile.TileManager;

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
    static final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int MINIMAL_RESOLUTION = 1536 * 864;
    static final int DEFAULT_RESOLUTION = 1920 * 1080;
    static final int DEFAULT_SCALE = 3;
    static final int SCREEN_AREA = (int) (SIZE.getWidth() * SIZE.getHeight());
    static int scale = 3;

    void determineScale() {
        if (SCREEN_AREA <= MINIMAL_RESOLUTION) {
            scale = 3;
        } else if (SCREEN_AREA == DEFAULT_RESOLUTION) {
            scale = 4;
        } else if (SCREEN_AREA > DEFAULT_RESOLUTION) {
            scale = 6;
        }
    }

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenWidth = tileSize * maxScreenCol;
    
    public Point location;

    //WORLD SETTINGS
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldCol;

    int fps = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    Enemy enemy = new Enemy(this, player);
    Thread gameThread;

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
        location = new Point(0, 0);
        System.out.println("asda");

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

        tileManager.draw(g2);
        player.draw(g2);
        enemy.draw(g2);

        g2.dispose();
    }

}
