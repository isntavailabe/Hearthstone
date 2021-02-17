package client.models.cards;

import java.util.HashMap;

public class ActorCard extends card {
    protected HashMap<String, Object> battleCry = new HashMap<>();
    protected HashMap<String, Object> deathRattle = new HashMap<>();

    ActorCard() {
        super();
    }

    ActorCard(String name, int manaCost, int price, String type, String heroClass, String rarity, int rarityValue) {
        super(name, manaCost, price, type, heroClass, rarity, rarityValue);
    }

    public HashMap<String, Object> getBattleCry() {
        return battleCry;
    }

    public HashMap<String, Object> getDeathRattle() {
        return deathRattle;
    }
}
