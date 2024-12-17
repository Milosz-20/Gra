package org.example.Entities;

import java.awt.*;

public class Entity {
    public int x, y;
    public int speed;
    public Rectangle bounds;
    public String direction = "";
    public boolean collisionOn = false;

    public void setDefaultValues(){
        x = 200;
        y = 400;
    }
}
