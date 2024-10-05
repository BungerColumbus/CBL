package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
    
    //Settings for the screen ()

    //the default size for sprites, tiles etc
    final int originalTileSize = 16;

    //since today a computer has a bigger resolution
    //than older ones we must scale the game so that
    //the sprites won't be extremly small
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 18;
    final int maxScreenRow = 12;
    final int screenHeight = tileSize * maxScreenRow;
    final int screenWidth = tileSize * maxScreenCol;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
    }

}
