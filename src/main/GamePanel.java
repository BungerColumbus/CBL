package main;

import core.CollisionCheck;
import entity.EnemyManager;
import entity.Bullet;
import entity.Heart;
import entity.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    

    /*GAME STATES
    public int gameState = 0;
    public final int titleState = 0;
    public final int playState = 1;
    public final int endState = 2;*/

    private int fps = 60;
    public Point location = new Point(0, 0);
    private BufferedImage tempScreen;
    private Graphics2D g2;

    private GameSettings gameSettings = new GameSettings(); 
    public KeyHandler keyH = new KeyHandler();
    public TileManager tileManager = new TileManager(this);
    public Player player = new Player(this, keyH);
    public EnemyManager enemyManager = new EnemyManager(this, player);
    public Bullet bullet = new Bullet(this, player, g2, 1344, 1344);
    public CollisionCheck collisionCheck = new CollisionCheck(this, keyH);
    Heart hearts = new Heart(this, player);
    Thread gameThread;


    public GamePanel(int width, int height) {
        this.setSize(width, height);
        this.setBackground(new Color(51, 51, 51));
        // Helps with rendering
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
        this.setVisible(true);
        tempScreen = new BufferedImage(gameSettings.getScreenWidth(),
                                       gameSettings.getScreenHeight(), BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    // Game time. It makes it possible to keep a constant framerate, no matter how fast the computer is running
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

    // The main update void of the game (which happens 60 times a second)
    public void update() {
        location = this.getLocationOnScreen();
        player.update();
        bullet.update();
        enemyManager.update();
    }


    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, gameSettings.getScreenWidth2(),
                                          gameSettings.getScreenHeight2(), null);
        g.dispose();
    }

    // Everything in the game scene is being drawn to the tempScreen
    public void drawToTempScreen() {
        tileManager.draw(g2);
        enemyManager.draw(g2);
        player.draw(g2);
        bullet.draw(g2);
        hearts.draw(g2);
    }

}
