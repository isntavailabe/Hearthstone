package client.utils.config;

public class StatusConfig {
    private Configs properties;
    private String name;
    private int labelX;
    private int labelY;
    private int infoX;
    private int infoY;
    private int deckNameX;
    private int deckNameY;
    private int lengthLimit;
    private int Spacing;


    public StatusConfig(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getStateProperties(name);
    }

    protected void initialize() {

        labelX = properties.readInteger("labelX");
        labelY = properties.readInteger("labelY");
        infoX = properties.readInteger("infoX");
        infoY = properties.readInteger("infoY");
        deckNameX = properties.readInteger("deckNameX");
        deckNameY = properties.readInteger("deckNameY");
        lengthLimit = properties.readInteger("lengthLimit");
        Spacing = properties.readInteger("Spacing");

    }

    public int getLabelX() {
        return labelX;
    }

    public int getLabelY() {
        return labelY;
    }

    public int getInfoX() {
        return infoX;
    }

    public int getInfoY() {
        return infoY;
    }

    public int getDeckNameX() {
        return deckNameX;
    }

    public int getDeckNameY() {
        return deckNameY;
    }

    public int getLengthLimit() {
        return lengthLimit;
    }

    public int getSpacing() {
        return Spacing;
    }
}
