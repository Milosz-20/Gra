package org.example;

import org.example.Collision.CollisionChecker;
import org.example.Entities.Enemy;
import org.example.Entities.Player;
import org.example.tile.TileManager;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int fps = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    TileManager tm = new TileManager(this);
    CollisionChecker cc = new CollisionChecker(this, tm);
    Thread gameThread;

    Player player = new Player(this, keyH, tm, cc);
    Enemy enemy1 = new Enemy(this);


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
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta = 0;
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
//      drawGrid(g);
        g2.dispose();
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.black);
        for(int i = 0; i < maxScreenCol; i++){
            g.drawLine(i * tileSize, 0, i * tileSize, screenHeight);
        }
        for(int i = 0; i < maxScreenRow; i++){
            g.drawLine(0, i * tileSize, screenWidth, i * tileSize);
        }
    }
}
