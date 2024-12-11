package org.example.tile;

import org.example.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    private static int[][] mapData = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[16];
        getTileImage();
    }

    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass1.png")));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water1.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sky1.png")));
        } catch (IOException e) {e.printStackTrace();}
    }

    public int getTile(int row, int col) {
        if(row >= 0 && row <= mapData.length && col >= 0 && col < mapData[row].length) {
            return mapData[row][col];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int[][] getMapData() {
        return mapData;
    }

    public void draw(Graphics2D g2) {
        for(int row = 0; row < mapData.length; row++) {
            for(int col = 0; col < mapData[row].length; col++) {
                g2.drawImage(tile[mapData[row][col]].image, gp.tileSize * col, gp.tileSize * row, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}