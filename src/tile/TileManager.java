package tile;

import main.GamePanel;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.imageio.*;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[12];
        mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];

        loadMap();
        getTileImage();
    }

    public void getTileImage() {
        try {
            int x;
            for(x = 0; x <= 11; x++) {
                tile[x] = new Tile();
            }
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet8.png"));
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet6.png"));
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet5.png"));
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet9.png"));
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet2.png"));
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet3.png"));
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet4.png"));
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet7.png"));
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet10.png"));
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet11.png"));
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet12.png"));
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/TileSet1.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/res/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
            int col = 0;
            int row = 0;
    
            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while(col <  gp.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void draw (Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNumber[col][row];
            
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
