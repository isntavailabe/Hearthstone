package client.animation;

import java.awt.*;

public class Rotate extends Anim {
    private final Anim painter;

    public Rotate(Anim painter) {
        this.painter = painter;
    }

    @Override
    public void paint(Graphics2D graphics2D, int frame) {
        double second = (frame % 60.0)/60.0;
        double rotationRequired = 2 * Math.PI * (second);
        double locationX = painter.getWidth() / 2.0;
        double locationY = painter.getHeight() / 2.0;
        graphics2D.rotate(rotationRequired,locationX,locationY);
        painter.paint(graphics2D, frame);
        graphics2D.rotate(-1*rotationRequired,locationX,locationY);
    }

    @Override
    public int getWidth() {
        return painter.getWidth();
    }

    @Override
    public int getHeight() {
        return painter.getHeight();
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
