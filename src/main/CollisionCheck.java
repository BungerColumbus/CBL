package main;

import entity.GameObject;

public class CollisionCheck {
    
    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(GameObject gameObject) {
        double xSolidAreaOnScreen = gameObject.worldX + gameObject.solidArea.x;
        double xSolidAreaOnScreenRight = xSolidAreaOnScreen + gameObject.solidArea.width;
        double ySolidAreaOnScreen = gameObject.worldY + gameObject.solidArea.y;
        double ySolidAreaOnScreenRight = ySolidAreaOnScreen + gameObject.solidArea.height;

        int solidAreaLeftCol = (int) xSolidAreaOnScreen / gp.tileSize;
        int solidAreaRightCol = (int) xSolidAreaOnScreenRight / gp.tileSize;
        int solidAreaTopRow = (int) ySolidAreaOnScreen / gp.tileSize;
        int solidAreaBottomRow = (int) ySolidAreaOnScreenRight / gp.tileSize;

    }
}
