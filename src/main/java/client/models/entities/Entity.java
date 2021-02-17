package client.models.entities;

import client.models.Attribute;
import client.state.game.actions.Action;
import org.graalvm.compiler.hotspot.nodes.AcquiredCASLockNode;

import java.awt.*;
import java.util.HashSet;

public abstract class Entity {
    transient protected Shape bounding;
    transient protected HashSet<Attribute> attributes = new HashSet<>();
    transient protected HashSet<Action> actions = new HashSet<>();
    transient protected int X;
    transient protected int Y;

    public abstract void render(Graphics2D g, int x, int y);

    public boolean isHovered(int x, int y) {
        return bounding.contains(x, y);
    }

    public abstract void showInfo(Graphics2D g);

    public abstract void takeDamage(int damage);

    public HashSet<Attribute> getAttributes() {
        return attributes;
    }

    public HashSet<Action> getActions() {
        return actions;
    }

    public abstract void addHp(double a);

    public void addAttack(double a) {}

    public abstract void setHp(double a);

    public void setAttack(double a) {}

    public abstract int getHp();

    public int getAttack() {
        return 0;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setBounding(Shape bounding) {
        this.bounding = bounding;
    }
}
