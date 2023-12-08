package group2.tile;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import group2.GamePanel;
import group2.Board;


/**
 * <h1> TileManager </h1>
 * This class is responsible for assigning images for each tile on the 2D board generated from the board class
 */
public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public static int[][] mapTileNum;
    public Board board;
    /**
     * This constructor for TileManager initializes all the variables and calls the map generation from the board class
     * @param gp This is the game panel class that contains all the data related to the game
     * @param board This is the board class that contains all teh data related to the board
     */
    public TileManager(GamePanel gp, Board board) {

        this.gp = gp;
        this.board = board;
        tile = new Tile[10];
        // put all the numbers from txt
        this.board.updateTerrain();
        mapTileNum = board.getMap();
        getTileImage();

    }
    /**
     * This returns the value of the index in the 2D map grid
     * @param x column of grid
     * @param y row of grid
     * @return int
     */
    public static int checkTile (int x, int y) {
        return mapTileNum[x][y];
    }

    /**
     * This method assignms an image to the tile
     */
    public void getTileImage() {

        try {
            // road
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("game/resources/tiles/road00.png"));

            // wall
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("game/resources/tiles/wall.png"));
            tile[1].collision = true;
            // grass
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("game/resources/tiles/grass01.png"));

            //end
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(new File("game/resources/tiles/end.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method loads the map into the mapTileNum 2D array from the generated map from the board class
     * @param map
     */
    public int[][] getMap(){
        return mapTileNum;
    }
    /**
     * draw function that paints all the tiles by looping through the 2D grid and assigning an image to the tile depending on the value of the item
     * @param g2
     */
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {

            int tileNum = mapTileNum[worldCol][worldRow]; // gives int

            int worldX = worldCol * gp.TILE_SIZE;
            int worldY = worldRow * gp.TILE_SIZE;
            int screenX = worldX + gp.mainCharacter.mcX - gp.mainCharacter.positionX;
            int screenY = worldY + gp.mainCharacter.mcY - gp.mainCharacter.positionY;
            if (tileNum > 2 && tileNum != 9){
                tileNum = 0;
            }
            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            worldCol++;

            if (worldCol == gp.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}