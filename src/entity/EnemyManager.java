package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import main.GamePanel;

/**
 * Controls the number, type and location of the enemies
 * that appear on teh map.
 */
public class EnemyManager {

    private Time time;
    private GamePanel gp;
    private Player player;

    // List for each enemy in play
    public ArrayList<Enemy> basicEnemies;
    public ArrayList<RangedEnemy> rangedEnemies;
    public ArrayList<Bullet> bullets;
    // We use this to keep track of how many frames have passed
    public int[] frameTick = new int[2];
    
    /**
     * Constructor where we define each enemy we want to add. 
     * This way it is easier to add a multitude of enemies.
     */
    public EnemyManager(GamePanel gp, Player player, Time time)
    {
        this.time = time;
        this.gp = gp;
        this.player = player;
        basicEnemies = new ArrayList<Enemy>();
        rangedEnemies = new ArrayList<RangedEnemy>();
        bullets = new ArrayList<Bullet>();
    }

    // The update void for the Enemy Manager. 
    public void update() {
        enemy();
        rangedEnemy();
        bulletEnemy();
        gameplay();
    }

    /** 
     * Draws each enemy from the list.
     */
    public void draw(Graphics2D g2) {
        for (Enemy enemy : basicEnemies) {
            enemy.draw(g2);
        }
        for (RangedEnemy enemy : rangedEnemies) {
            enemy.draw(g2);
        }
        for (Bullet enemy : bullets) {
            enemy.draw(g2);
        }
    }

    /**
     * Controls the number and type of enemies depending on time survived.
     */
    public void gameplay() {
        if (time.getGameTime() / 60 <= 20) {
            spawnEnemy(180 + (int) Math.random() * 60);
        } else if (time.getGameTime() / 60 <= 40) {
            spawnEnemy(180 + (int) Math.random() * 60);
            spawnRangedEnemy(390 + (int) Math.random() * 60);
        } else if (time.getGameTime() / 60 <= 60) {
            spawnEnemy(150 + (int) Math.random() * 60);
            spawnRangedEnemy(360 + (int) Math.random() * 60);
        }
    }

    // We use an iterator so that we can safely remove the enemy from the list.
    private void enemy() {
        Iterator<Enemy> enemyIterator = basicEnemies.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates and if not enough life,
            // it removes it
            Enemy enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                enemyIterator.remove();
            }
        }
    }

    // We use an iterator so that we can safely remove the rangedEnemy from the list.
    private void rangedEnemy() {
        Iterator<RangedEnemy> enemyIterator = rangedEnemies.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it
            RangedEnemy enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                enemyIterator.remove();
            }
        }
    }

    // We use an iterator so that we can safely remove the bullet from the list.
    private void bulletEnemy() {
        Iterator<Bullet> enemyIterator = bullets.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it
            Bullet enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                enemyIterator.remove();
            }
        }
    }

    // Spawn ranged enemies every x amount of frames
    private void spawnRangedEnemy(int frames) {
        if  (frameTick[1] > frames) {

            rangedEnemies.add(new RangedEnemy(gp, player));
            frameTick[1] = 0;
        }
        frameTick[1]++;
    }

    
    // Spawn enemies every x amount of frames
    private void spawnEnemy(int frames) {
        if  (frameTick[0] > frames) {

            basicEnemies.add(new Enemy(gp, player));
            frameTick[0] = 0;
        }
        frameTick[0]++;
    }

    // Spawn bullet
    public void spawnBullet(Graphics2D g2, double x, double y) {
        bullets.add(new Bullet(gp, player, gp.getGraphics2d(), x, y));
    }
}
