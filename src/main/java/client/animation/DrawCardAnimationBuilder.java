package client.animation;

import client.models.cards.card;

public class DrawCardAnimationBuilder implements AnimationBuilder<DrawCardAnimationBuilder> {
    private int originX, originY, destinationX, destinationY;
    private Anim painter;
    private Speed speed;
    private DrawCardListener listener;
    private card c;


    public DrawCardAnimationBuilder setPainter(Anim painter) {
        this.painter = painter;
        return this;
    }

    public DrawCardAnimationBuilder setC(card c) {
        this.c = c;
        return this;
    }

    public DrawCardAnimationBuilder setListener(DrawCardListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public DrawCardAnimationBuilder setSpeed(Speed speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public DrawCardAnimationBuilder setOriginX(int originX) {
        this.originX = originX;
        return this;
    }

    @Override
    public DrawCardAnimationBuilder setOriginY(int originY) {
        this.originY = originY;
        return this;
    }

    @Override
    public DrawCardAnimationBuilder setDestinationX(int destinationX) {
        this.destinationX = destinationX;
        return this;
    }

    @Override
    public DrawCardAnimationBuilder setDestinationY(int destinationY) {
        this.destinationY = destinationY;
        return this;
    }

    @Override
    public DrawCardAnimationBuilder setPainter(Painter painter) {
        this.painter = painter;
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

    public DrawCardListener getListener() {
        return listener;
    }

    public card getC() {
        return c;
    }
}
