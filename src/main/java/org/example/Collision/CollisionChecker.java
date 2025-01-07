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

    // --- Coin Collision ---
    public void checkCoin(Player player) {
        int playerLeft = player.x;
        int playerRight = player.x + player.bounds.width;
        int playerTop = player.y;
        int playerBottom = player.y + player.bounds.height;

        int playerLeftCol = playerLeft / gp.tileSize;
        int playerRightCol = playerRight / gp.tileSize;
        int playerTopRow = playerTop / gp.tileSize;
        int playerBottomRow = playerBottom / gp.tileSize;

        checkAndCollectCoin(player, playerLeftCol, playerTopRow);
        checkAndCollectCoin(player, playerRightCol, playerTopRow);
        checkAndCollectCoin(player, playerLeftCol, playerBottomRow);
        checkAndCollectCoin(player, playerRightCol, playerBottomRow);
    }

    private void checkAndCollectCoin(Player player, int col, int row) {
        if (col >= 0 && col < tm.getMapData()[0].length && row >= 0 && row < tm.getMapData().length) {
            if (tm.getTile(row, col) == 3) { // 3 is the coin tile
                tm.getMapData()[row][col] = 2; // Replace with air tile (2 is air)
                player.incrementCoinCount();
            }
        }
    }

    // --- Spike Collision ---
    public void checkSpike(Player player) {
        int playerLeft = player.x;
        int playerRight = player.x + player.bounds.width;
        int playerTop = player.y;
        int playerBottom = player.y + player.bounds.height;

        int playerLeftCol = playerLeft / gp.tileSize;
        int playerRightCol = playerRight / gp.tileSize;
        int playerTopRow = playerTop / gp.tileSize;
        int playerBottomRow = playerBottom / gp.tileSize;

        checkAndHandleSpike(player, playerLeftCol, playerTopRow);
        checkAndHandleSpike(player, playerRightCol, playerTopRow);
        checkAndHandleSpike(player, playerLeftCol, playerBottomRow);
        checkAndHandleSpike(player, playerRightCol, playerBottomRow);
    }

    private void checkAndHandleSpike(Player player, int col, int row) {
        if (col >= 0 && col < tm.getMapData()[0].length && row >= 0 && row < tm.getMapData().length) {
            if (tm.getTile(row, col) == 4) { // 4 is the spike tile
                player.resetToDefault();
            }
        }
    }

    // --- Flag Collision ---
    public void checkFlag(Player player) {
        int playerLeft = player.x;
        int playerRight = player.x + player.bounds.width;
        int playerTop = player.y;
        int playerBottom = player.y + player.bounds.height;

        int playerLeftCol = playerLeft / gp.tileSize;
        int playerRightCol = playerRight / gp.tileSize;
        int playerTopRow = playerTop / gp.tileSize;
        int playerBottomRow = playerBottom / gp.tileSize;

        checkAndHandleFlag(player, playerLeftCol, playerTopRow);
        checkAndHandleFlag(player, playerRightCol, playerTopRow);
        checkAndHandleFlag(player, playerLeftCol, playerBottomRow);
        checkAndHandleFlag(player, playerRightCol, playerBottomRow);
    }

    private void checkAndHandleFlag(Player player, int col, int row) {
        if (col >= 0 && col < tm.getMapData()[0].length && row >= 0 && row < tm.getMapData().length) {
            if (tm.getTile(row, col) == 5) { // 5 is the flag tile
                gp.setGameWon(true); // Use gp to access GamePanel's setGameWon()
            }
        }
    }
}