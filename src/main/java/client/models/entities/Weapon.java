package client.models.entities;

import client.utils.Asset;
import client.models.cards.WeaponCard;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static client.utils.Text.drawString;

public class Weapon extends Entity {
    private WeaponCard weaponCard;
    private int durability;
    private int attack;

    public Weapon() {
    }

    public Weapon(WeaponCard weaponCard, int x, int y) {
        durability = weaponCard.getDurability();
        attack = weaponCard.getAttack();
        this.weaponCard = weaponCard;
        bounding = new Ellipse2D.Double(x, y, 120, 120);
        X = x;
        Y = y;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.setColor(Color.black);
        g.drawImage(Asset.weapon, x, y, 120, 120, null);
        drawString(g, String.valueOf(durability), x + 80, y + 80, false, Color.black, Asset.font20);
        drawString(g, String.valueOf(attack), x + 10, y + 80, false, Color.black, Asset.font20);
        if (weaponCard.getDeathRattle() != null)
            if (weaponCard.getDeathRattle().keySet().size() != 0)
                g.drawImage(Asset.utils.get("deathrattle"), x + 50, y, 40, 40, null);
        g.draw(bounding);
    }

    @Override
    public void showInfo(Graphics2D g) {
        g.drawImage(Asset.cards.get(this.getWeaponCard().getName()), X + 90, Y, 150, 200, null);
    }

    public WeaponCard getWeaponCard() {
        return weaponCard;
    }

    public void takeDamage(int damage) {
        durability -= 1;
    }

    public int getAttack() {
        return attack;
    }

    public void addHp(double a) {
        this.durability += a;
    }

    public void addAttack(double a) {
        this.attack += a;
    }

    public void setHp(double a) {
        durability = (int) a;
    }

    public void setAttack(double a) {
        attack = (int) a;
    }

    public void setWeaponCard(WeaponCard weaponCard) {
        this.weaponCard = weaponCard;
    }

    @Override
    public int getHp() {
        return durability;
    }

}
