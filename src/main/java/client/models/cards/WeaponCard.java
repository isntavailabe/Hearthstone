package client.models.cards;

public class WeaponCard extends ActorCard {
    private int durability;
    private int attack;

    public WeaponCard(String name, int manaCost, int price, String type, String heroClass, String rarity, int rarityValue, int durability, int attack) {
        super(name, manaCost, price, type, heroClass, rarity, rarityValue);
        this.attack = attack;
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public int getAttack() {
        return attack;
    }

}
