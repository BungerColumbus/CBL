package core;

import entity.GameObject;
import main.GamePanel;
import main.GameSettings;
import main.KeyHandler;

public class CollisionCheck {
    
    public GamePanel gp;
    private KeyHandler keyH;
    private GameSettings gameSettings = new GameSettings();

    public CollisionCheck(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
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

        int errorCorrection = 0;

        if (keyH.upPressed) {
            solidAreaTopRow = (int) (ySolidAreaOnScreenTop - gameObject.speed)
                             / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaTopRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionVertical = true;
                errorCorrection = 1;
            }
        }
        if (keyH.downPressed) {
            solidAreaBottomRow = (int) (ySolidAreaOnScreenBottom + gameObject.speed)
                                 / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaBottomRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionVertical = true;
                errorCorrection = -1;
            }
        }
        if (keyH.leftPressed) {
            solidAreaLeftCol = (int) (xSolidAreaOnScreenLeft - gameObject.speed)
                                 / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber
                            [solidAreaLeftCol][solidAreaTopRow + errorCorrection];
            tileToCheck2 = gp.tileManager.mapTileNumber
                            [solidAreaLeftCol][solidAreaBottomRow + errorCorrection];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionHorizontal = true;
            }
        }
        if (keyH.rightPressed) {
            solidAreaRightCol = (int) (xSolidAreaOnScreenRight + gameObject.speed)
                                 / gameSettings.getTileSize();
            tileToCheck1 = gp.tileManager.mapTileNumber
                            [solidAreaRightCol][solidAreaTopRow + errorCorrection];
            tileToCheck2 = gp.tileManager.mapTileNumber
                            [solidAreaRightCol][solidAreaBottomRow + errorCorrection];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionHorizontal = true;
            }
        }
    }
}
