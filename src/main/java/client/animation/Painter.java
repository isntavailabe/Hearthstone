package client.animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Painter extends Anim {
    private BufferedImage image;


    public Painter(BufferedImage image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics2D graphics2D, int frame) {
        graphics2D.drawImage(image, x,y, width, height, null);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
