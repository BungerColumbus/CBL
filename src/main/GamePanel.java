package main;

import entity.Enemy;
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

    //since today a computer has a bigger resolution
    //than older ones we must scale the game so that
    //the sprites won't be extremly small
    static final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int DEFAULT_WIDTH = 1536;
    static final int DEFAULT_HEIGHT = 864;
    static final int MONITOR_WIDTH = (int) (SIZE.getWidth());
    static final int MONITOR_HEIGHT = (int) (SIZE.getHeight());


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
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    
    public Point location = new Point(0, 0);

    //WORLD SETTINGS
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldCol;

    int fps = 60;

    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    public Enemy enemy = new Enemy(this, player);
    public CollisionCheck collisionCheck = new CollisionCheck(this);
    Thread gameThread;


    public GamePanel() {
        //this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        int resize = Math.round((float) MONITOR_WIDTH / DEFAULT_WIDTH);
        screenWidth2 = screenWidth * resize;
        screenHeight2 = screenHeight * resize;
        Main.window.setSize(screenWidth2, screenHeight2);
        int xCenter = (int) ((MONITOR_WIDTH - Main.window.getWidth()) / 2);
        int yCenter = (int) ((MONITOR_HEIGHT - Main.window.getHeight()) / 2);
        Main.window.setLocation(xCenter, yCenter);
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
        //location = new Point(0, 0);
        System.out.println("asda");

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
        enemy.draw(g2);
    }

}
