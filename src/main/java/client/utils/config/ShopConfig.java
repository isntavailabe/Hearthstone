package client.utils.config;

public class ShopConfig {
    private Configs properties;
    private String name;
    private int cardWidth;
    private int cardHeight;
    private int lockWidth;
    private int lockHeight;
    private int Spacing;
    private int cardInPage;
    private int cardX;
    private int rowOneY;
    private int rowTwoY;

    public ShopConfig(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getStateProperties(name);
    }

    protected void initialize() {
        cardWidth = properties.readInteger("cardWidth");
        cardHeight = properties.readInteger("cardHeight");
        lockWidth = properties.readInteger("lockWidth");
        lockHeight = properties.readInteger("lockHeight");
        Spacing = properties.readInteger("Spacing");
        cardInPage = properties.readInteger("cardInPage");
        cardX = properties.readInteger("cardX");
        rowOneY = properties.readInteger("rowOneY");
        rowTwoY = properties.readInteger("rowTwoY");

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

    public int getSpacing() {
        return Spacing;
    }

    public int getCardInPage() {
        return cardInPage;
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
