package client.animation;

import java.awt.*;

public class Flip extends Anim {
    private final Anim back, front;

    public Flip(Anim back, Anim front) {
        this.back = back;
        this.front = front;
    }

    public void paint(Graphics2D graphics2D, int frame) {
        double second = (frame%60.0)/60.0;
        int translateX;
        double scaleX;
        Anim painter;
        if (second < 0.5) {
            scaleX = (1 - 2*second);
            painter = front;
        } else {
            scaleX = 2*second - 1;
            painter = back;
        }
        translateX = (int) ((painter.getWidth() * (1 - scaleX)) / 2);
        graphics2D.translate(translateX, 0);
        graphics2D.scale(scaleX, 1);
        painter.paint(graphics2D, frame);
        graphics2D.scale(1 / scaleX, 1);
        graphics2D.translate(-1*translateX, 0);
    }

    @Override
    public int getWidth() {
        return front.getWidth();
    }

    @Override
    public int getHeight() {
        return front.getHeight();
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
