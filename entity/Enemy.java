package entity;

import java.awt.Graphics2D;

import core.Vector2D;

import java.awt.Color;

import main.GamePanel;

public class Enemy extends GameObject {
    GamePanel gp;
    Player player;
    protected int speed = 2;
    protected Vector2D vector2d = new Vector2D(0, 0);

    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 500;
        y = 500;
    }

    public void update() {

        vector2d = new Vector2D(player.x-x, player.y-y);
        vector2d.normalize();
        vector2d.multiply(speed);
        System.out.println("Enemy vector info: " + vector2d.getX() + " " + vector2d.getY() + " " + vector2d.length());
        x += vector2d.getX();
        y += vector2d.getY();
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.red);
        g2.fillRect((int) Math.round(x),(int) Math.round(y), gp.tileSize, gp.tileSize);
    }
}
