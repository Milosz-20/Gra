package org.example;

import org.example.Collision.CollisionChecker;
import org.example.Entities.Enemy;
import org.example.Entities.Player;
import org.example.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    public CollisionChecker cc = new CollisionChecker(this, tileM);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public Player player = new Player(this, keyH, tileM, cc);
    //Enemy enemy1 = new Enemy(this);

    public int cameraX = 0;
    public int cameraY = 0;

    private boolean gameWon = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
        //enemy1.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);
        //enemy1.draw(g2);

        if (gameWon) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 72));
            String text = "You Won!";
            int x = getXForCenteredText(text, g2);
            int y = tileSize * 4;
            g2.drawString(text, x, y);

            g2.setFont(new Font("Arial", Font.BOLD, 48));
            String score = "Coins: " + player.getCoinCount();
            x = getXForCenteredText(score, g2);
            y += tileSize * 2;
            g2.drawString(score, x, y);

            gameThread = null; // Stop the game loop
        }

        g2.dispose();
    }

    public int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return screenWidth / 2 - length / 2;
    }

    // Getter and setter for gameWon
    public void setGameWon(boolean won) {
        this.gameWon = won;
    }

    public boolean isGameWon() {
        return gameWon;
    }
}