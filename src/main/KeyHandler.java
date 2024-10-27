package main;

import core.Vector2D;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KeyHandler extends MouseAdapter implements KeyListener {

    // All the key and mouse variables
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean clickedLeftButton;
    public boolean dashed;
    public double mouseX = 0;
    public double mouseY = 0;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // if key x is pressed boolean y is true
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        } 
        if (code == KeyEvent.VK_SPACE) {
            dashed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // if key x is released boolean y is false
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == MouseEvent.MOUSE_RELEASED) {
            clickedLeftButton = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            dashed = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickedLeftButton = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clickedLeftButton = false;
        }
    }

    // Mouse screen position returned as a vector
    public Vector2D mousePosition() {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        return new Vector2D(mouseX, mouseY);
    }
    
}
