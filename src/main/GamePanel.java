package main;

import core.CollisionCheck;
import entity.Enemy;
import entity.Heart;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    //Settings for the screen ()

    //the default size for sprites, tiles etc
    final int originalTileSize = 16;


    //Temp screen settings
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenWidth = tileSize * maxScreenCol;
    BufferedImage tempScreen;
    Graphics2D g2;
    
    //Actual window settings
    private int screenWidth2 = screenWidth * Main.titleScreenFrame.resize;
    private int screenHeight2 = screenHeight * resize;
    public Point location = new Point(0, 0);

    //WORLD SETTINGS
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldCol;

    /*GAME STATES
    public int gameState = 0;
    public final int titleState = 0;
    public final int playState = 1;
    public final int endState = 2;*/

    int fps = 60;

    KeyHandler keyH = new KeyHandler();
    public TileManager tileManager = new TileManager(this);
    public Player player = new Player(this, keyH);
    public CollisionCheck collisionCheck = new CollisionCheck(this, keyH, player.vector2d);
    public Enemy enemy = new Enemy(this, player);
    Heart hearts = new Heart(this, player);
    Thread gameThread;


    public GamePanel() {
        this.setBackground(new Color(51, 51, 51));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
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
                drawToTempScreen();
                drawToScreen();
                delta--;
            }

        }
    }

    public void update() {
        location = this.getLocationOnScreen();
        player.update();
        enemy.update();
    }


    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void drawToTempScreen() {
        tileManager.draw(g2);
        player.draw(g2);
        hearts.draw(g2);
        enemy.draw(g2);
    }

}
