package client.models.cards;

import client.models.Attribute;

import java.util.ArrayList;


public class MinionCard extends ActorCard {
    private int HP;
    private int attack;
    private ArrayList<Attribute> attributes = new ArrayList<>();

    public MinionCard() {
        super();
    }

    public MinionCard(String name, int manaCost, int price, String type, String heroClass, String rarity, int rarityValue, int hp, int attack) {
        super(name, manaCost, price, type, heroClass, rarity, rarityValue);
        this.HP = hp;
        this.attack = attack;

    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHP() {
        return this.HP;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
}
