package main;

import entity.GameObject;
import main.KeyHandler;
import tile.TileManager;

public class CollisionCheck {
    
    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(GameObject gameObject) {
        double xSolidAreaOnScreen = gameObject.worldX + gameObject.solidArea.x;
        double xSolidAreaOnScreenRight = xSolidAreaOnScreen + gameObject.solidArea.width;
        double ySolidAreaOnScreen = gameObject.worldY + gameObject.solidArea.y;
        double ySolidAreaOnScreenBottom = ySolidAreaOnScreen + gameObject.solidArea.height;

        int solidAreaLeftCol = (int) xSolidAreaOnScreen / gp.tileSize;
        int solidAreaRightCol = (int) xSolidAreaOnScreenRight / gp.tileSize;
        int solidAreaTopRow = (int) ySolidAreaOnScreen / gp.tileSize;
        int solidAreaBottomRow = (int) ySolidAreaOnScreenBottom / gp.tileSize;

        int tileToCheck1;
        int tileToCheck2;

        if (KeyHandler.upPressed) {
            solidAreaTopRow = (int) (ySolidAreaOnScreen - gameObject.speed) / gp.tileSize;
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaTopRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
        if (KeyHandler.downPressed) {
            solidAreaBottomRow = (int) (ySolidAreaOnScreenBottom + gameObject.speed) / gp.tileSize;
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaBottomRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
        if (KeyHandler.leftPressed) {
            solidAreaLeftCol = (int) (xSolidAreaOnScreen - gameObject.speed) / gp.tileSize;
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaLeftCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
        if (KeyHandler.rightPressed) {
            solidAreaRightCol = (int) (xSolidAreaOnScreenRight + gameObject.speed) / gp.tileSize;
            tileToCheck1 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaTopRow];
            tileToCheck2 = gp.tileManager.mapTileNumber[solidAreaRightCol][solidAreaBottomRow];
            
            if (gp.tileManager.tile[tileToCheck1].collision 
                || gp.tileManager.tile[tileToCheck2].collision) {
                gameObject.collisionOn = true;
            }
        }
    }
}
