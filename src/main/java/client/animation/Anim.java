package client.animation;

import java.awt.*;

public abstract class Anim {
    protected int originX, originY, destinationX, destinationY;
    protected Anim painter;
    protected Speed speed;
    protected boolean active = false;
    protected int frame;
    protected int width;
    protected  int height;
    protected int x;
    protected int y;

    public abstract void paint(Graphics2D graphics2D, int frame);

    abstract int getWidth();

    abstract int getHeight();

    public abstract boolean isActive();

    public void addFrame() {
        frame++;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
