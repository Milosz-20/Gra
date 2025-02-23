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
    final int jumpStrength = -15;
    boolean onGround = false;
    String direction = "down";

    public int screenX;
    public int screenY;

    int[] speeds = {1, 2, 3, 4, 6, 8, 12, 16, 24, 48};

    boolean isDashing = false;
    int dashSpeedIndex = 6; // Index for dash speed in speeds array
    int dashDuration = 15;
    int dashDistance = 96;
    int dashFrames = 0;
    int dashCounter = 0;
    boolean canDash = true;
    int dashCooldown = 0;
    final int dashCooldownTime = 60;

    private int coinCount = 0;
    private int defaultX, defaultY;


    public Player(GamePanel gp, KeyHandler keyH, TileManager tm, CollisionChecker cc) {
        this.gp = gp;
        this.keyH = keyH;
        this.tm = tm;
        this.cc = cc;
        bounds = new Rectangle(0, 0, 48, 48);
        setDefaultValues();

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    }

    @Override
    public void setDefaultValues() {
        defaultX = 300; // Set the default spawn x
        defaultY = 596; // Set the default spawn y
        x = defaultX;
        y = defaultY;
        speed = speeds[4];
        coinCount = 0;
    }

    public void update() {
        if (gp.isGameWon()) {
            return;
        }

        int dx = 0;

        // Handle dash cooldown
        if (dashCooldown > 0) {
            dashCooldown--;
        }

        // Handle dashing
        if (keyH.oPressed && canDash && !isDashing && dashCooldown == 0) {
            isDashing = true;
            canDash = false;
            dashFrames = dashDuration;
            dashCounter = 0;
            dashCooldown = dashCooldownTime;
        }

        if (isDashing) {
            performDash();
        } else {
            if (keyH.leftPressed && !keyH.rightPressed) {
                dx -= speed;
            } else if (keyH.rightPressed && !keyH.leftPressed) {
                dx += speed;
            }
            if (!cc.hasHorizontalCollision(this, dx)) {
                x += dx;
            }
        }

        if (keyH.upPressed && keyH.leftPressed) {
            direction = "topleft";
        } else if (keyH.upPressed && keyH.rightPressed) {
            direction = "topright";
        } else if (keyH.downPressed && keyH.leftPressed) {
            direction = "downleft";
        } else if (keyH.downPressed && keyH.rightPressed) {
            direction = "downright";
        } else if (keyH.upPressed) {
            direction = "top";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        }

        if (!isDashing) {
            if (!onGround) {
                fallSpeed += gravity;
            }

            if (cc.hasVerticalCollision(this, fallSpeed)) {
                if (fallSpeed > 0) {
                    while (!cc.hasVerticalCollision(this, 1)) {
                        y++;
                    }
                    onGround = true;
                    fallSpeed = 0;
                } else if (fallSpeed < 0) {
                    fallSpeed = 0;
                }
            } else {
                y += fallSpeed;
                onGround = false;
            }

            if (keyH.spacePressed && onGround) {
                fallSpeed = jumpStrength;
                onGround = false;
            }
        }

        if (onGround) {
            canDash = true;
        }

        cc.checkCoin(this);
        cc.checkSpike(this);
        cc.checkFlag(this);
    }

    private void performDash() {
        fallSpeed = 0;
        int dashSpeed = speeds[dashSpeedIndex];
        int dx = 0, dy = 0;

        switch (direction) {
            case "topleft":
                dx = (int) Math.round(-dashSpeed / Math.sqrt(2));
                dy = (int) Math.round(-dashSpeed / Math.sqrt(2));
                break;
            case "topright":
                dx = (int) Math.round(dashSpeed / Math.sqrt(2));
                dy = (int) Math.round(-dashSpeed / Math.sqrt(2));
                break;
            case "downleft":
                dx = (int) Math.round(-dashSpeed / Math.sqrt(2));
                dy = (int) Math.round(dashSpeed / Math.sqrt(2));
                break;
            case "downright":
                dx = (int) Math.round(dashSpeed / Math.sqrt(2));
                dy = (int) Math.round(dashSpeed / Math.sqrt(2));
                break;
            case "top":
                dy = -dashSpeed;
                break;
            case "down":
                dy = dashSpeed;
                break;
            case "left":
                dx = -dashSpeed;
                break;
            case "right":
                dx = dashSpeed;
                break;
        }

        int distanceToMove = Math.min(dashSpeed, dashDistance - dashCounter);
        if (!cc.hasHorizontalCollision(this, dx)) {
            if (Math.abs(dx) <= distanceToMove) {
                x += dx;
            } else {
                x += dx > 0 ? distanceToMove : -distanceToMove;
            }
        }
        if (!cc.hasVerticalCollision(this, dy)) {
            if (Math.abs(dy) <= distanceToMove) {
                y += dy;
            } else {
                y += dy > 0 ? distanceToMove : -distanceToMove;
            }
        }

        dashCounter += Math.max(Math.abs(dx), Math.abs(dy));
        dashFrames--;

        if (dashFrames <= 0 || dashCounter >= dashDistance) {
            isDashing = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);

        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Coins: " + coinCount, 10, 30);
    }

    public void incrementCoinCount() {
        coinCount++;
    }

    public void resetToDefault() {
        x = defaultX;
        y = defaultY;
        fallSpeed = 0;
        isDashing = false;
        onGround = false;
        direction = "down";
    }
    public int getCoinCount() {
        return coinCount;
    }
}