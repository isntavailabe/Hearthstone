package client.models;

import client.models.cards.Collection;
import client.models.cards.MinionCard;
import client.models.cards.card;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.models.entities.Weapon;
import client.models.entities.heroes.Hero;
import client.state.game.questReward.Quest;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    protected String name;
    protected String password;
    protected long id;
    protected int diamonds;
    protected ArrayList<card> hand = new ArrayList<>();
    protected ArrayList<card> collection = new ArrayList<>();
    protected HashMap<String, Collection> deck = new HashMap<>();
    protected Collection currentDeck = new Collection();
    protected ArrayList<Minion> minions = new ArrayList<>();
    protected ArrayList<Entity> entities = new ArrayList<>();
    protected ArrayList<Hero> unlockedHeroes = new ArrayList<>();
    protected Weapon weapon;
    protected Hero hero;
    protected int mana;
    protected int maxMana;
    protected int turn;
    transient protected ArrayList<Quest> quests = new ArrayList<>();

    public Player() {
    }

    public Player(String name, String password, long id) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.diamonds = 50;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public Collection getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(Collection currentDeck) {
        this.currentDeck = currentDeck;
    }

    public HashMap<String, Collection> getDeck() {
        return deck;
    }

    public String getName() {
        return this.name;
    }

    public String getPass() {
        return this.password;
    }

    public long getId() {
        return this.id;
    }

    public int getDiamonds() {
        return this.diamonds;
    }

    public ArrayList<card> getHand() {
        return this.hand;
    }

    public int getMana() {
        return this.mana;
    }

    public ArrayList<card> getCollection() {
        return this.collection;
    }

    public ArrayList<Hero> getUnlockedHeroes() {
        return this.unlockedHeroes;
    }

    public void setCollection(ArrayList<card> collection) {
        this.collection.clear();
        this.collection.addAll(collection);
    }

    public Hero getHeroFromUnlocked(String name) {
        for (Hero hero : this.getUnlockedHeroes()) {
            if (hero.getName().equals(name))
                return hero;
        }
        return null;
    }

    public MinionCard getMinionCard() {
        for (card c : currentDeck.getDeck()) {
            if (c instanceof MinionCard)
                return (MinionCard) c;
        }
        return null;
    }

    public MinionCard getMinionFromHand() {
        for (card card : hand) {
            if (card instanceof MinionCard)
                return (MinionCard) card;
        }
        return null;
    }

    public void setUnlockedHeroes(ArrayList<Hero> unlockedHeroes) {
        this.unlockedHeroes = unlockedHeroes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public ArrayList<Minion> getMinions() {
        return this.minions;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setQuests(ArrayList<Quest> quests) {
        this.quests = quests;
    }
}
