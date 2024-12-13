package org.example;

import org.example.Collision.CollisionChecker;
import org.example.Entities.Enemy;
import org.example.Entities.Player;
import org.example.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 432 pixels
    final int FPS = 60;

    // Tile manager and collision checker (make sure these are initialized correctly)
    TileManager tileM = new TileManager(this);
    public CollisionChecker cc = new CollisionChecker(this, tileM);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public Player player = new Player(this, keyH, tileM, cc);
    //Enemy enemy1 = new Enemy(this); // You'll need to update Enemy class for camera

    // Camera position
    public int cameraX = 0;
    public int cameraY = 0;

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
        //enemy1.update(); // Update enemy logic with camera in mind
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw tiles (with camera offset)
        tileM.draw(g2);

        // Draw player (with camera offset)
        player.draw(g2);

        // Draw enemy (with camera offset)
        //enemy1.draw(g2); // Update enemy draw method

        g2.dispose();
    }

    // You can remove the drawGrid method if you don't need it anymore.
}