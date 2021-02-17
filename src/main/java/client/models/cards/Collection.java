package client.models.cards;

import java.util.ArrayList;
import java.util.HashMap;

public class Collection {
    private String Name;
    private ArrayList<card> Deck = new ArrayList<card>();
    private int wins;
    private int totalPlayed;
    private int avrManaCost;
    private String HeroClass;
    private card mostUsed;
    private HashMap<card, Integer> counter = new HashMap<>();

    public Collection() {

    }

    public Collection clone(){
        Collection clone = new Collection();
        clone.setDeck((ArrayList<card>) this.Deck.clone());
        clone.setName(this.getName());
        clone.setWins(this.getWins());
        clone.setTotalPlayed(this.getTotalPlayed());
        clone.setHeroClass(this.getHeroClass());
        clone.setAvrManaCost(this.getAvrManaCost());
        clone.setCounter((HashMap<card, Integer>) this.getCounter().clone());
        return clone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDeck(ArrayList<card> deck) {
        Deck.clear();
        Deck.addAll(deck);
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setTotalPlayed(int totalPlayed) {
        this.totalPlayed = totalPlayed;
    }

    public void setAvrManaCost(int avrManaCost) {
        this.avrManaCost = avrManaCost;
    }

    public void setHeroClass(String heroClass) {
        HeroClass = heroClass;
    }

    public void setCounter(HashMap<card, Integer> counter) {
        this.counter = counter;
    }

    public ArrayList<card> getDeck() {
        return Deck;
    }

    public int getWins() {
        return wins;
    }

    public int getTotalPlayed() {
        return totalPlayed;
    }

    public int getAvrManaCost() {
        return avrManaCost;
    }

    public String getHeroClass() {
        return HeroClass;
    }

    public HashMap<card, Integer> getCounter() {
        return counter;
    }

    public void setMostUsed(card mostUsed) {
        this.mostUsed = mostUsed;
    }

    public card getMostUsed() {
        return mostUsed;
    }
}
