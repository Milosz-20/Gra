package org.example.Collision;

import org.example.Entities.Entity;
import org.example.Entities.Player;
import org.example.GamePanel;
import org.example.tile.TileManager;

public class CollisionChecker {
    GamePanel gp;
    TileManager tm;

    public CollisionChecker(GamePanel gp, TileManager tm) {
        this.gp = gp;
        this.tm = tm;
    }

    // Check for horizontal collisions (left and right)
    public boolean hasHorizontalCollision(Entity e, int dx) {
        int newX = e.x + dx;
        int topTile = e.y / gp.tileSize;
        int bottomTile = (e.y + e.bounds.height - 1) / gp.tileSize;

        if (dx > 0) { // Moving right
            int rightTile = (newX + e.bounds.width - 1) / gp.tileSize;
            return isTileCollidable(rightTile, topTile) || isTileCollidable(rightTile, bottomTile);
        } else if (dx < 0) { // Moving left
            int leftTile = newX / gp.tileSize;
            return isTileCollidable(leftTile, topTile) || isTileCollidable(leftTile, bottomTile);
        }

        return false; // No horizontal movement
    }

    // Check for vertical collisions (up and down)
    public boolean hasVerticalCollision(Entity e, int dy) {
        int newY = e.y + dy;
        int leftTile = e.x / gp.tileSize;
        int rightTile = (e.x + e.bounds.width - 1) / gp.tileSize;

        if (dy > 0) { // Moving down
            int bottomTile = (newY + e.bounds.height - 1) / gp.tileSize;
            return isTileCollidable(leftTile, bottomTile) || isTileCollidable(rightTile, bottomTile);
        } else if (dy < 0) { // Moving up
            int topTile = newY / gp.tileSize;
            return isTileCollidable(leftTile, topTile) || isTileCollidable(rightTile, topTile);
        }

        return false; // No vertical movement
    }

    private boolean isTileCollidable(int tileX, int tileY) {
        int[][] mapData = tm.getMapData();
        if (tileX < 0 || tileX >= mapData[0].length || tileY < 0 || tileY >= mapData.length) {
            return true; // Out of bounds tiles are considered collidable
        }
        return tm.tile[tm.getTile(tileY, tileX)].collision;
    }
}