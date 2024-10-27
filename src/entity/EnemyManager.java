package entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics2D;

import main.GamePanel;

public class EnemyManager {
    GamePanel gp;
    Player player;
    // List for each enemy in play
    public ArrayList<Enemy> basicEnemies;
    public ArrayList<RangedEnemy> rangedEnemies;
    public ArrayList<Bullet> bullets;
    public SoftbodyCollision collisions = new SoftbodyCollision(48);
    public GameObject tempObject;
    // We use this to keep track of how many frames have passed
    public int[] frameTick = new int[2];

    public int gameTime = 0;
    
    // Constructor where we define each enemy we want to add. This way it is easier to add a multitude of enemies.
    public EnemyManager(GamePanel gp, Player player)
    {
        this.gp = gp;
        this.player = player;
        basicEnemies = new ArrayList<Enemy>();
        rangedEnemies = new ArrayList<RangedEnemy>();
        bullets = new ArrayList<Bullet>();
    }

    // The update void for the Enemy Manager. We use an iterator so that we can safely remove the enemy from the list.
    public void update() {
        Enemy();
        RangedEnemy();
        BulletEnemy();
        
        if (player.life != 0) {
            Gameplay();
        } else {
            System.out.println("OH NO! YOU COULDN'T SURVIVE HELLMOND. YOU SURVIVED FOR ONLY " + (gameTime/60) + "SECONDS.");
            System.out.println( "BETTER LUCK NEXT TIME!");
        }


        System.out.println(collisions.otherObjects.size());
    }

    // Draws each enemy from the list
    public void draw(Graphics2D g2) {
        for(Enemy enemy : basicEnemies) {
            enemy.draw(g2);
        }
        for(RangedEnemy enemy : rangedEnemies) {
            enemy.draw(g2);
        }
        for(Bullet enemy : bullets) {
            enemy.draw(g2);
        }
    }

    public void Gameplay() {
        if (gameTime/60 <= 20) {
            SpawnEnemy(180 + (int) Math.random()*60);
        } else if (gameTime/60 <= 40) {
            SpawnEnemy(180 + (int) Math.random()*60);
            SpawnRangedEnemy(390 + (int) Math.random()*60);
        } else if (gameTime/60 <= 60) {
            SpawnEnemy(150 + (int) Math.random()*60);
            SpawnRangedEnemy(360 + (int) Math.random()*60);
        } else  if (player.life != 0) {
            System.out.println("CONGRATS YOU SURVIVED HELLMOND!");
        }
        gameTime++;
    }

    private void Enemy() {
        Iterator<Enemy> enemyIterator = basicEnemies.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it from the list (deletes it)
            Enemy enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                tempObject = enemy;
                collisions.removeSpecificObject(tempObject);
                enemyIterator.remove();
            }
        }
    }

    private void RangedEnemy() {
        Iterator<RangedEnemy> enemyIterator = rangedEnemies.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it from the list (deletes it)
            RangedEnemy enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                tempObject = enemy;
                collisions.removeSpecificObject(tempObject);
                enemyIterator.remove();
            }
        }
    }

    private void BulletEnemy() {
        Iterator<Bullet> enemyIterator = bullets.iterator();

        //It looks if it has an object in the list.
        while (enemyIterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it from the list (deletes it)
            Bullet enemy = enemyIterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                enemyIterator.remove();
            }
        }
    }

    // Spawn enemies every x amount of frames
    private void SpawnRangedEnemy(int frames) {
        if(frameTick[1] > frames) {

            rangedEnemies.add(new RangedEnemy(gp, player, collisions));
            collisions.otherObjects.add(rangedEnemies.getLast());
            frameTick[1] = 0;
        }
        frameTick[1]++;
    }

    
    // Spawn enemies every frames
    private void SpawnEnemy(int frames) {
        if(frameTick[0] > frames) {

            basicEnemies.add(new Enemy(gp, player, collisions));
            collisions.otherObjects.add(basicEnemies.getLast());
            frameTick[0] = 0;
        }
        frameTick[0]++;
    }

    // Spawn enemies every frames
    public void SpawnBullet(Graphics2D g2 ,double x, double y) {
        bullets.add(new Bullet(gp, player, gp.getGraphics2d(), x, y));
    }
}
