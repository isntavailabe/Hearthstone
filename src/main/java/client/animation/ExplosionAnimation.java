package client.animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosionAnimation extends Anim {
    private BufferedImage[] frames;
    private int currentFrame;
    private ExplosionAnimationListener listener;
    private double previousTime;

    public ExplosionAnimation(ExplosionAnimationBuilder builder) {
        this.originX = builder.getOriginX();
        this.originY = builder.getOriginY();
        this.destinationX = builder.getDestinationX();
        this.destinationY = builder.getDestinationY();
        this.painter = builder.getPainter();
        this.speed = builder.getSpeed();
        this.frames = builder.getFrames();
        this.listener = builder.getListener();
        currentFrame = 0;
        active = true;
        previousTime = System.nanoTime();
    }

    @Override
    public void paint(Graphics2D graphics2D, int frame) {
        double second = speed.getPosition((this.frame % 60.0) / 60.0);
        double x = originX + (destinationX - originX) * (second),
                y = originY + (destinationY - originY) * (second);
        graphics2D.translate(x, y);
        painter.paint(graphics2D, this.frame);
        graphics2D.translate(-1 * x, -1 * y);
        if (System.nanoTime() - previousTime >= 100000000) {
            currentFrame++;
            previousTime = System.nanoTime();
        }
        if (currentFrame >= 12) {
            active = false;
            listener.eventOccurred();
        } else
            ((Painter) painter).setImage(frames[currentFrame]);
    }

    @Override
    int getWidth() {
        return 0;
    }

    @Override
    int getHeight() {
        return 0;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
