package entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics2D;
import main.GamePanel;

public class EnemyManager {
    // List for each enemy in play
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    // We use this to keep track of how many frames have passed
    public int frameTick;
    // Constructor where we define each enemy we want to add. This way it is easier to add a multitude of enemies.
    public EnemyManager(GamePanel gp, Player player)
    {

    }

    // The update void for the Enemy Manager. We use an iterator so that we can safely remove the enemy from the list.
    public void update(GamePanel gp, Player player) {
        Iterator<Enemy> iterator = enemies.iterator();

        //It looks if it has an object in the list.
        while (iterator.hasNext()) {
            // It selects the next enemy, updates, and if not enough life it removes it from the list (deletes it)
            Enemy enemy = iterator.next();
            enemy.update();

            if (enemy.life <= 0) {
                iterator.remove();
            }
        }
        // Spawns an enemy every 120 frames (check void SpawnEnemy())
        SpawnEnemy(120, gp, player);
    }

    // Draws each enemy from the list
    public void draw(Graphics2D g2) {
        for(Enemy enemy : enemies) {
            enemy.draw(g2);
        }
    }

    // Spawn enemies every frames
    public void SpawnEnemy(int frames, GamePanel gp, Player player) {
        if(frameTick > frames) {
            enemies.add(new Enemy(gp, player));
            frameTick = 0;
        }
        frameTick++;
    }
    
}
