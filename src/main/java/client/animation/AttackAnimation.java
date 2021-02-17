package client.animation;

import client.models.Player;
import client.models.entities.Entity;
import client.utils.Asset;

import java.awt.*;

import static client.audio.SoundEffect.effectPlay;
import static client.audio.SoundEffect.setFile;

public class AttackAnimation extends Anim {
    private AttackListener listener;
    private Entity actor;
    private Entity target;
    private Player actorPlayer;
    private Player targetPlayer;

    public AttackAnimation(AttackAnimationBuilder builder) {
        this.originX = builder.getOriginX();
        this.originY = builder.getOriginY();
        this.destinationX = builder.getDestinationX();
        this.destinationY = builder.getDestinationY();
        this.painter = builder.getPainter();
        this.speed = builder.getSpeed();
        this.actor = builder.getActor();
        this.target = builder.getTarget();
        this.actorPlayer = builder.getActorPlayer();
        this.targetPlayer = builder.getTargetPlayer();
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
        painter.setX((int) -Math.abs((destinationX - originX) * (second)) * 3);
        painter.setY((int) -Math.abs((destinationY - originY) * (second)) * 3);
        painter.setWidth((int) (painter.getWidth() + Math.abs((destinationX - originX) * (second))));
        painter.setHeight((int) (painter.getHeight() + Math.abs((destinationY - originY) * (second))));
        graphics2D.translate(-1 * x, -1 * y);
        if (second >= 0.25) {
            active = false;
            setFile(Asset.sound.get("damaged"));
            effectPlay();
            listener.eventOccurred(actor, target, actorPlayer, targetPlayer);
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

}
