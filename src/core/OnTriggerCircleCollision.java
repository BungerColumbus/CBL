package core;

import entity.GameObject;
import main.GamePanel;

public class OnTriggerCircleCollision {
    
    GamePanel gp;
    Vector2D vector2d;
    public double radius;
    public boolean active;

    public OnTriggerCircleCollision(GamePanel gp, double distance, Vector2D vector2d) {
        this.gp = gp;
        this.radius = distance;
        this.vector2d = vector2d;
    }

    public boolean checkCollisionBetween2Objects(GameObject gameObject1, GameObject gameObject2, OnTriggerCircleCollision circleCollider1, OnTriggerCircleCollision circleCollider2) {
        boolean collided;
        Vector2D vector;
        vector = new Vector2D((gameObject2.worldX - gameObject1.worldX), (gameObject2.worldY - gameObject1.worldY));
        if(vector.length() < circleCollider1.radius + circleCollider2.radius) {
            collided = true;
        } else {
            collided = false;
        }

        //if(gameObject1.vector2d gameObject1.OnTriggerCircleCollision.distance)
        
        if(collided) {
            return true;
        } else {
            return false;
        }
    }
}
