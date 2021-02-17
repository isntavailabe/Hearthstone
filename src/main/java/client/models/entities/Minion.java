package client.models.entities;

import client.models.Attribute;
import client.utils.Asset;
import client.models.cards.MinionCard;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static client.utils.Text.drawString;

public class Minion extends Entity {
    private MinionCard minionCard;
    private int hp;
    private int attack;

    public Minion() {
    }

    public Minion(MinionCard minionCard, int x, int y) {
        hp = minionCard.getHP();
        attack = minionCard.getAttack();
        this.minionCard = minionCard;
        bounding = new Ellipse2D.Double(x, y, 110, 130);
        attributes.addAll(minionCard.getAttributes());
        X = x;
        Y = y;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (minionCard == null)
            return;
        if (attributes.contains(Attribute.TAUNT))
            g.drawImage(Asset.utils.get("taunt"), x - 20, y - 20, 150, 180, null);
        g.setColor(Color.black);
        g.drawImage(Asset.minion, x, y, 110, 130, null);
        if (attributes.contains(Attribute.DIVINE_SHIELD))
            g.drawImage(Asset.utils.get("divine_shield"), x, y, 110, 130, null);
        if (minionCard.getDeathRattle().keySet().size() != 0)
            g.drawImage(Asset.utils.get("deathrattle"), x + 50, y, 10, 10, null);
        if (minionCard.getAttributes().contains(Attribute.CHARGE))
            drawString(g, "C", x, y, true, Color.BLACK, Asset.font20);
        if (minionCard.getAttributes().contains(Attribute.RUSH))
            drawString(g, "R", x + 10, y, true, Color.BLACK, Asset.font20);
        drawString(g, String.valueOf(hp), x + 80, y + 105, false, Color.black, Asset.font20);
        drawString(g, String.valueOf(attack), x + 20, y + 105, false, Color.black, Asset.font20);
        g.setColor(Color.DARK_GRAY);
    }

    @Override
    public void showInfo(Graphics2D g) {
        g.drawImage(Asset.cards.get(this.getMinionCard().getName()), X + 90, Y, 150, 200, null);
    }

    public void resetBounding(int minionSpacing) {
        X += minionSpacing;
        ((Ellipse2D) bounding).setFrame(X, Y, 100, 130);
    }

    public MinionCard getMinionCard() {
        return minionCard;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public void addHp(double a) {
        this.hp += a;
    }

    public void addAttack(double a) {
        this.attack += a;
    }

    public void setHp(double a) {
        hp = (int) a;
    }

    public void setAttack(double a) {
        attack = (int) a;
    }

    public void setMinionCard(MinionCard minionCard) {
        this.minionCard = minionCard;
    }


}
