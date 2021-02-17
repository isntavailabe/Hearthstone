package client.utils.config;

public class GameConfig {
    private Configs properties;
    private String name;
    private int cardsInHand;
    private int cardsInDeck;
    private int cardWidth;
    private int cardHeight;
    private int cardSpacing;
    private int minionSpacing;
    private int maxMinions;
    private int maxMana;
    private int firstCards;
    private int gameEventSpacing;
    private int gameEventrecent;
    private int mode;
    private String enemy;
    private String deckReader;
    private String player_one_hero;
    private String player_two_hero;

    public GameConfig(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getStateProperties(name);
    }

    protected void initialize() {
        cardsInHand = properties.readInteger("cardsInHand");
        cardsInDeck = properties.readInteger("cardsInDeck");
        cardWidth = properties.readInteger("cardWidth");
        cardHeight = properties.readInteger("cardHeight");
        cardSpacing = properties.readInteger("cardSpacing");
        minionSpacing = properties.readInteger("minionSpacing");
        maxMinions = properties.readInteger("maxMinions");
        maxMana = properties.readInteger("maxMana");
        firstCards = properties.readInteger("firstCards");
        gameEventSpacing = properties.readInteger("gameEventSpacing");
        gameEventrecent = properties.readInteger("gameEventrecent");
        mode = properties.readInteger("mode");
        enemy = properties.getProperty("enemy");
        deckReader = properties.getProperty("deckReader");
        player_one_hero = properties.getProperty("player_one_hero");
        player_two_hero = properties.getProperty("player_two_hero");

    }

    public int getCardsInHand() {
        return cardsInHand;
    }

    public int getCardsInDeck() {
        return cardsInDeck;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getCardSpacing() {
        return cardSpacing;
    }

    public int getMinionSpacing() {
        return minionSpacing;
    }

    public int getMaxMinions() {
        return maxMinions;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getFirstCards() {
        return firstCards;
    }

    public int getGameEventSpacing() {
        return gameEventSpacing;
    }

    public int getGameEventrecent() {
        return gameEventrecent;
    }

    public int getMode() {
        return mode;
    }

    public String getEnemy() {
        return enemy;
    }

    public String getDeckReader() {
        return deckReader;
    }

    public String getPlayer_one_hero() {
        return player_one_hero;
    }

    public String getPlayer_two_hero() {
        return player_two_hero;
    }
}
