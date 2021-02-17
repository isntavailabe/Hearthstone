package client.models.entities.heroes;

import client.models.Attribute;
import client.models.entities.Entity;
import client.utils.Asset;
import client.models.cards.card;

import java.awt.*;
import java.util.ArrayList;

import static client.utils.Text.*;

public class Hero extends Entity implements Cloneable {
    private String name;
    private int HP;
    private ArrayList<card> cards = new ArrayList<>();
    private HeroPower heroPower;
    private int defense;

    public Hero clone() {
        Hero clone = new Hero();
        clone.setHp(this.getHp());
        clone.setName(this.getName());
        clone.setCards((ArrayList<card>) this.getCards().clone());
        clone.setHeroPower(this.getHeroPower().clone());
        return clone;
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.drawImage(Asset.heroes.get(name), x, y, 180, 230, null);
        if (attributes.contains(Attribute.IMMUNE_WHILE_ATTACKING))
            g.drawImage(Asset.utils.get("immune_hero"), x, y, 160, 160, null);
        g.drawImage(Asset.heroPowers.get(name + "_power"), x + 200, y + 50, 100, 100, null);
        drawString(g, String.valueOf(HP), x + 155, y + 170, true, Color.white, Asset.font20);
        if (defense != 0) {
            g.drawImage(Asset.utils.get("defense"), x + 160, y + 115, 40, 40, null);
            drawString(g, String.valueOf(defense), x + 180, y + 130, true, Color.white, Asset.font20);
        }
        g.draw(heroPower.getBounding());
        g.draw(bounding);
    }

    public void addDefense(int defense) {
        this.defense += defense;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<card> getCards() {
        return cards;
    }

    public int getHp() {
        return HP;
    }

    public void setCards(ArrayList<card> cards) {
        this.cards = cards;
    }

    public HeroPower getHeroPower() {
        return heroPower;
    }

    public void setBounding(int x, int y) {
        bounding = new Rectangle(x, y, 160, 210);
    }

    public void takeDamage(int damage) {
        if (defense <= damage) {
            HP -= damage - defense;
            defense = 0;
        } else {
            defense -= damage;
        }
    }

    public void addHp(double a) {
        this.HP += a;
        if (HP > 30)
            HP = 30;
    }

    public void setHp(double a) {
        HP = (int) a;
    }

    public void setHeroPower(HeroPower heroPower) {
        this.heroPower = heroPower;
    }

    @Override
    public void showInfo(Graphics2D g) {

    }

    @Override
    public String toString() {
        return this.name + " ";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hero) {
            return this.getName().equals(((Hero) obj).getName());
        }
        return super.equals(obj);
    }

}
