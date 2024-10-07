package entity;

import java.awt.Graphics2D;

import core.Vector2D;

import java.awt.Color;

import main.GamePanel;
import main.KeyHandler;

public class Player extends GameObject {
    GamePanel gp;
    KeyHandler keyH;
    protected int speed = 4;
    protected Vector2D vector2d = new Vector2D(0, 0);

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
    }

    public void update() {
        int deltaX = 0;
        int deltaY = 0;

        if(keyH.upPressed == true) {
            deltaY--;
        }
        if(keyH.downPressed == true) {
            deltaY++;
        }
        if(keyH.leftPressed == true) {
            deltaX--;
        }
        if(keyH.rightPressed == true) {
            deltaX++;
        }

        vector2d = new Vector2D(deltaX, deltaY);
        vector2d.normalize();
        vector2d.multiply(speed);
        System.out.println("Player vector details: " + vector2d.getX() + " " + vector2d.getY() + " " + vector2d.length());
        x += vector2d.getX();
        y += vector2d.getY();
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.white);
        g2.fillRect((int) Math.round(x),(int) Math.round(y), gp.tileSize, gp.tileSize);
    }
}
