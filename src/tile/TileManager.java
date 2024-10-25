package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameSettings;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNumber;
    private final int fullMapCol;
    private final int fullMapRow;
    private GameSettings gameSettings = new GameSettings();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[21];
        fullMapCol = gameSettings.getMaxWorldCol() + 18;
        fullMapRow = gameSettings.getMaxWorldRow() + 12;
        mapTileNumber = new int[fullMapCol][fullMapRow];

        loadMap();
        getTileImage();
    }

    public void getTileImage() {
        try {
            int x;
            for (x = 0; x < 21; x++) {
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

            tile[12].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterDLCorner.png"));  
            tile[13].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterDown.png"));  
            tile[14].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterDRCorner.png"));    
            tile[15].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterLeft.png"));
            tile[16].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterRight.png"));   
            tile[17].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterULCorner.png"));
            tile[18].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterUp.png"));
            tile[19].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/OuterURCorner.png"));
            tile[20].image = ImageIO.read(getClass()
                            .getResourceAsStream("/res/tiles/Waves.png"));
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
    
            while (col < fullMapCol && row < fullMapRow) {
                String line = br.readLine();

                while (col <  fullMapCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }
                if (col == fullMapCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < fullMapCol && worldRow < fullMapRow) {
            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gameSettings.getTileSize();
            int worldY = worldRow * gameSettings.getTileSize();
            int screenX = (int) Math.round(worldX - gp.player.worldX + gp.player.screenX);
            int screenY = (int) Math.round(worldY - gp.player.worldY + gp.player.screenY);
            
            if (worldX + gameSettings.getTileSize() > gp.player.worldX - gp.player.screenX 
                && worldX - gameSettings.getTileSize() < gp.player.worldX + gp.player.screenX
                && worldY + gameSettings.getTileSize() > gp.player.worldY - gp.player.screenY
                && worldY - gameSettings.getTileSize() < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gameSettings.getTileSize(),
                             gameSettings.getTileSize(), null);
            }
            worldCol++;

            if (worldCol == fullMapCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
