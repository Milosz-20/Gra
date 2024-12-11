package org.example.Collision;

import org.example.Entities.Entity;
import org.example.GamePanel;
import org.example.tile.TileManager;

public class CollisionChecker {
    GamePanel gp;
    TileManager tm;

    public CollisionChecker(GamePanel gp, TileManager tm) {
        this.gp = gp;
        this.tm = tm;
    }

    public boolean hasCollision(Entity e, int dx, int dy) {
        int newX = e.x + dx;
        int newY = e.y + dy;

        // Calculate the corners of the entity's bounding box
        int leftTile = newX / gp.tileSize;
        int rightTile = (newX + e.bounds.width - 1) / gp.tileSize;
        int topTile = newY / gp.tileSize;
        int bottomTile = (newY + e.bounds.height - 1) / gp.tileSize;

        // Check each corner for collision
        if (isTileCollidable(leftTile, topTile) || isTileCollidable(rightTile, topTile) ||
                isTileCollidable(leftTile, bottomTile) || isTileCollidable(rightTile, bottomTile)) {
            return true;
        }

        return false;
    }

    private boolean isTileCollidable(int tileX, int tileY) {
        int[][] mapData = tm.getMapData();
        if (tileX < 0 || tileX >= mapData[0].length || tileY < 0 || tileY >= mapData.length) {
            return true; // Out of bounds tiles are considered collidable
        }
        return tm.tile[tm.getTile(tileY, tileX)].collision;
    }
}