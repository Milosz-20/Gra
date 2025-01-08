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

    public boolean hasHorizontalCollision(Entity e, int dx) {
        int newX = e.x + dx;
        int topTile = e.y / gp.tileSize;
        int bottomTile = (e.y + e.bounds.height - 1) / gp.tileSize;

        if (dx > 0) {
            int rightTile = (newX + e.bounds.width - 1) / gp.tileSize;
            return isTileCollidable(rightTile, topTile) || isTileCollidable(rightTile, bottomTile);
        } else if (dx < 0) {
            int leftTile = newX / gp.tileSize;
            return isTileCollidable(leftTile, topTile) || isTileCollidable(leftTile, bottomTile);
        }

        return false;
    }

    public boolean hasVerticalCollision(Entity e, int dy) {
        int newY = e.y + dy;
        int leftTile = e.x / gp.tileSize;
        int rightTile = (e.x + e.bounds.width - 1) / gp.tileSize;

        if (dy > 0) {
            int bottomTile = (newY + e.bounds.height - 1) / gp.tileSize;
            return isTileCollidable(leftTile, bottomTile) || isTileCollidable(rightTile, bottomTile);
        } else if (dy < 0) {
            int topTile = newY / gp.tileSize;
            return isTileCollidable(leftTile, topTile) || isTileCollidable(rightTile, topTile);
        }

        return false;
    }

    private boolean isTileCollidable(int tileX, int tileY) {
        int[][] mapData = tm.getMapData();
        if (tileX < 0 || tileX >= mapData[0].length || tileY < 0 || tileY >= mapData.length) {
            return true;
        }
        return tm.tile[tm.getTile(tileY, tileX)].collision;
    }

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
            if (tm.getTile(row, col) == 3) {
                tm.getMapData()[row][col] = 2;
                player.incrementCoinCount();
            }
        }
    }

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
            if (tm.getTile(row, col) == 4) {
                player.resetToDefault();
            }
        }
    }

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
            if (tm.getTile(row, col) == 5) {
                gp.setGameWon(true);
            }
        }
    }
}