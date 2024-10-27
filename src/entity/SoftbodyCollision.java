package entity;

import java.util.ArrayList;
import java.util.Iterator;

import core.Vector2D;

public class SoftbodyCollision {
    
    public boolean active;
    public ArrayList<GameObject> otherObjects;
    double range;

    public SoftbodyCollision(double range) {
        this.range = range;
        active = false;
        otherObjects = new ArrayList<GameObject>();
    }

    public void removeSpecificObject(GameObject specificObject) {
        Iterator<GameObject> objectsIterator = otherObjects.iterator();
        
        while(objectsIterator.hasNext()) {
            GameObject object = objectsIterator.next();

            if (object == specificObject) {
                objectsIterator.remove();
            }
        }
    }

    public void softCollision(GameObject mainObject) {
        Iterator<GameObject> objectsIterator = otherObjects.iterator();
        mainObject.softCollisionVector = new Vector2D(0, 0);
        while(objectsIterator.hasNext()) {
            GameObject object = objectsIterator.next();
            double distance = new Vector2D(object.worldX - mainObject.worldX, 
                                            object.worldY - mainObject.worldY).length();
            if (object != mainObject && distance < range) {
                mainObject.softCollisionVector = 
                new Vector2D(mainObject.softCollisionVector.getX() + mainObject.worldX - object.worldX, 
                            mainObject.softCollisionVector.getY() + mainObject.worldY - object.worldY);
                mainObject.softCollisionVector.normalize();
                mainObject.softCollisionVector.multiply((48-distance)/24);            
            }
        }
        
    }

}
