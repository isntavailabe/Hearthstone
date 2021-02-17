package client.models.cards;

import java.util.ArrayList;

public class SpellCard extends card {
    private ArrayList<Object> quest;

    public SpellCard(String name, int manaCost, int price, String type, String heroClass, String rarity, int rarityValue) {
        super(name, manaCost, price, type, heroClass, rarity, rarityValue);
    }

    public ArrayList<Object> getQuest() {
        return quest;
    }
}
