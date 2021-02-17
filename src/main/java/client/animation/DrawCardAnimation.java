package client.animation;

import client.models.cards.card;

import java.awt.*;

public class DrawCardAnimation extends Anim {
    private DrawCardListener listener;
    private card c;


    public DrawCardAnimation(DrawCardAnimationBuilder builder) {
        this.originX = builder.getOriginX();
        this.originY = builder.getOriginY();
        this.destinationX = builder.getDestinationX();
        this.destinationY = builder.getDestinationY();
        this.painter = builder.getPainter();
        this.speed = builder.getSpeed();
        this.c = builder.getC();
        this.listener = builder.getListener();
        active = true;
    }

    @Override
    public void paint(Graphics2D graphics2D, int frame) {
        double second = speed.getPosition((this.frame % 60.0) / 60.0);
        double x = originX + (destinationX - originX) * (second),
                y = originY + (destinationY - originY) * (second);
        graphics2D.translate(x, y);
        painter.paint(graphics2D, this.frame);
        graphics2D.translate(-1 * x, -1 * y);
        if (second >= 0.98) {
            active = false;
            listener.eventOccurred(c);
        }
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

    public void setListener(DrawCardListener listener) {
        this.listener = listener;
    }

    @Override
    public void addFrame() {
        super.addFrame();
    }
}
