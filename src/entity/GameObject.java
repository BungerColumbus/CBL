package entity;

public class GameObject {
    
    protected double x;
    protected double y;

    protected int animationTick; 
    protected int animationIndex;

    public void updateAnimation(int frame, int length, int animationSpeed) {
        if(animationIndex < frame || animationIndex > length) {
            animationIndex = frame;
        }
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= length) {
                animationIndex = frame;
            }
        }
    }

}
