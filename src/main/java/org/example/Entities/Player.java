package org.example.Entities;

import org.example.Collision.CollisionChecker;
import org.example.GamePanel;
import org.example.KeyHandler;
import org.example.tile.TileManager;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    TileManager tm;
    CollisionChecker cc;
    int speed;
    int fallSpeed = 0;
    final int gravity = 1;
    final int jumpStrength = -15;  // Stała siła skoku
    boolean onGround = false;

    int[] speeds = {1, 2, 3, 4, 6, 8, 12, 16, 24, 48};

    public Player(GamePanel gp, KeyHandler keyH, TileManager tm, CollisionChecker cc) {
        this.keyH = keyH;
        this.gp = gp;
        this.tm = tm;
        this.cc = cc;
        bounds = new Rectangle(0, 0, 48, 48);
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 96;
        y = 96;
        speed = speeds[4];
        direction = "right";
    }

    public void update() {
        int dx = 0;
        int dy = 0;

        // Ruch w lewo i prawo
        if (keyH.leftPressed) {
            dx -= speed;
            direction = "left";
        }
        if (keyH.rightPressed) {
            dx += speed;
            direction = "right";
        }

        // Obsługa grawitacji
        if (!onGround) {
            fallSpeed += gravity;
            dy += fallSpeed;
        } else {
            fallSpeed = 0;
        }

        // Logika skoku
        if (keyH.spacePressed && onGround) {
            fallSpeed = jumpStrength;
            onGround = false;
        }

        // Sprawdzamy kolizje i aktualizujemy pozycję
        if (!cc.hasCollision(this, dx, dy)) {
            x += dx;
            y += dy;
        } else {
            if (dy > 0) {
                while (!cc.hasCollision(this, 0, 1)) {
                    y++;
                }
                onGround = true;
                fallSpeed = 0;
            }
        }
    }

    public void draw(Graphics g2) {
        g2.setColor(Color.black);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
