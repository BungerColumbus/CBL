package core;

import main.GamePanel;

public class OnTriggerCircleCollision {
    
    GamePanel gp;
    Vector2D vector2d;
    public double radius;
    public boolean active = true;

    public OnTriggerCircleCollision(GamePanel gp, double distance, Vector2D vector2d) {
        this.gp = gp;
        this.radius = distance;
        this.vector2d = vector2d;
    }

    public boolean checkCollisionBetween2Objects(double gameObject1X, double gameObject2X, double gameObject1Y, double gameObject2Y, OnTriggerCircleCollision circleCollider1, OnTriggerCircleCollision circleCollider2) {
        boolean collided;
        Vector2D vector;
        vector = new Vector2D((gameObject1X - gameObject2X), (gameObject1Y - gameObject2Y));
        if(vector.length() < (circleCollider1.radius + circleCollider2.radius)) {
            collided = true;
        } else {
            collided = false;
        }

        if(circleCollider2.active && collided) {
            return true;
        } else {
            return false;
        }
    }
}
