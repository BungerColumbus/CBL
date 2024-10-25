package main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GameSettings {
    
    //Current device settings
    private final Dimension size;
    private final int defaultWidth;
    private final int monitorWidth;
    private int resize;

    //the default size for sprites, tiles etc
    private final int originalTileSize;


    //Temp screen settings
    private final int scale;
    private final int tileSize;
    private final int maxScreenCol;
    private final int maxScreenRow;
    private final int screenHeight;
    private final int screenWidth;

    //Actual window size
    public int screenWidth2;
    public int screenHeight2;

    //WORLD SETTINGS
    public final int maxWorldCol;
    public final int maxWorldRow;
    public final int worldWidth;
    public final int worldHeight;

    public GameSettings() {
        size = Toolkit.getDefaultToolkit().getScreenSize();
        defaultWidth = 1536;
        monitorWidth = (int) (size.getWidth());
        resize = Math.round((float) monitorWidth / defaultWidth);

        //the default size for sprites, tiles etc
        originalTileSize = 16;


        //Temp screen settings
        scale = 3;
        tileSize = originalTileSize * scale;
        maxScreenCol = 18;
        maxScreenRow = 12;
        screenWidth = tileSize * maxScreenCol;
        screenHeight = tileSize * maxScreenRow;

        //Actual window size
        screenWidth2 = screenWidth * resize;
        screenHeight2 = screenHeight * resize;
        

        //World settings
        maxWorldCol = 30;
        maxWorldRow = 30;
        worldWidth = tileSize * maxWorldCol;
        worldHeight = tileSize * maxWorldCol;

    }

    public int getResize() {
        return this.resize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth2() {
        return screenWidth2;
    }

    public int getScreenHeight2() {
        return screenHeight2;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }
}
