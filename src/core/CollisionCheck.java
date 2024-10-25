package core;

import entity.GameObject;
import main.GamePanel;
import main.GameSettings;
import main.KeyHandler;

public class CollisionCheck {
    
    GamePanel gp;
    KeyHandler keyH;
    Vector2D vector2d;
    private GameSettings gameSettings = new GameSettings();

    public CollisionCheck(GamePanel gp, KeyHandler keyH, Vector2D vector2d) {
        this.gp = gp;
        this.keyH = keyH;
        this.vector2d = vector2d;
    }

    public void checkTile(GameObject gameObject) {
        double xSolidAreaOnScreenLeft = gameObject.worldX + gameObject.solidArea.x;
        double xSolidAreaOnScreenRight = xSolidAreaOnScreenLeft + gameObject.solidArea.width;
        double ySolidAreaOnScreenTop = gameObject.worldY + gameObject.solidArea.y;
        double ySolidAreaOnScreenBottom = ySolidAreaOnScreenTop + gameObject.solidArea.height;
 
        int solidAreaLeftCol = (int) xSolidAreaOnScreenLeft / gameSettings.getTileSize();
        int solidAreaRightCol = (int) xSolidAreaOnScreenRight / gameSettings.getTileSize();
        int solidAreaTopRow = (int) ySolidAreaOnScreenTop / gameSettings.getTileSize();
        int solidAreaBottomRow = (int) ySolidAreaOnScreenBottom / gameSettings.getTileSize();

        int tileToCheck1;
        int tileToCheck2;

        if (keyH.upPressed) {
            solidAreaTopRow = (int) (ySolidAreaOnScreenTop - gameObject.speed)
                             / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaTopRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
        if (keyH.downPressed) {
            solidAreaBottomRow = (int) (ySolidAreaOnScreenBottom + gameObject.speed)
                                 / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaBottomRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
        if (keyH.leftPressed) {
            solidAreaLeftCol = (int) (xSolidAreaOnScreenLeft - gameObject.speed)
                                 / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
        if (keyH.rightPressed) {
            solidAreaRightCol = (int) (xSolidAreaOnScreenRight + gameObject.speed)
                                 / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
    }
}
