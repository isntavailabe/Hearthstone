package client.models.cards;

import java.util.HashMap;

public class card {
    private String name;
    private int manaCost;
    private String type;
    private String heroClass;
    private String rarity;
    private int rarityValue;
    private int price;

    private HashMap<String, Object> actions = new HashMap<>();

    public card() {

    }

    public card(String name, int manaCost, int price, String type, String heroClass, String rarity, int rarityValue) {
        this.name = name;
        this.manaCost = manaCost;
        this.type = type;
        this.heroClass = heroClass;
        this.rarity = rarity;
        this.rarityValue = rarityValue;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public String getType() {
        return this.type;
    }

    public String getHeroClass() {
        return this.heroClass;
    }

    public String getRarity() {
        return rarity;
    }

    public HashMap<String, Object> getActions() {
        return actions;
    }

    public int getRarityValue() {
        return rarityValue;
    }

    public void setActions(HashMap<String, Object> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return name + ".json";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof card) {
            return this.getName().equals(((card) obj).getName());
        }
        return super.equals(obj);
    }
}
