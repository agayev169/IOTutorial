package MiniProject;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    int x;
    int y;
    Color color = Color.RED;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public abstract void show(Graphics g);

    public abstract void stroke(Graphics g);

    public abstract boolean isInside(int x, int y);
}
