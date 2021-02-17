package client.animation;

import client.models.Player;
import client.models.entities.Entity;

public class AttackAnimationBuilder implements AnimationBuilder<AttackAnimationBuilder>{
    private int originX, originY, destinationX, destinationY;
    private Anim painter;
    private Speed speed;
    private AttackListener listener;
    private Entity actor;
    private Entity target;
    private Player actorPlayer;
    private Player targetPlayer;


    public AttackAnimationBuilder setActor(Entity actor) {
        this.actor = actor;
        return this;
    }

    public AttackAnimationBuilder setTarget(Entity target) {
        this.target = target;
        return this;
    }

    public AttackAnimationBuilder setActorPlayer(Player actorPlayer) {
        this.actorPlayer = actorPlayer;
        return this;
    }

    public AttackAnimationBuilder setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        return this;
    }


    @Override
    public AttackAnimationBuilder setSpeed(Speed speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public AttackAnimationBuilder setOriginX(int originX) {
        this.originX = originX;
        return this;
    }

    @Override
    public AttackAnimationBuilder setOriginY(int originY) {
        this.originY = originY;
        return this;
    }

    @Override
    public AttackAnimationBuilder setDestinationX(int destinationX) {
        this.destinationX = destinationX;
        return this;
    }

    @Override
    public AttackAnimationBuilder setDestinationY(int destinationY) {
        this.destinationY = destinationY;
        return this;
    }

    @Override
    public AttackAnimationBuilder setPainter(Painter painter) {
        this.painter = painter;
        return this;
    }

    public AttackAnimationBuilder setListener(AttackListener listener) {
        this.listener = listener;
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

    public AttackListener getListener() {
        return listener;
    }

    public Entity getActor() {
        return actor;
    }

    public Entity getTarget() {
        return target;
    }

    public Player getActorPlayer() {
        return actorPlayer;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }
}
