package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import main.GamePanel;

public class EnemyManager {
    GamePanel gp;
    Player player;
    // List for each enemy in play
    public ArrayList<Enemy> basicEnemies;
    public ArrayList<RangedEnemy> rangedEnemies;
    public ArrayList<Bullet> bullets;
    // We use this to keep track of how many frames have passed
    public int[] frameTick = new int[2];

    public int gameTime = 0;
    
    // Constructor where we define each enemy we want to add. 
    // This way it is easier to add a multitude of enemies.
    public EnemyManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        basicEnemies = new ArrayList<Enemy>();
        rangedEnemies = new ArrayList<RangedEnemy>();
        bullets = new ArrayList<Bullet>();
    }

    // The update void for the Enemy Manager. 
    public void update() {
        Enemy();
        RangedEnemy();
        BulletEnemy();
        
        // If the player is alive the enemy manager continues to spawn enemies

        if (player.life != 0) {
            Gameplay();
        }
    }

    // Draws each enemy from the lists
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

    // The void is used in order to increase the difficulty of the game with each second
    public void Gameplay() {
        if (gameTime / 60 <= 20) {
            SpawnEnemy(180 + (int) Math.random() * 60);
        } else if (gameTime / 60 <= 40) {
            SpawnEnemy(180 + (int) Math.random() * 60);
            SpawnRangedEnemy(390 + (int) Math.random() * 60);
        } else if (gameTime / 60 <= 60) {
            SpawnEnemy(150 + (int) Math.random() * 60);
            SpawnRangedEnemy(360 + (int) Math.random() * 60);
        }
        gameTime++;
    }

    // We use an iterator so that we can safely remove the enemy from the list.
    private void Enemy() {
        Iterator<Enemy> enemyIterator = basicEnemies.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it
            Enemy enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                enemyIterator.remove();
            }
        }
    }

    // We use an iterator so that we can safely remove the rangedEnemy from the list.
    private void RangedEnemy() {
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
    private void BulletEnemy() {
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
    private void SpawnRangedEnemy(int frames) {
        if (frameTick[1] > frames) {

            rangedEnemies.add(new RangedEnemy(gp, player));
            frameTick[1] = 0;
        }
        frameTick[1]++;
    }

    
    // Spawn enemies every x amount of frames
    private void SpawnEnemy(int frames) {
        if (frameTick[0] > frames) {

            basicEnemies.add(new Enemy(gp, player));
            frameTick[0] = 0;
        }
        frameTick[0]++;
    }

    // Spawn bullet
    public void SpawnBullet(Graphics2D g2 ,double x, double y) {
        bullets.add(new Bullet(gp, player, gp.getGraphics2d(), x, y));
    }
}
