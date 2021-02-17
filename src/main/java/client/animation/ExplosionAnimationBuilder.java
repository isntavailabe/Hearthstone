package client.animation;

import java.awt.image.BufferedImage;

public class ExplosionAnimationBuilder implements AnimationBuilder<ExplosionAnimationBuilder> {
    private int originX, originY, destinationX, destinationY;
    private Anim painter;
    private Speed speed;
    private ExplosionAnimationListener listener;
    private BufferedImage[] frames;

    @Override
    public ExplosionAnimationBuilder setSpeed(Speed speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public ExplosionAnimationBuilder setOriginX(int originX) {
        this.originX = originX;
        return this;
    }

    @Override
    public ExplosionAnimationBuilder setOriginY(int originY) {
        this.originY = originY;
        return this;
    }

    @Override
    public ExplosionAnimationBuilder setDestinationX(int destinationX) {
        this.destinationX = destinationX;
        return this;
    }

    @Override
    public ExplosionAnimationBuilder setDestinationY(int destinationY) {
        this.destinationY = destinationY;
        return this;
    }

    @Override
    public ExplosionAnimationBuilder setPainter(Painter painter) {
        this.painter = painter;
        return this;
    }

    public ExplosionAnimationBuilder setListener(ExplosionAnimationListener listener) {
        this.listener = listener;
        return this;
    }

    public ExplosionAnimationBuilder setFrames(BufferedImage[] frames) {
        this.frames = frames;
        return this;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public Anim getPainter() {
        return painter;
    }

    public Speed getSpeed() {
        return speed;
    }

    public BufferedImage[] getFrames() {
        return frames;
    }

    public ExplosionAnimationListener getListener() {
        return listener;
    }
}
