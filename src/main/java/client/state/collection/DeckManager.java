package client.state.collection;

import client.state.Handler;
import client.utils.Asset;
import client.models.cards.Collection;

import java.awt.*;
import java.util.ArrayList;

import static client.utils.Logger.logger;
import static client.utils.Text.drawString;
import static client.state.collection.CollectionState.getConfig;

public class DeckManager {
    private Handler handler;
    private int selectedItem = 0;
    private ArrayList<Collection> Decks;
    private static final int Spacing = 2580 / 30;
    private state current;
    private int selectedHero;

    private int deckImageWidth = getConfig().getDeckImageWidth();
    private int deckImageHeight = getConfig().getDeckImageHeight();

    public enum state {
        DECK, EDIT_DECK, CHANGE_DECK, NEW_DECK;
    }

    public DeckManager(Handler handler) {
        current = state.DECK;
        selectedHero = 0;
        this.handler = handler;
        Decks = new ArrayList<>();
        updateDeck();

    }

    public void update() {

    }

    public void render(int x, int y, Graphics2D g) {
        int len = Decks.size();
        if (current == state.DECK && len != 0) {
            renderDecks(len, x, y, g);
        } else if (current == state.EDIT_DECK && len != 0) {
            rendercards(x, y, g);
        } else if (current == state.NEW_DECK) {
            handler.getGame().getDecksName().setVisible(true);
            drawString(g, "SELECT HERO :", 1100, 170, false, Color.white, Asset.font20);
        } else if (current == state.CHANGE_DECK && len != 0) {
            handler.getGame().getDecksName().setVisible(true);
            drawString(g, "SELECT HERO :", 1100, 170, false, Color.white, Asset.font20);
        }
    }

    private void updateDeck() {
        for (String deckname : handler.getGame().getEnvironment().getCurrentPlayer().getDeck().keySet()) {
            Decks.add(handler.getGame().getEnvironment().getCurrentPlayer().getDeck().get(deckname));
        }
    }

    private void rendercards(int x, int y, Graphics2D g) {
        if (Decks.get(selectedItem).getDeck().size() == 0)
            return;
        for (int j = 0; j < Decks.get(selectedItem).getDeck().size(); j++) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y + j * 25, 200, 24);
            drawString(g, Decks.get(selectedItem).getDeck().get(j).getName(), x + 5,
                    y + 17 + j * 25, false, Color.white, Asset.font20);
        }
        drawString(g, "EditDeck", 1090, 940, false, Color.white, Asset.font20);
    }

    private void renderDecks(int len, int x, int y, Graphics2D g) {
        for (int i = -4; i < 5; i++) {
            if (selectedItem + i < 0 || selectedItem + i >= len)
                continue;
            if (i == 0) {
                renderDeck(g, x, y, i, Color.orange);
            } else {
                renderDeck(g, x, y, i, Color.white);
            }
        }
    }

    private void renderDeck(Graphics2D g, int x, int y, int i,Color color){
        g.drawImage(Asset.heroDeck.get(Decks.get(selectedItem + i).getHeroClass()),
                x, y + Spacing * 4 + i * Spacing, deckImageWidth, deckImageHeight, null);
        drawString(g, getDeckName(selectedItem + i), x + deckImageWidth / 2,
                y + Spacing * 4 + i * Spacing + deckImageHeight / 2, true, color, Asset.font20);
    }

    private String getDeckName(int i) {
        String str = Decks.get(i).getName();
        if (str.length() > 8) {
            str = str.substring(0, 8);
        }
        return str;
    }

    void scrollUp(){
        selectedItem -= 1;
        if (selectedItem < 0)
            selectedItem = 0;
    }

    void scrollDown(){
        selectedItem += 1;
        if (selectedItem >= Decks.size())
            selectedItem = Decks.size() - 1;
    }

    void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    int getSelectedItem() {
        return selectedItem;
    }

    public static int getSpacing() {
        return Spacing;
    }

    ArrayList<Collection> getDecks() {
        return Decks;
    }

    state getCurrent() {
        return current;
    }

    void setSelectedHero(int selectedHero) {
        this.selectedHero = selectedHero;
    }

    int getSelectedHero() {
        return selectedHero;
    }

    void setCurrent(state current) {
        this.current = current;
    }
}
