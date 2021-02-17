package client.utils.config;

public class CollectionConfig {
    private Configs properties;
    private String name;
    private int cardsInDeck;
    private int cardsInPage;
    private int cardWidth;
    private int cardHeight;
    private int lockWidth;
    private int lockHeight;
    private int deckImageWidth;
    private int deckImageHeight;
    private int cardSpacing;
    private int deckManagerX;
    private int deckManagerY;
    private int manaY;
    private int manaX;
    private int manaSpacing;
    private int cardX;
    private int rowOneY;
    private int rowTwoY;

    public CollectionConfig(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getStateProperties(name);
    }

    private void initialize() {
        cardsInDeck = properties.readInteger("cardsInDeck");
        cardsInPage = properties.readInteger("cardsInPage");
        cardWidth = properties.readInteger("cardWidth");
        cardHeight = properties.readInteger("cardHeight");
        lockWidth = properties.readInteger("lockWidth");
        lockHeight = properties.readInteger("lockHeight");
        deckImageWidth = properties.readInteger("deckImageWidth");
        deckImageHeight = properties.readInteger("deckImageHeight");
        cardSpacing = properties.readInteger("cardSpacing");
        deckManagerX = properties.readInteger("deckManagerX");
        deckManagerY = properties.readInteger("deckManagerY");
        manaY = properties.readInteger("manaY");
        manaX = properties.readInteger("manaX");
        manaSpacing = properties.readInteger("manaSpacing");
        cardX = properties.readInteger("cardX");
        rowOneY = properties.readInteger("rowOneY");
        rowTwoY = properties.readInteger("rowTwoY");

    }

    public int getCardsInDeck() {
        return cardsInDeck;
    }

    public int getCardsInPage() {
        return cardsInPage;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getLockWidth() {
        return lockWidth;
    }

    public int getLockHeight() {
        return lockHeight;
    }

    public int getDeckImageWidth() {
        return deckImageWidth;
    }

    public int getDeckImageHeight() {
        return deckImageHeight;
    }

    public int getCardSpacing() {
        return cardSpacing;
    }

    public int getDeckManagerX() {
        return deckManagerX;
    }

    public int getDeckManagerY() {
        return deckManagerY;
    }

    public int getManaY() {
        return manaY;
    }

    public int getManaX() {
        return manaX;
    }

    public int getManaSpacing() {
        return manaSpacing;
    }

    public int getCardX() {
        return cardX;
    }

    public int getRowOneY() {
        return rowOneY;
    }

    public int getRowTwoY() {
        return rowTwoY;
    }
}
