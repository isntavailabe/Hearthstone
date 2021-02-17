package client.utils.config;

public class InfoPassiveConfig {
    private Configs properties;
    private String name;
    private int numPassivesToShow;
    private int numAllPassives;
    private int cardWidth;
    private int cardHeight;
    private int cardSpacing;
    private int twicedrawDefault;
    private int offcardsDefault;
    private int warriorsDefault;
    private int freepowerDefault;
    private int heropowerCounterDefault;
    private int manajumpDefault;
    private int twicedraw;
    private int offcards;
    private int freepower;
    private int heropowerCounter;
    private int manajump;

    public InfoPassiveConfig(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getStateProperties(name);
    }

    protected void initialize() {
        numPassivesToShow = properties.readInteger("numPassivesToShow");
        numAllPassives = properties.readInteger("numAllPassives");
        cardWidth = properties.readInteger("cardWidth");
        cardHeight = properties.readInteger("cardHeight");
        cardSpacing = properties.readInteger("cardSpacing");
        twicedrawDefault = properties.readInteger("twicedrawDefault");
        offcardsDefault = properties.readInteger("offcardsDefault");
        warriorsDefault = properties.readInteger("warriorsDefault");
        freepowerDefault = properties.readInteger("freepowerDefault");
        heropowerCounterDefault = properties.readInteger("heropowerCounterDefault");
        manajumpDefault = properties.readInteger("manajumpDefault");
        twicedraw = properties.readInteger("twicedraw");
        offcards = properties.readInteger("offcards");
        freepower = properties.readInteger("freepower");
        heropowerCounter = properties.readInteger("heropowerCounter");
        manajump = properties.readInteger("manajump");

    }

    public int getNumPassivesToShow() {
        return numPassivesToShow;
    }

    public int getNumAllPassives() {
        return numAllPassives;
    }

    public int getcardWidth() {
        return cardWidth;
    }

    public int getcardHeight() {
        return cardHeight;
    }

    public int getCardSpacing() {
        return cardSpacing;
    }

    public int getTwicedrawDefault() {
        return twicedrawDefault;
    }

    public int getOffcardsDefault() {
        return offcardsDefault;
    }

    public int getWarriorsDefault() {
        return warriorsDefault;
    }

    public int getFreepowerDefault() {
        return freepowerDefault;
    }

    public int getHeropowerCounterDefault() {
        return heropowerCounterDefault;
    }

    public int getManajumpDefault() {
        return manajumpDefault;
    }

    public int getTwicedraw() {
        return twicedraw;
    }

    public int getOffcards() {
        return offcards;
    }

    public int getFreepower() {
        return freepower;
    }

    public int getHeropowerCounter() {
        return heropowerCounter;
    }

    public int getManajump() {
        return manajump;
    }
}
