package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.*;
import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNumber;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[12];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];

        loadMap();
        getTileImage();
    }

    public void getTileImage() {
        try {
            int x;
            for (x = 0; x <= 11; x++) {
                tile[x] = new Tile();
                if (x >= 4) {
                    tile[x].collision = true;
                }
            }
            tile[0].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet8.png"));
            tile[1].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet6.png"));
            tile[2].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet5.png"));
            tile[3].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet9.png"));

            tile[4].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet2.png"));
            tile[5].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet3.png"));
            tile[6].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet4.png"));
            tile[7].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet7.png"));
            tile[8].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet10.png"));
            tile[9].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet11.png"));
            tile[10].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet12.png"));
            tile[11].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/TileSet1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/res/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
            int col = 0;
            int row = 0;
    
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col <  gp.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldRow) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = (int) Math.round(worldX - gp.player.worldX + gp.player.screenX);
            int screenY = (int) Math.round(worldY - gp.player.worldY + gp.player.screenY);
            
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
