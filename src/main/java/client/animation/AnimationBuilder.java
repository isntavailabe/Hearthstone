package client.animation;

public interface AnimationBuilder<T> {

    T setSpeed(Speed speed);

    T setOriginX(int originX);

    T setOriginY(int originY);

    T setDestinationX(int destinationX);

    T setDestinationY(int destinationY);

    T setPainter(Painter painter);


}
