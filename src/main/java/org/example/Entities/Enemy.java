package org.example.Entities;

import org.example.GamePanel;
import org.example.KeyHandler;

import java.awt.*;

public class Enemy extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Enemy(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
    }

    @Override
    public void setDefaultValues(){
        x = 600;
        y = 300;
        speed = 4;
    }
    public void update(){

    }
    public void draw(Graphics g2){
        g2.setColor(Color.red);
        g2.fillRect((int) x, (int) y, gp.tileSize, gp.tileSize);
    }
}
